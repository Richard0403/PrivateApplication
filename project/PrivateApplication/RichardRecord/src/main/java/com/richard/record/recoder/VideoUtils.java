package com.richard.record.recoder;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.TextureView;

import com.richard.record.base.BaseApplication;
import com.richard.record.entity.EventVideoEntity;
import com.richard.record.entity.VideoEntity;
import com.richard.record.constants.AppSetting;
import com.richard.record.utils.FileUtils;
import com.richard.record.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import se.emilsjolander.sprinkles.CursorList;



public class VideoUtils extends LoadBinaryResponseHandler {
	private static FFmpeg ffmpeg;

	private static String TAG = "VideoUtils";
	public static class PicRunner implements Runnable {
		Iterator iterator;
		Runnable runnable;
		int size,i;
		 PicRunner(Iterator paramIterator, Runnable paramRunnable) {
			iterator = paramIterator;
			runnable = paramRunnable;

		}

		public void run() {
						
			if (!iterator.hasNext()) {
				runnable.run();
				return;
			}
			VideoUtils.excute((String) iterator.next(), this);
		}
	};
	public static class VideoRunner implements Runnable {
		Iterator iterator;
		Runnable runnable;
		int size,i;
		 CursorList<EventVideoEntity> cList;
		VideoRunner(Iterator paramIterator, Runnable paramRunnable, CursorList<EventVideoEntity> enventList) {
			iterator = paramIterator;
			runnable = paramRunnable;
			this.size=enventList.size()*7;
			this.cList=enventList;
		}

		public void run() {
			
//			while(iterator.hasNext()){
//				int progress=i*100/(size*2);
//				Log.i("VedioUtil", "==="+i);
//				i+=1;
//				Util.mBuilder.setProgress(100, progress, false);
//				Util.manager.notify(1, Util.mBuilder.build());
//				VideoUtils.excute((String)iterator.next(), this);
//			}
			
//			PicHandleUtils.VedioToPicAndMerge(cList);
//			runnable.run();
			
			
			if (!iterator.hasNext()) {
//				PicHandleUtils.VedioToPicAndMerge(cList);
//				if(FileUtil.fileIsExists(cList.get(0).originalVideoPath)){
//					new File(cList.get(0).originalVideoPath).delete();
//				}
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
			VideoUtils.excute((String) iterator.next(), this);
		}
	};

	public static class LoadBinaryResponseListener extends
			LoadBinaryResponseHandler {
		@Override
		public void onFailure() {
			Log.e(TAG, "getFFmpegInstance onFailure");
		}
	};

	public static class ExecuteBinaryResponseListener extends
			ExecuteBinaryResponseHandler {

		String TAG = "ExecuteBinary";
		String cmd;
		long b = System.currentTimeMillis();
		Runnable runnable;

		// private Logger e = new FileLogger("/sensor/log/", "ffmpeg");

		public ExecuteBinaryResponseListener(String paramString,
				Runnable paramRunnable) {
			cmd = paramString;
			runnable = paramRunnable;
		}

		public void onStart() {
			Log.i(TAG, "onStart:");
		}

		public void onFailure(String paramString) {
			Log.i(TAG, "onFailure:" + paramString);
		}

		public void onFinish() {
			Log.i(TAG, "onFinish:");
			this.runnable.run();
		}

		public void onSuccess(String paramString) {
			Log.i(TAG, "onSuccess:" + paramString);
		}

		public void onProgress(String paramString) {
			Log.i(TAG, "onProgress:" + paramString);
		}
	};
	public static String formatVideoThumb(VideoEntity enventVideo) {
		if(enventVideo.video_transpose==270){
			Object[] arrayOfObject = new Object[4];
//			arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo+ enventVideo.length-1);
			arrayOfObject[0] = formatTime(1);
			arrayOfObject[1] = enventVideo.path;
			arrayOfObject[2] = enventVideo.picture;
		return String.format("-ss %s -i %s -vframes 1 -f image2 -y %s",
				arrayOfObject);
		}else{
			Object[] arrayOfObject = new Object[4];
//			arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo+ enventVideo.length-1);
			arrayOfObject[0] = formatTime(1);
			arrayOfObject[1] = enventVideo.path;
			
			
			switch(enventVideo.video_transpose){
			case 0:
				arrayOfObject[2] = "-vf transpose=1";
				break;
			case 90:
				arrayOfObject[2] = "-vf transpose=2,transpose=2";
				break;
			case 180:
				arrayOfObject[2] = "-vf transpose=2";
				break;
			
			}	
			arrayOfObject[3] = enventVideo.picture;
			return String.format("-ss %s -i %s %s -vframes 1 -f image2 -y %s",
					arrayOfObject);			
		}
	}
	public static String formatEnventVideo(EventVideoEntity enventVideo) {
		Object[] arrayOfObject = new Object[4];
		arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo);
		arrayOfObject[1] = enventVideo.originalVideoPath;
		arrayOfObject[2] = enventVideo.length;
		arrayOfObject[3] = enventVideo.savedPath;
		return String.format("-ss %s -i %s -vcodec copy -acodec copy -t %d %s",
				arrayOfObject);
	}
	public static String formatEnventVideoThumb(EventVideoEntity enventVideo) {

		if(enventVideo.video_transpose==270){
			Object[] arrayOfObject = new Object[4];
//			arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo+ enventVideo.length-1);
			arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo+ 1);
			arrayOfObject[1] = enventVideo.originalVideoPath;
			arrayOfObject[2] = enventVideo.picture;
		return String.format("-ss %s -i %s -vframes 1 -f image2 -y %s",
				arrayOfObject);
		}else{		
			Object[] arrayOfObject = new Object[4];
//			arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo+ enventVideo.length-1);
			arrayOfObject[0] = formatTime(enventVideo.startPositionOfOriginalVideo+ 1);
			arrayOfObject[1] = enventVideo.originalVideoPath;
			
			switch(enventVideo.video_transpose){
			case 0:
				arrayOfObject[2] = "-vf transpose=1";
				break;
			case 90:
				arrayOfObject[2] = "-vf transpose=2,transpose=2";
				break;
			case 180:
				arrayOfObject[2] = "-vf transpose=2";
				break;
			
			}
		
			arrayOfObject[3] = enventVideo.picture;		
			
			return String.format("-ss %s -i %s %s -vframes 1 -f image2 -y %s",
					arrayOfObject);			
		}
	}

	public static String formatTime(int paramInt) {
		int i = paramInt / 60 / 60;
		int j = (paramInt - 60 * (i * 60)) / 60;
		int k = paramInt - 60 * (i * 60) - j * 60;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = Integer.valueOf(i);
		arrayOfObject[1] = Integer.valueOf(j);
		arrayOfObject[2] = Integer.valueOf(k);
		return String.format("%02d:%02d:%02d", arrayOfObject);
	}

	/**
	 *
	 * @param videopath
	 * @param time
	 * @param picturepath
     * @return
     */
	private static String formatPic(String videopath, int time,
			String picturepath) {
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = formatMilSecondTime(time);
		arrayOfObject[1] = videopath;
		arrayOfObject[2] = picturepath;
		return String.format("-ss %s -i %s -vframes 1 -f image2 -y %s",
				arrayOfObject);
	}

	/**
	 *
	 * @param videoEntity
	 * @param enventList
	 * @param paramRunnable
     */
	public static void excute(VideoEntity videoEntity,
			CursorList<EventVideoEntity> enventList, Runnable paramRunnable) {
		if ((enventList == null) || (enventList.size() == 0))
			return;
//TODO
		ArrayList localArrayList = new ArrayList(enventList.size());
		EventVideoEntity eventVideoEntity;
		for (int i = 0; i < enventList.size(); i++) {
			String str = formatEnventVideo((EventVideoEntity) enventList.get(i));
			Log.d(TAG, "caption video cmd: "+ str);
			if (!Util.isEmpty(str))
				localArrayList.add(str);
			
			String strPic = formatEnventVideoThumb((EventVideoEntity) enventList.get(i));
			if (!Util.isEmpty(strPic))
				localArrayList.add(strPic);
			
			
//			eventVideoEntity = enventList.get(i);
//			int len = eventVideoEntity.length;
//			int start = 0;
//			float piece = (float) (len/5.0);
//			String morePic="";
//			for(int j=start; j<5; j++){			
//				String pic_path = eventVideoEntity.picture.replace(".jpg", "-"+j+".jpg");			
//				String cmd = formatPic(eventVideoEntity.savedPath, (int)(j*1000*piece), pic_path);	
//				localArrayList.add(cmd);
//				morePic=morePic+pic_path+",";
//			}
//			eventVideoEntity.videoMorePic=morePic;
//			eventVideoEntity.save();
			
		}
		new VideoRunner(localArrayList.iterator(), paramRunnable,enventList).run();
	}

	/**
	 * 提取图片
	 * 
	 * @param video_path
	 * @param time
	 * @param picturepath
	 * @param paramRunnable
	 */
	public static void getPicture(String video_path, int time,
			String picturepath, Runnable paramRunnable) {
		excute(formatPic(video_path, time, picturepath), paramRunnable);
	}
	
//	public static void getPictureList(EventVideoEntity eventVideoEntity, Runnable paramRunnable) {
//		//TODO
//		int len = eventVideoEntity.length;
////		int start =0;
////		if (len >=10){
////			start = 2;
////		}
//		
//		int start = 0;
//		float piece = (float) (len/5.0);
//		
//		ArrayList localArrayList = new ArrayList();
//		String morePic="";
//		for(int i=start; i<5; i++){			
//			String pic_path = eventVideoEntity.picture.replace(".jpg", "-"+i+".jpg");			
//			String cmd = formatPic(eventVideoEntity.savedPath, (int)(i*1000*piece), pic_path);	
//			localArrayList.add(cmd);
//			morePic=morePic+pic_path+",";
//		}
//		eventVideoEntity.videoMorePic=morePic;
//		eventVideoEntity.save();
//		new PicRunner(localArrayList.iterator(), paramRunnable).run();
//		
//	}

	/**
	 * 
	 * @param cmd
	 * @param paramExecuteBinaryResponseHandler
	 */
	public static void excute(String cmd,
			ExecuteBinaryResponseHandler paramExecuteBinaryResponseHandler) {
		String[] cmds = cmd.split(" ");
		FFmpeg localFFmpeg;
		if (cmds.length != 0) {
			localFFmpeg = getFFmpeg();
			if (localFFmpeg == null)
				return;
		} else {
			return;
		}
		try {
			localFFmpeg.execute(cmds, paramExecuteBinaryResponseHandler);
			return;
		} catch (FFmpegCommandAlreadyRunningException localFFmpegCommandAlreadyRunningException) {
			localFFmpegCommandAlreadyRunningException.printStackTrace();
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,localFFmpegCommandAlreadyRunningException.toString() , true);
		}
	}

	public static void excute(String videoPath, String paramString2,
			int starttime, int duration,
			ExecuteBinaryResponseHandler paramExecuteBinaryResponseHandler) {
		Object[] arrayOfObject = new Object[4];
		arrayOfObject[0] = videoPath;
		arrayOfObject[1] = formatTime(starttime);
		arrayOfObject[2] = formatTime(duration);
		arrayOfObject[3] = paramString2;
		String[] arrayOfString = String.format(
				"-i %s -ss %s -t %s -async 1 -strict -2 %s", arrayOfObject)
				.split(" ");
		FFmpeg localFFmpeg;
		if (arrayOfString.length != 0) {
			localFFmpeg = getFFmpeg();
			if (localFFmpeg == null)
				return;
		} else {
			return;
		}
		try {
			localFFmpeg.execute(arrayOfString,
					paramExecuteBinaryResponseHandler);
			return;
		} catch (FFmpegCommandAlreadyRunningException localFFmpegCommandAlreadyRunningException) {
			localFFmpegCommandAlreadyRunningException.printStackTrace();
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,localFFmpegCommandAlreadyRunningException.toString() , true);
		}
	}

	/**
	 * 提取视频
	 * 
	 * @param videopath
	 * @param targetpath
	 * @param starttime
	 * @param duration
	 * @param paramRunnable
	 */
	public static void cutVideo(String videopath, String targetpath,
			int starttime, int duration, Runnable paramRunnable) {
		Object[] arrayOfObject = new Object[4];
		arrayOfObject[0] = videopath;
		arrayOfObject[1] = formatTime(starttime);
		arrayOfObject[2] = Integer.valueOf(duration);
		arrayOfObject[3] = targetpath;
		String str = String.format(
				"-y -i %s -ss %s -t %d -vcodec copy -acodec copy %s",
				arrayOfObject);
		String[] arrayOfString = str.split(" ");
		ExecuteBinaryResponseListener executeBinaryResponseListener = new ExecuteBinaryResponseListener(
				str, paramRunnable);
		FFmpeg localFFmpeg;
		if (arrayOfString.length != 0) {
			localFFmpeg = getFFmpeg();
			if (localFFmpeg == null)
				return;
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

	/**
	 *
	 * @return
     */
//	public static void cutTaskList(ArrayList<String> taskList, Runnable runnable) {
//		new VideoRunner(taskList.iterator(), runnable).run();
//	}

	public static boolean isRunning() {
		return getFFmpeg().isFFmpegCommandRunning();
	}

	public static FFmpeg getFFmpeg() {
		if (ffmpeg == null)
			ffmpeg = FFmpeg.getInstance(BaseApplication.getInstance()
					.getApplicationContext());
		try {
			ffmpeg.loadBinary(new LoadBinaryResponseListener());
			return ffmpeg;
		} catch (FFmpegNotSupportedException localFFmpegNotSupportedException) {
			Log.i(TAG, "getFFmpegInstance", localFFmpegNotSupportedException);
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,localFFmpegNotSupportedException.toString() , true);
		}
		return null;
	}

	private static String formatMilSecondTime(int paramInt) {
		int i = paramInt / 1000;
		int j = i / 60 / 60;
		int k = (i - 60 * (j * 60)) / 60;
		int m = i - 60 * (j * 60) - k * 60;
		Object[] arrayOfObject = new Object[4];
		arrayOfObject[0] = Integer.valueOf(j);
		arrayOfObject[1] = Integer.valueOf(k);
		arrayOfObject[2] = Integer.valueOf(m);
		arrayOfObject[3] = Integer.valueOf(paramInt % 1000);
		return String.format("%02d:%02d:%02d.%03d", arrayOfObject);
	}

	private static void excute(String paramString, Runnable paramRunnable) {
		Log.i(TAG, "excute : " + paramString);
		String[] arrayOfString = paramString.split(" ");
		ExecuteBinaryResponseListener executeBinaryResponseListener = new ExecuteBinaryResponseListener(
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

	public static void saveThumb(TextureView textureView, String pic_path) {
		if ( textureView == null)
			return;
		while (true) {
			try {
				Bitmap localBitmap = textureView.getBitmap();
				File localFile2 = new File(pic_path);
				FileOutputStream localFileOutputStream = new FileOutputStream(
						localFile2);
				// localBitmap.compress(Bitmap.CompressFormat.PNG, 100,
				// localFileOutputStream);
				localBitmap.compress(Bitmap.CompressFormat.PNG, 30,
						localFileOutputStream);
				localFileOutputStream.close();
				// eventvideo.picture = localFile2.getPath();
				// eventvideo.save();
				return;

			} catch (Exception localException) {
				localException.printStackTrace();
				FileUtils.writeFile(AppSetting.APP_CATCH_LOG,localException.toString() , true);
			}
		}
	}
	
	public static boolean isSupportDevice(){
		if(null!=getFFmpeg()){
			return true;
		}
		return false;		
	} 
	
	public static Size getMaxSupportedVideoSizes(Camera.Parameters parameters){
		List<Size> list = parameters.getSupportedVideoSizes();
		if(list==null)
			list=parameters.getSupportedPreviewSizes();

		Size maxSize=null;
		maxSize = list.get(0);
		for(int i=1; i< list.size(); i++)
		{
			if(list.get(i).width>maxSize.width){
				maxSize = list.get(i);
			}
		}
		return maxSize;
		
	}
	
	public static int getMaxSupportedPreviewFrameRates(Camera.Parameters parameters){
		List<Integer> frameRatelist = parameters.getSupportedPreviewFrameRates();
		if (frameRatelist ==null) return 0;
		int maxSize = frameRatelist.get(0);
		for(int i=1; i< frameRatelist.size(); i++)
		{
			if(frameRatelist.get(i)>maxSize){
				maxSize = frameRatelist.get(i);
			}
		}
		return maxSize;
		
	}
	
}
