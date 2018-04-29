package com.richard.record.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.format.Formatter;

/**
 * 
 * @author Jacks gong
 * @data 2014-6-6 上午1:33:23
 * @Describe
 */
public class SDCardTool {

	/**
	 * sdcard
	 */
	public static final String ROOT_DIR = Environment.getExternalStorageDirectory().toString();

	/**
	 * 应用目录
	 */
	public static final String APP_FOLDER = ROOT_DIR + "/beehootravel/";

	public static final String RECORD_AUDIO_FOLDER = APP_FOLDER + "record/.audio/";

	/**
	 * 缓存目录
	 */
	public static final String CACHE_FOLDER = APP_FOLDER + "cache/";

	/**
	 * 音频文件缓存目录
	 */
	public static final String MEDIA_CACHE_FOLDER = CACHE_FOLDER + "media/";

	/**
	 * 图片文件缓存目录
	 */
	public static final String IMAGE_CACHE_FOLDER = CACHE_FOLDER + ".image/";

	/** 动态图片缓存目录 */
	public static final String TREND_IMAGE_CACHE_FOLDER = IMAGE_CACHE_FOLDER + "trend/";

	/** 用户头像缓存目录 */
	public static final String USER_HEAD_IMAGE_CACHE_FOLDER = IMAGE_CACHE_FOLDER + "user/";

	/**
	 * 下载目录
	 */
	public static final String DOWNLOAD_FOLDER = APP_FOLDER + "download/";

	/**
	 * 音频文件下载目录
	 */
	public static final String MEDIA_DOWNLOAD_FOLDER = DOWNLOAD_FOLDER + "media/";

	/**
	 * 安装包下载目录
	 */
	public static final String APK_DOWNLOAD_FOLDER = DOWNLOAD_FOLDER + "apk/";

	/**
	 * 日志目录
	 */
	public static final String LOG_FOLDER = APP_FOLDER + "log/";

	private SDCardTool() {

	}

	/**
	 * 判断是否存在SDCard
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	/**
	 * SDCard剩余大小
	 * 
	 * @return 字节
	 */
	public static long getAvailableSize() {
		if (hasSDCard()) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				return availableBlocks * blockSize;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 是否有足够的空间抓违章
	 * 最小10M
	 * @return
	 */
	public static boolean hasEnoughSpace() {
		return getAvailableSize() > 1024 * 1024 * 10;
	}

	/**
	 * 是否有足够的空间启动行车记录仪
	 * 最小1G
	 * @return
	 */
	public static boolean hasEnoughSpaceStartRecord() {
		return getAvailableSize() > 1024 * 1024 * 1024;
	}

	/**
	 * SDCard总容量大小
	 * 
	 * @return 字节
	 */
	public static long getTotalSize() {
		if (hasSDCard()) {
			try {
				File path = Environment.getExternalStorageDirectory();
				StatFs stat = new StatFs(path.getPath());
				long blockSize = stat.getBlockSize();
				long totalBlocks = stat.getBlockCount();
				return totalBlocks * blockSize;

			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 格式转化为MB格式
	 * 
	 * @param context
	 * @param size
	 * @return
	 */
	public static String formatSize(Context context, long size) {
		return Formatter.formatFileSize(context, size);
	}

	
	/**
	 * isRemoveable =true 是外置SDcard; =fase 是内置SDcard
	 * @author Administrator
	 *
	 */
	public static class StorageInfo {
		public String path;
		public String state;
		public boolean isRemoveable;

		public StorageInfo(String path) {
			this.path = path;
		}

		public boolean isMounted() {
			return "mounted".equals(state);
		}
	}
	
	
	public static List listAvaliableStorage(Context context) {
		ArrayList storagges = new ArrayList();
		StorageManager storageManager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		try {
			Class<?>[] paramClasses = {};
			Method getVolumeList = StorageManager.class.getMethod(
					"getVolumeList", paramClasses);
			getVolumeList.setAccessible(true);
			Object[] params = {};
			Object[] invokes = (Object[]) getVolumeList.invoke(storageManager,
					params);
			if (invokes != null) {
				StorageInfo info = null;
				for (int i = 0; i < invokes.length; i++) {
					Object obj = invokes[i];
					Method getPath = obj.getClass().getMethod("getPath",
							new Class[0]);
					String path = (String) getPath.invoke(obj, new Object[0]);
					info = new StorageInfo(path);
					File file = new File(info.path);
					if ((file.exists()) && (file.isDirectory())
							&& (file.canWrite())) {
						Method isRemovable = obj.getClass().getMethod(
								"isRemovable", new Class[0]);
						String state = null;
						try {
							Method getVolumeState = StorageManager.class
									.getMethod("getVolumeState", String.class);
							state = (String) getVolumeState.invoke(
									storageManager, info.path);
							info.state = state;
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (info.isMounted()) {
							info.isRemoveable = ((Boolean) isRemovable.invoke(
									obj, new Object[0])).booleanValue();
							storagges.add(info);
						}
					}
				}
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		storagges.trimToSize();

		return storagges;
	}	

	
}
