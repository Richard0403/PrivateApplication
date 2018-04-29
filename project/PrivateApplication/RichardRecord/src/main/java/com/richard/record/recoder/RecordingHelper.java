package com.richard.record.recoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

import com.richard.record.R;
import com.richard.record.base.BaseApplication;
import com.richard.record.constants.Config;
import com.richard.record.entity.EventVideoEntity;
import com.richard.record.entity.VideoEntity;
import com.richard.record.constants.AppSetting;
import com.richard.record.utils.FileUtils;
import com.richard.record.utils.StringUtils;
import com.richard.record.utils.ToastUtil;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.Query;

public class RecordingHelper implements SurfaceTextureListener {

	private static final String TAG = "RecordingHelper";

	// 文件路径
	private String strFilePath = "";
	// 数据上下文
	private Context context = null;
	// 媒体播放对象
	private MediaPlayer mPlayer = null;
	// 媒体录音对象
	private MediaRecorder mRecorder = null;
	// 用来显示视频的一个接口，我靠不用还不行，也就是说用mediarecorder录制视频还得给个界面看
	// 想偷偷录视频的同学可以考虑别的办法。。嗯需要实现这个接口的Callback接口

	TextureView mTextureView;

	private Camera mCamera;
	// private int cameraId;
	CamcorderProfile mCamcorderProfile;

	private boolean startStatus = false;

	protected VideoEntity video;
	protected EventVideoEntity event_video;
	File localFile;

	int mOrientation = 270; //默认
	private boolean isVerticle=false;

	public int event_number = 0;
	public boolean isSurfaceTextureAvaliable = false;
	private Handler mHandler = new Handler() {

	};

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
										  int height) {
		Log.i(TAG, "onSurfaceTextureAvailable");
		isSurfaceTextureAvaliable = true;
		initCamera();
		// startRecording();
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
											int height) {
		Log.i(TAG, "onSurfaceTextureSizeChanged");
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		Log.i(TAG, "onSurfaceTextureDestroyed");
		if(destroyListener!=null){
			destroyListener.onTextureDestroy();
		}

		isSurfaceTextureAvaliable = false;
		if (context != null) {
			context = null;
		}
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
		if (mRecorder != null) {
			mRecorder.setOnErrorListener(null);
			mRecorder.setOnInfoListener(null);
			mRecorder.reset();
			mRecorder.release();
			mRecorder = null;
			if(mCamera!=null){
				mCamera.lock();
			}
		}

		kill_camera();
		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// System.out.println("onSurfaceTextureUpdated onSurfaceTextureUpdated");

	}

	public String getStrFilePath() {
		return strFilePath;
	}

	public void setStrFilePath(String strFilePath) {
		this.strFilePath = strFilePath;
	}

	/*
	 * 音频录制的构造函数
	 */
	public RecordingHelper(Context context, String strFilePath) {
		this.context = context;
		this.strFilePath = strFilePath;
	}

	/*
	 * 视频录制的构造函数
	 */
	public RecordingHelper(Context context, TextureView textureView) {
		this.context = context;
		this.mTextureView = textureView;
		this.mTextureView.setSurfaceTextureListener(this);
	}
	public RecordingHelper(Context context, TextureView textureView, boolean isVerticle) {
		this.context = context;
		this.mTextureView = textureView;
		this.mTextureView.setSurfaceTextureListener(this);
		this.isVerticle = isVerticle;
	}

	/*
	 * 开始录音
	 */
	public boolean startSoundRecording() {
		boolean bResult = false;
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频来源（MIC表示麦克风）
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT); // 设置音频输出格式
		mRecorder.setOutputFile(strFilePath);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); // 设置音频编码
		try {
			mRecorder.prepare();
			mRecorder.start();
			bResult = true;
			startStatus = true;
		} catch (Exception ex) {
			System.out.println("Error：录音失败、" + ex.getMessage());
			bResult = false;
		}
		return bResult;
	}

	/*
	 * 停止录音
	 */
	public boolean stopSoundRecording() {
		boolean bResult = false;
		if (startStatus && mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
			bResult = true;
		} else {
			bResult = false;
		}
		return bResult;
	}

	/*
	 * 开始播放音频
	 */
	public boolean startPlay() {
		boolean bResult = false;
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(strFilePath);
			mPlayer.prepare();
			mPlayer.start();
			bResult = true;
			startStatus = true;
		} catch (Exception ex) {
			System.out.println("Error：播放失败、" + ex.getMessage());
			bResult = false;
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
					ex.toString(), true);
		}
		return bResult;
	}

	/*
	 * 停止播放音频
	 */
	public boolean stopPlay() {
		boolean bResult = false;
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
			bResult = true;
			startStatus = false;
		} else {
			bResult = false;
		}
		return bResult;
	}

	/*
	 * 调用摄像头进行视频录制 nWidth：视频的宽
	 * nHeight：视频的高nRate：视频帧数nType：视频格式（MediaRecorder.OutputFormat.THREE_GPP）
	 */
	// public boolean startVideoRecording(int nWidth, int nHeight, int nRate,
	// int nType) {
	// boolean bResult = false;
	// mRecorder = new MediaRecorder();
	// mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); // 设置视频源为摄像头
	// mRecorder.setOutputFormat(nType);//
	// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
	// mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); //
	// 设置录制的视频编码h263
	// // h264
	// // mRecorder.setVideoSize(nWidth, nHeight);//
	// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
	// mRecorder.setVideoFrameRate(nRate);// 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
	// mRecorder.setOutputFile(strFilePath); // 设置输出路径
	//
	// try {
	// mRecorder.prepare();
	// mRecorder.start();
	// bResult = true;
	// startStatus = true;
	//
	// } catch (Exception ex) {
	// System.out.print("Error：视频录制错误，" + ex.getMessage());
	// bResult = false;
	// }
	// return bResult;
	// }

	/*
	 * 停止视频录制
	 */
	public boolean stopVideoRecording() {
		boolean bResult = false;
		if (startStatus && mRecorder != null) {
			try {
				mRecorder.setOnErrorListener(null);
				mRecorder.setOnInfoListener(null);
				// 停止
				mRecorder.stop();
			} catch (RuntimeException e) {
				FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
						e.toString(), true);
				e.printStackTrace();
				// 如果发生异常，很可能是在不合适的状态执行了stop操作
				// 所以等待一会儿
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					Log.e(TAG, "sleep for second stop error!!");
				}
				try {
					mRecorder.stop();
				} catch (Exception ec) {
					FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
							ec.toString(), true);
					Log.e(TAG, "stop fail2", ec);
				}
			}
			releaseMediaRecorder();
			bResult = true;
			startStatus = false;
			this.video.endTime = System.currentTimeMillis();
//			if (null != BaseApplication.mLocation) {
//				this.video.end_latitude = BaseApplication.mLocation
//						.getLatitude();
//				this.video.end_lontitude = BaseApplication.mLocation
//						.getLongitude();
//				this.video.end_street = BaseApplication.mLocation.getStreet();
//				this.video.city = BaseApplication.mLocation.getCity();
//			}
			this.video.latitude = 0;
			this.video.lontitude = 0;
			this.video.street = "";
			this.video.city = "";

			this.video.save();
			BaseApplication.isRecording = false;
			BaseApplication.curVideoId = "";
		} else {
			bResult = false;
		}
		return bResult;
	}

	private boolean prepareVideoRecorder() {
		localFile = CameraInterface.newFile(2);
		if (localFile == null) {
			ToastUtil.show(context, R.string.videorecording_garbage_postion);
			return false;
		}
		if (mCamera == null) {
			initCamera();
		}

		mRecorder = new MediaRecorder();

		// Step 1: Unlock and set camera to MediaRecorder
		mCamera.unlock();
		mRecorder.setCamera(mCamera);
		if(context!=null){
			if(mOrientation==90){
				mRecorder.setOrientationHint(180);
			}else if(mOrientation==0){
				mRecorder.setOrientationHint(90);
			}else if(mOrientation==180){
				mRecorder.setOrientationHint(270);
			}
		}


		// Step 2: Set sources
		// mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

		// Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
		// mRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
		mRecorder.setVideoEncodingBitRate(8 * 1024 * 1024);
		mRecorder.setMaxDuration(1000*60* Config.getConfig(context).getVideoMaxDuraion());
		mRecorder.setOnInfoListener(mRecorderInfoListen);
		mRecorder.setOnErrorListener((OnErrorListener) context);
		try {
			//获取录像大小合适的方法
			//CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
			//mRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);

			int width = Config.getConfig(context).getVideoWidth();
			int height = Config.getConfig(context).getVideoHeight();

//			if(mOrientation==0||mOrientation==180){
//				width = Config.getConfig(context).getVideoHeight();
//				height = Config.getConfig(context).getVideoWidth();
//			}
			mRecorder.setVideoSize(width, height);


			//Log.i(TAG, "Camera size---width:"+ profile.videoFrameWidth + " heigth:" + profile.videoFrameHeight);
//			CamcorderProfile profile = getCamcorderProfile(Camera.CameraInfo.CAMERA_FACING_BACK);
//			mRecorder.setVideoSize(profile.videoFrameWidth,
//					profile.videoFrameHeight);
//			int maxSupportedPreviewFrameRates = VideoUtils
//					.getMaxSupportedPreviewFrameRates(mCamera.getParameters());
//			if (maxSupportedPreviewFrameRates > 0) {
//				mRecorder.setVideoFrameRate(maxSupportedPreviewFrameRates);
//			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
					e.toString(), true);
		}

		mRecorder.setVideoFrameRate(30);

		// Step 4: Set output file
		mRecorder.setOutputFile(localFile.toString());

		// Step 5: Set the preview output
		// mRecorder.setPreviewDisplay(mTextureView.getSurfaceTexture());

		// Step 6: Prepare configured MediaRecorder
		try {
			mRecorder.prepare();
		} catch (IllegalStateException e) {
			Log.d(TAG,
					"IllegalStateException preparing MediaRecorder: "
							+ e.getMessage());
			releaseMediaRecorder();
			return false;
		} catch (IOException e) {
			Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
			releaseMediaRecorder();
			return false;
		}
		return true;
	}

	/*
	 * 调用摄像头进行视频录制 nWidth：视频的宽
	 */
	public boolean startRecording() {
		boolean bResult = false;
		event_number = 0;
		if (prepareVideoRecorder()) {
			mRecorder.start();
			bResult = true;
			startStatus = true;
		} else {
			bResult = false;
			releaseMediaRecorder();
			return bResult;
		}

		try {
			this.video = new VideoEntity();
			this.video.path = localFile.toString();
			this.video.startTime = System.currentTimeMillis();
//			if (null != BaseApplication.mLocation) {
//				this.video.latitude = BaseApplication.mLocation.getLatitude();
//				this.video.lontitude = BaseApplication.mLocation.getLongitude();
//				this.video.street = BaseApplication.mLocation.getStreet();
//				this.video.city = BaseApplication.mLocation.getCity();
//			}
			this.video.latitude = 0;
			this.video.lontitude = 0;
			this.video.street = "";
			this.video.city = "";


			if(context!=null){
				this.video.video_transpose = this.mOrientation;
			}

			this.video.save();
			this.video.route = this.video.path.replace(".mp4", "_route.txt");
			if (FileUtils.isFileExist(this.video.route)) {
				new File(this.video.route).delete();
			} else {
				FileUtils.writeFile(this.video.route, "");
			}
			this.video.picture = this.video.path.replace(".mp4", ".jpg");
			BaseApplication.curVideoId = this.video.route;
			BaseApplication.isRecording = true;

		} catch (Exception ex) {
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
					ex.toString(), true);
			Log.e(TAG, "Error：视频保存失败, " + ex.getMessage());
			bResult = false;
			return bResult;
		}
		return bResult;
	}

	private void initCamera() {
		if(mCamera!=null)
			return;
		int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
		try {
			if (Camera.getNumberOfCameras() == 2) {
				mCamera = Camera.open(cameraId);
			} else {
				mCamera = Camera.open();
			}

			Camera.Size mSize = null;//相机的尺寸

//			CameraSizeComparator sizeComparator = new CameraSizeComparator();
			Camera.Parameters parameters = mCamera.getParameters();

			if (mSize == null) {
				List<Camera.Size> vSizeList = parameters.getSupportedPreviewSizes();

				List<String> focusModesList = parameters.getSupportedFocusModes();

				//增加对聚焦模式的判断
				if (focusModesList.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
					parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
				} else if (focusModesList.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
					parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				}
				if(isVerticle)
					mCamera.setDisplayOrientation(90);
				mCamera.setParameters(parameters);
			}



//			try {
//				//CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
//				CamcorderProfile profile = getCamcorderProfile(cameraId);
//				Camera.Parameters parameters = mCamera.getParameters();
//				if(parameters!=null){
//					parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);
//					parameters.setJpegQuality(80);
//					mCamera.setParameters(parameters);
//				}
//
//			} catch (Exception e) {
//				// Log.e(TAG, e.getMessage());
//				// FileUtils.writeFile(AppSetting.APP_CATCH_LOG,e.toString()
//				// , true);
//			}

			mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
			mCamera.startPreview(); // 进行预览

		} catch (RuntimeException e) {
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
					e.toString(), true);
			Log.i(TAG, "不能打开摄像头：" + e.getMessage());
			// mCamera = Camera.open(Camera.getNumberOfCameras()-1);
			System.out.println("open()方法有问题");
			if (context != null) {
				ToastUtil.show(this.context,
						R.string.videorecording_cont_start_camera);
				// ((RecordingActivity)context).setStatusText(context.getResources().getString(R.string.videorecording_failed_start_camera));
			}
		} catch (IOException e) {
			Log.i(TAG, "不能打开摄像头：" + e.getMessage());
			FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
					e.toString(), true);
		}
	}



	public void focusOnTouch(MotionEvent event) {
		try {
			Camera.Parameters parameters = mCamera.getParameters();
			Camera.Size paraSize = parameters.getPreviewSize();

			Rect focusRect = calculateTapArea(event.getRawX(), event.getRawY(), 1f, paraSize);
			Rect meteringRect = calculateTapArea(event.getRawX(), event.getRawY(), 1.5f, paraSize);

			Log.i(TAG, focusRect.toString());


			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
			if (parameters.getMaxNumFocusAreas() > 0) {
				List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
				focusAreas.add(new Camera.Area(focusRect, 600));

				parameters.setFocusAreas(focusAreas);
			}

			//		if (parameters.getMaxNumMeteringAreas() > 0) {
			//			List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
			//			meteringAreas.add(new Camera.Area(meteringRect, 600));
			//
			//			parameters.setMeteringAreas(meteringAreas);
			//		}

				mCamera.setParameters(parameters);

		}catch (Exception e){
			Log.i(TAG, "焦点设置失败");
		}

//        mCamera.autoFocus(this);
	}

	private Rect calculateTapArea(float x, float y, float coefficient,Camera.Size paraSize) {
		float focusAreaSize = 300;
		int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

		int centerX = (int) (x / paraSize.width * 2000 - 1000);
		int centerY = (int) (y / paraSize.height * 2000 - 1000);

		int left = clamp(centerX - areaSize / 2, -1000, 1000);
		int right = clamp(left + areaSize, -1000, 1000);
		int top = clamp(centerY - areaSize / 2, -1000, 1000);
		int bottom = clamp(top + areaSize, -1000, 1000);

		return new Rect(left, top, right, bottom);
	}

	private int clamp(int x, int min, int max) {
		if (x > max-10) {
			return max-10;
		}
		if (x < min+10) {
			return min+10;
		}
		return x;
	}













	private void releaseMediaRecorder() {
		if(mRecorder!=null){
			mRecorder.reset();    // clear recorder configuration
			mRecorder.release();   // release the recorder object
			mRecorder = null;
		}
		if(mCamera!=null)
			mCamera.lock(); // lock camera for later use
	}

	private void kill_camera() {
		//TODO


		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
	OnInfoListener mRecorderInfoListen = new OnInfoListener() {

		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			// TODO Auto-generated method stub
			if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED){
				Log.i(TAG,"zhangwuba --- dution retach");
//				stopVideoRecording();
				stopRecording();
				startRecording();
			}
//			if(what == MediaRecorder.MEDIA_ERROR_SERVER_DIED){
//				stopRecording();
//			}
		}
	};

//	OnErrorListener mRecorderErrorListener = new OnErrorListener() {
//
//		@Override
//		public void onError(MediaRecorder arg0, int arg1, int arg2) {
//			// TODO Auto-generated method stub
//			stopRecording();
//		}
//	};


	public VideoEntity getVideo() {
		if (null == mRecorder)
			return null;
		return this.video;
	}

	public void captureVideo() {

		new AsyncTask<Void, Void, Void>() {
			// 运行在ui线程中 ,在调用 doInbackground方法之前执行,
			// 可以做操作ui控件的操作
			@Override
			protected void onPreExecute() {
				super.onPreExecute();

			}

			// 获取实体中的数据，运行在主线程中,ui线程中, 在doInbackground执行完毕后执行
			@Override
			protected void onPostExecute(Void ue) {
				super.onPostExecute(ue);

			}

			// 从网上获取登陆用户的数据实体，后台运行的方法 ,运行在非ui线程 可以执行耗时的操作
			@Override
			protected Void doInBackground(Void... params) {

				if (video == null) {
					return null;
				}

				EventVideoEntity eventVideo = new EventVideoEntity();
				// 所属视频
				eventVideo.videoId = video.id;
				// 位置信息
//				if (null != BaseApplication.mLocation) {
//					eventVideo.latitude = BaseApplication.mLocation
//							.getLatitude();
//					eventVideo.lontitude = BaseApplication.mLocation
//							.getLongitude();
//					eventVideo.street = BaseApplication.mLocation.getStreet();
//					eventVideo.city = BaseApplication.mLocation.getCity();
//				}
				eventVideo.latitude = 0;
				eventVideo.lontitude = 0;
				eventVideo.street = "";
				eventVideo.city = "";

				eventVideo.time = System.currentTimeMillis();
				eventVideo.setVideoParams(video);
				eventVideo.typeDes = "抓拍";
				eventVideo.statusDes = "抓拍";
				eventVideo.videoMorePic = "";

				eventVideo.video_transpose = mOrientation;

				// save event video to db
				eventVideo.save();
				event_video = eventVideo;
				event_number++;
				Log.d(TAG, event_video.toString());

				// new EventThumb().saveThumb(mTextureView, event_video);

				return null;

			}
		}.execute();

	}

	public void handleMediaRecorderError(){
		stopVideoRecording();
		VideoEntity.delete(this.video);

	}
	//TODO
	public void stopRecording() {
		if (mRecorder != null) {
			stopVideoRecording();
			if (this.video == null) {
				return;
			}
			if(this.video.endTime-this.video.startTime<3000){
				VideoEntity.delete(this.video);
				return;
			}

			CursorList localCursorList = Query.many(
					EventVideoEntity.class,
					"select * from "
							+ "event_video"
							+ " where video_id=" + this.video.id
							+ " order by time desc", new Object[0]).get();
			Log.i(TAG, "cut video size : " + event_number);
			Log.i(TAG, "cut video size in DB: " + localCursorList.size());

			ArrayList<String> tasks = new ArrayList<String>();
			String video_thumb = VideoUtils.formatVideoThumb(video);
			tasks.add(video_thumb);

			if (localCursorList.size() > 0) {

				for (int i = 0; i < localCursorList.size(); i++) {
					String str = VideoUtils.formatEnventVideo((EventVideoEntity) localCursorList.get(i));
					Log.d(TAG, "caption video cmd: "+ str);
					if (!StringUtils.isEmpty(str))
						tasks.add(str);

					String strPic = VideoUtils.formatEnventVideoThumb((EventVideoEntity) localCursorList.get(i));
					if (!StringUtils.isEmpty(strPic))
						tasks.add(strPic);

				}
//				LocationService.handleVideo(BaseApplication.getInstance()
//						.getApplicationContext(), this.video, localCursorList);

			}
//			VideoHandler.processTask(tasks);
			VideoHandler.addTask(tasks);
		}
	}

	/**
	 *
	 * @param cameraId
	 * @return
     */
	public CamcorderProfile getCamcorderProfile(int cameraId) {

		CamcorderProfile profile;// = CamcorderProfile.get(cameraId,
		// CamcorderProfile.QUALITY_720P);

		if (CamcorderProfile
				.hasProfile(cameraId, CamcorderProfile.QUALITY_720P)) {
			profile = CamcorderProfile.get(cameraId,
					CamcorderProfile.QUALITY_720P);
		} else {
			profile = CamcorderProfile.get(cameraId,
					CamcorderProfile.QUALITY_HIGH);
		}


		profile.videoBitRate = 5*1024 * 1024;
		profile.fileFormat = MediaRecorder.OutputFormat.MPEG_4;
		profile.videoCodec = MediaRecorder.VideoEncoder.H264;
		// localCamcorderProfile.audioCodec = MediaRecorder.AudioEncoder.HE_AAC;
		profile.audioCodec = MediaRecorder.AudioEncoder.DEFAULT;
		return profile;
	}

	public Runnable captureVideoRunnable = new Runnable() {

		@Override
		public void run() {
			if (mRecorder != null) {
				captureVideo();
			}
		}

	};

	/**
	 * TextureView
	 *
	 * @author Administrator
	 *
	 */
	public class EventThumb {

		public void saveThumb(TextureView textureView,
							  EventVideoEntity eventvideo) {
			if (eventvideo == null || textureView == null)
				return;
			while (true) {
				try {
					Bitmap localBitmap = textureView.getBitmap();
					String dir = eventvideo.picture;
					File localFile2 = new File(eventvideo.picture);
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
					FileUtils.writeFile(AppSetting.APP_CATCH_LOG,
							localException.toString(), true);
				}
			}
		}

	}

	private Runnable getThumbOnEvent = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			VideoUtils.getPicture(video.path, event_video.length,
					event_video.picture, new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

						}
					});
		}

	};


	public void setContext(Context context){
		this.context = context;
	}
	public void setOrientation(int orientation){
		this.mOrientation = orientation;
	}


	private TextureDestroyListener destroyListener;
	public interface TextureDestroyListener{
		public void onTextureDestroy();
	}
	public void setOnTextureDestroyListener(TextureDestroyListener destroyListener){
		this.destroyListener = destroyListener;
	}
}
