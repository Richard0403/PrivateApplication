package com.richard.record.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.richard.record.R;
import com.richard.record.recoder.VideoUtils;
import com.richard.record.constants.AppSetting;
import com.richard.record.utils.ToastUtil;

import java.io.File;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;

/**
 * Created by richard on 2016/10/24.
 */

public class BaseApplication extends Application {
    private static BaseApplication INSTANCE;
    public static boolean isRecording;
    public static String curVideoId;
    public static DisplayImageOptions options;
    private RefWatcher refWatcher;


    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);

        initApplication();
        initDatabase();
        initImageLoader();

    }

    private void initImageLoader() {
        WindowManager wm = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        File cacheDir = new File( AppSetting.APP_IMAGE_TMP_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(width, height)
                .threadPoolSize(3) // default 3
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(40 * 1024 * 1024))
                .memoryCacheSize(50 * 1024 * 1024)
                //.memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(50)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // Not necessary in common
                .build();

        //Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);
        options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.headphoto)
                .showImageForEmptyUri(R.mipmap.ic_common)
                .showImageOnFail(R.mipmap.ic_common)
                .resetViewBeforeLoading(false)
                //.delayBeforeLoading(500)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .cacheInMemory(false)
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                //.displayer(new FadeInBitmapDisplayer(100))// 图片加载好后渐入的动画时间
                .build();

    }

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    private void initDatabase() {
        Sprinkles sprinkles = Sprinkles.init(this, "com.richard.record", 0);
        sprinkles.addMigration(new Migration() {

            @Override
            protected void doMigration(SQLiteDatabase db) {
                // TODO Auto-generated method stub
                db.execSQL(
                        "CREATE TABLE video (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "path TEXT," +
                                "city TEXT," +
                                "street TEXT," +
                                "picture TEXT," +
                                "startTime INTEGER," +
                                "endTime INTEGER," +
                                "latitude DOUBLE," +
                                "lontitude DOUBLE," +
                                "end_latitude DOUBLE," +
                                "end_lontitude DOUBLE," +
                                "end_street TEXT," +
                                "route TEXT," +
                                "video_transpose INTEGER" +
                                ")"
                );

                db.execSQL(
                        "CREATE TABLE event_video (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "savedPath TEXT," +
                                "city TEXT," +
                                "street TEXT," +
                                "time INTEGER," +
                                "type INTEGER," +
                                "typeDes TEXT," +
                                "status INTEGER," +
                                "statusDes TEXT," +
                                "picture TEXT," +
                                "video_id INTEGER," +
                                "originalVideoPath TEXT," +
                                "start_position_of_original_video INTEGER," +
                                "video_length INTEGER," +
                                "is_shared BOOLEAN," +
                                "share_url TEXT," +
                                "play_url TEXT," +
                                "startTime INTEGER," +
                                "endTime INTEGER," +
                                "latitude DOUBLE," +
                                "lontitude DOUBLE," +
                                "upload_status TEXT," +
                                "upload_cartnumber TEXT," +
                                "upload_car_type TEXT," +
                                "upload_tag TEXT," +
                                "upload_title TEXT," +
                                "upload_car_shot_pic TEXT," +
                                "upload_weizhang_reason INTEGER," +
                                "upload_add_info TEXT," +
                                "upload_vid TEXT," +
                                "curr_rogress INTEGER," +
                                "value INTEGER," +
                                "savedFileNameInServer TEXT," +
                                "video_more_pic TEXT," +
                                "video_merge_pic TEXT," +
                                "savedFileNameIn7Niu TEXT," +
                                "video_transpose INTEGER" +

                                ")"
                );
            }
        });
    }

    /**
     * 初始化应用目录
     */
    private void initApplication() {
        try {
            File root = new File(AppSetting.APP_PATH);
            if (!root.exists()) {
                root.mkdirs();
            }
            File image = new File(AppSetting.APP_IMAGE_PATH);
            if (!image.exists()) {
                image.mkdirs();
            }

//    	    File message = new File(AppSetting.HISTORY_MESSAGES);
//    	    if(message.exists()){
//    	    	if (message.isFile()) {
//    	    		message.delete();
//    	    	}
//    	    }

            File cache = new File(AppSetting.APP_CACHE_PATH);
            if (!cache.exists()) {
                cache.mkdirs();
            }

            File media = new File(AppSetting.APP_VIDEO_PATH);
            if (!media.exists()) {
                media.mkdirs();
            }

            File ffmpeg = new File(AppSetting.APP_FFMPEG);
            if (!ffmpeg.exists()) {
                ffmpeg.mkdirs();
            }

            FFmpeg ff = VideoUtils.getFFmpeg();
            File ffmpeg_cpu = new File(AppSetting.APP_FFMPEG + "/" + ff.cpuArchNameFromAssets);
            if (!ffmpeg_cpu.exists()) {
                ffmpeg_cpu.mkdirs();
            }
        } catch (Exception e) {
            ToastUtil.show(this, "文件夹创建失败");
        }

    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

}