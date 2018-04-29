package com.richard.record.recoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.Query;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.richard.record.MainActivity;
import com.richard.record.base.BaseApplication;
import com.richard.record.constants.Config;
import com.richard.record.entity.EventVideoEntity;
import com.richard.record.entity.VideoEntity;
import com.richard.record.constants.AppSetting;
import com.richard.record.fragments.HomeFragment;
import com.richard.record.fragments.VideoFragment;
import com.richard.record.utils.FileUtils;
import com.richard.record.utils.SharedPreferenceHelper;
import com.richard.record.utils.Util;


public class VideoHandler {
	private static String TAG ="VideoHandler";
	private static FFmpeg ffmpeg;
	private static BlockingQueue taskQueue = new LinkedBlockingQueue();
	public static boolean isRunning =false;
	
	private static Context mContext = BaseApplication.getInstance().getApplicationContext();

	private static final int HANDLE_NEXT =0x01;
	private static final int HANDLE_OVER =0x02;
	
	private static Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLE_NEXT:
				if(taskQueue.size()!=0){
					ArrayList<String> task = null;
					CursorList<EventVideoEntity> enventList = null;
					try {
						task = (ArrayList<String>) taskQueue.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(task!=null){
						processTask(task);
					}
				}
				break;
			case HANDLE_OVER:
				isRunning = false;
				break;
			default:
				break;
			}
		};
	};
	
	public static class VideoRunner implements Runnable {
		Iterator iterator;
		Runnable runnable;
		int size,i;
		CursorList<EventVideoEntity> cList;
		VideoRunner(Iterator paramIterator, Runnable paramRunnable,int size) {
			iterator = paramIterator;
			runnable = paramRunnable;
			this.size= size;
		}

		public void run() {			
			if (!iterator.hasNext()) {
				runnable.run();
				return;
			}
			
			int progress=i*100/(size);
			Log.i("VedioUtil", "==="+i);
			i+=1;
			if(Util.mBuilder!=null){
				Util.mBuilder.setProgress(100, progress, false);
				Util.manager.notify(1, Util.mBuilder.build());
			}
			VideoHandler.excute((String) iterator.next(), this);
		}
	};
	private static void excute(String paramString, Runnable paramRunnable) {
		Log.i(TAG, "excute : " + paramString);
		String[] arrayOfString = paramString.split(" ");
		VideoUtils.ExecuteBinaryResponseListener executeBinaryResponseListener = new VideoUtils.ExecuteBinaryResponseListener(
				paramString, paramRunnable);
		FFmpeg localFFmpeg;
		if (arrayOfString.length != 0) {
			localFFmpeg = getFFmpeg();
			if (localFFmpeg == null){
				Log.i(TAG, "excute : " + "localFFmpeg==null");
				return;}
		} else {
			return;
		}
		try {
			localFFmpeg.execute(arrayOfString, executeBinaryResponseListener);
			return;
		} catch (FFmpegCommandAlreadyRunningException localFFmpegCommandAlreadyRunningException) {
			localFFmpegCommandAlreadyRunningException.printStackTrace();
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,localFFmpegCommandAlreadyRunningException.toString() , true);
		}
	}
	public static FFmpeg getFFmpeg() {
		if (ffmpeg == null)
			ffmpeg = FFmpeg.getInstance(BaseApplication.getInstance()
					.getApplicationContext());
		try {
			ffmpeg.loadBinary(new VideoUtils.LoadBinaryResponseListener());
			return ffmpeg;
		} catch (FFmpegNotSupportedException localFFmpegNotSupportedException) {
			Log.i(TAG, "getFFmpegInstance", localFFmpegNotSupportedException);
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,localFFmpegNotSupportedException.toString() , true);
		}
		return null;
	}	
	
	public static void doTask(ArrayList<String> tasks, Runnable paramRunnable){
		new VideoRunner(tasks.iterator(), paramRunnable, tasks.size()).run();
	}
	
	public static void processTask(ArrayList<String> tasks){
		
		Util.makeNotification(mContext, "壁虎车拍", "您的视频正在处理中");	
		isRunning = true;
		doTask(tasks, new Runnable() {

			@Override
			public void run() {
				
				new Thread(new Runnable() {
					@Override
					public void run() {

						if(taskQueue.size()!=0){
							mHandler.sendEmptyMessage(HANDLE_NEXT);
						}else{
							mHandler.sendEmptyMessage(HANDLE_OVER);
						}
						
						if(Util.mBuilder!=null&&mContext!=null){
							Intent i = new Intent(mContext,MainActivity.class);
							i.putExtra("option", 0);
							PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,i, 0);  
							Util.mBuilder.setProgress(0, 0, false);
							Util.mBuilder.setContentIntent(pendingIntent);
							Util.mBuilder.setContentText("您的视频处理完毕，请到我的车拍中查看");
							Util.manager.notify(1, Util.mBuilder.build());
						}
						
						SharedPreferenceHelper.save(AppSetting.EXIST_UNREAD_VIDEO, true);
						
						Intent b = new Intent();
						b.setAction(VideoFragment.ACTION_REFRESH_VIDEO_LIST);
						mContext.sendBroadcast(b);
						
						new DeleteThread().run();
					}
				}).run();
			}
		});
	
	}

		
	public static void addTask(ArrayList<String> task){
		
		taskQueue.add(task);
		if(!isRunning){
			mHandler.sendEmptyMessage(HANDLE_NEXT);
		}
		
	}
	public static class DeleteThread extends Thread {
		@Override
		public void run() {
			//Util.deleteOldFiles();
			
			CursorList result=Query.many(
					VideoEntity.class,
					"select * from "
							+ "video"
							+ " order by startTime desc", new Object[0]).get();
			List<VideoEntity> videoList=result.asList();
			int size = videoList.size();
			int max = Config.getConfig(mContext).getBigVideoCount();
			if(size > max){
				for(int i=size-1; i> max-1; i--){
					VideoEntity tmp = videoList.get(i);
					VideoEntity.delete(tmp);
				}
			}
			
			Log.i(TAG, "delete file ...");
		}
	}	
}
