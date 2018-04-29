package com.richard.record.videos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayerHelper implements SurfaceTextureListener,
		OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener {
	private int videoWidth;
	private int videoHeight;
	public MediaPlayer mediaPlayer;
	private TextureView mSurfaceView;
	private Surface mSurface;
	private SeekBar skbProgress;
	private Timer mTimer = new Timer();

	private Context mContext;
	private final static String TAG = "PlayerHelper";

	private boolean isChanging = false;

	private String videoUrl;

	private OnCompletionListener completionListener;
	private OnSeekBarChangeListener seekbarChangListener;

	public PlayerHelper(Context context, TextureView surfaceView,
			SeekBar skbProgress, String videoUrl,
			OnCompletionListener completionListener) {
		this.mContext = context;
		this.skbProgress = skbProgress;
		mSurfaceView = surfaceView;
		mSurfaceView.setSurfaceTextureListener(this);
		this.skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		mTimer.schedule(mTimerTask, 0, 100);
		this.videoUrl = videoUrl;
		this.completionListener = completionListener;
	}

	/*******************************************************
	 * 通过定时器和Handler来更新进度条
	 ******************************************************/
	TimerTask mTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (mediaPlayer == null || isChanging)
				return;
			if (/* mediaPlayer.isPlaying() && */skbProgress.isPressed() == false) {
				handleProgress.sendEmptyMessage(0);
			}
		}
	};

	Handler handleProgress = new Handler() {
		public void handleMessage(Message msg) {
			try{
				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					int position = mediaPlayer.getCurrentPosition();
					int duration = mediaPlayer.getDuration();
					if (duration > 0) {
						skbProgress.setProgress(mediaPlayer.getCurrentPosition());
						if (completionListener == null && position > 1) {
							mediaPlayer.pause();
						}
					}
	
				}
			}catch(Exception e){
				
			}
		};
	};

	// *****************************************************
	public void setVideoUrl(String url) {
		this.videoUrl = url;
	}

	public void play() {
		mediaPlayer.start();
	}

	public void playUrl(String videoUrl) {
		try {
			Log.d(TAG, "play url: " + videoUrl);
			if (mediaPlayer.isPlaying())
				mediaPlayer.seekTo(0);
			mediaPlayer.reset();
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {// 错误处理事件
				@Override public boolean onError(MediaPlayer player, int arg1, int arg2) {
				mediaPlayer.release();
				return false;
				}
				});
			File file = new File(videoUrl);
			FileInputStream fis = new FileInputStream(file);
			mediaPlayer.setDataSource(fis.getFD());
			mediaPlayer.prepare();

			// mediaPlayer.setDataSource(videoUrl);
			// mediaPlayer.prepare();// prepare之后自动播放

			this.skbProgress.setMax(mediaPlayer.getDuration());

			// 自动播放
			// mediaPlayer.start();

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void pause() {
		mediaPlayer.pause();
	}

	public void stop() {
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying())
				mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	/**  
	 * 通过onPrepared播放  
	 */
	public void onPrepared(MediaPlayer arg0) {
		videoWidth = mediaPlayer.getVideoWidth();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {
			arg0.start();
		}
		Log.i(TAG, "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onCompletion");
		if (completionListener != null) {
			completionListener.onCompletion(arg0);
		}
		this.skbProgress.setProgress(mediaPlayer.getDuration());
		// ((PlayerActivity) mContext).setButtonStatus(false);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
		skbProgress.setSecondaryProgress(bufferingProgress);
		int currentProgress = skbProgress.getMax()
				* mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		Log.e(currentProgress + "% play", bufferingProgress + "% buffer");

	}

	public int getPlayTime() {
		return skbProgress.getMax() * mediaPlayer.getCurrentPosition()
				/ mediaPlayer.getDuration();
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		mSurface = new Surface(mSurfaceView.getSurfaceTexture());
		try {
			mediaPlayer = new MediaPlayer();
			// mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setSurface(mSurface);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);

			playUrl(videoUrl);

		} catch (Exception e) {
			Log.e("mediaPlayer", "error", e);
		}
		Log.e("mediaPlayer", "surface created");
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub

		surface = null;
		mSurface = null;
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying())
				mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		mTimer.cancel();

		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		// TODO Auto-generated method stub

	}

	class SeekBarChangeEvent implements OnSeekBarChangeListener {
		

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
			if (mediaPlayer != null) {
//				this.progress = progress * mediaPlayer.getDuration()
//						/ seekBar.getMax();
				// Log.i("seekbar", "play progress: " + this.progress);
				if (seekbarChangListener != null) {
					seekbarChangListener.onProgressChanged(seekBar, progress,
							fromUser);
				}
				int duration = mediaPlayer.getDuration();
				int playtime = mediaPlayer.getCurrentPosition();
				Log.i(TAG,"Duration: "+ duration + "----Current: "+ playtime);
				int play_time = new BigDecimal(
						playtime / 1000).setScale(0,
						BigDecimal.ROUND_HALF_UP).intValue();
				int duration_time = new BigDecimal(
						duration / 1000).setScale(0,
						BigDecimal.ROUND_HALF_UP).intValue();
				if(play_time>0){
					((PlayerActivity) mContext).updateListener(duration_time,  play_time);
				}
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			isChanging = true;
			if (seekbarChangListener != null) {
				seekbarChangListener.onStartTrackingTouch(seekBar);
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
			// player.mediaPlayer.seekTo(progress);
			Log.i(TAG, "touch");
			mediaPlayer.seekTo(seekBar.getProgress());
			isChanging = false;
			if (seekbarChangListener != null) {
				seekbarChangListener.onStopTrackingTouch(seekBar);
			}
		}

	}

	public void SetOnSeekBarDragListener(
			OnSeekBarChangeListener seekbarChangListener) {
		this.seekbarChangListener = seekbarChangListener;
	}
}