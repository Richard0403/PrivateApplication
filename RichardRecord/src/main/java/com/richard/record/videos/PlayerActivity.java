package com.richard.record.videos;


import java.io.File;
import java.text.SimpleDateFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.richard.record.R;
import com.richard.record.base.BaseActivity;
import com.richard.record.entity.EventVideoEntity;
import com.richard.record.entity.VideoEntity;
import com.richard.record.fragments.VideoFragment;
import com.richard.record.utils.FileUtils;


//import tv.danmaku.ijk.media.widget.VideoView;

public class PlayerActivity extends BaseActivity {


	
	private String videoPath;
	private long videoTime;
	private String videoAddress;
	private String videoPic;
	
	private TextureView mTextureView;
	private SeekBar skbProgress;
	private PlayerHelper player;
//	private LinearLayout ll_shoot;
	private ImageView iv_surfaceView;
	TextView tv_total_time;
	TextView tv_time;

	private boolean status = true;
	
	EventVideoEntity event_video;
	
	int playtime =0;
	String pictureShotPath;
	
	Context mContext;
	
	private RelativeLayout rl_videoview;
	ImageView iv_play;
	
	ImageView iv_btn_play;
	
	private static final int SHOW_VIEW =0;
	private static final int HIDE_VIEW = 1;
	private static final int UPDATE_TIME =3;
	
	RelativeLayout head_relative;
	
	RelativeLayout bottom_relative;
	RelativeLayout rl_del;
	
	Object video;
	private String imageUrl;
	
	int video_transpose =270;
	
	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			switch (msg.what) {

			case SHOW_VIEW:
				if(!status){
        		head_relative.setVisibility(View.VISIBLE);
        		bottom_relative.setVisibility(View.VISIBLE);
				}
				break;
			case HIDE_VIEW:
				if(status){
        		head_relative.setVisibility(View.INVISIBLE);
        		bottom_relative.setVisibility(View.INVISIBLE);
				}
				break;
			case UPDATE_TIME:
				tv_time.setText(formatTime(msg.arg2));
				tv_total_time.setText(formatTime(msg.arg1));
				break;
			default:
				break;
			}
		}

	};	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		setContentView(R.layout.activity_video_player);
		
		mContext = this;
	
//		this.mVideoView.setMediaController(this.mMediaController);
//		this.mVideoView.setMediaBufferingIndicator(this.mBufferingIndicator);	
//		this.mVideoView.setVideoPath(videoPath);
		
		video = getIntent().getSerializableExtra("video");
		
		if(video instanceof EventVideoEntity){
			event_video = (EventVideoEntity)video;
			videoPath = event_video.savedPath;
			videoTime = event_video.length;
			videoAddress = event_video.city +  event_video.street;
			videoPic = event_video.picture;
			video_transpose = event_video.video_transpose;
		}else if(video instanceof VideoEntity){
			VideoEntity event_video = (VideoEntity)video;
			videoPath = event_video.path;
			videoTime = (event_video.endTime - event_video.startTime)/1000;	
			videoAddress = event_video.city +  event_video.street;
			videoPic = event_video.picture;
			video_transpose = event_video.video_transpose;
		}
		if(video_transpose==270 || video_transpose == 90){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			setContentView(R.layout.activity_video_player);
		}else{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			setContentView(R.layout.activity_video_player_v);
		}
		
		initView();	
		
		player = new PlayerHelper(mContext, mTextureView, skbProgress, videoPath,new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				setButtonStatus(false);
				mHandler.sendEmptyMessage(SHOW_VIEW);
			}
		});
		
		mHandler.sendEmptyMessageDelayed(HIDE_VIEW, 2000);
	}
	
	protected void initView() {
		// TODO Auto-generated method stub

		

		iv_surfaceView=(ImageView) findViewById(R.id.iv_surfaceView);
		if(event_video!=null && event_video.picture!=null )
//		iv_surfaceView.setImageBitmap(ImageUtil.getimage(event_video.picture));
		ImageLoader.getInstance().displayImage("file:///"+event_video.picture, iv_surfaceView);
		
		iv_surfaceView.setVisibility(View.GONE);
		mTextureView = (TextureView) this.findViewById(R.id.surfaceView); 
		//ll_shoot=(LinearLayout) findViewById(R.id.ll_shoot);
		//ll_shoot.setOnClickListener(new ClickEvent());
		skbProgress=(SeekBar) findViewById(R.id.skbProgress);
		
		tv_total_time = (TextView) findViewById(R.id.tv_total_time);
		tv_time = (TextView) findViewById(R.id.tv_time);
		
		tv_total_time.setText(formatTime((int)videoTime));

		tv_time.setText(formatTime(0));
		
		rl_del = (RelativeLayout) this.findViewById(R.id.rl_del);
		

		
		rl_del.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//删除视频
				final AlertDialog  myDialog;
				myDialog = new AlertDialog.Builder(mContext).create();  
				myDialog.setCanceledOnTouchOutside(false);
				myDialog.show();  
				myDialog.getWindow().setContentView(R.layout.common_dialog);
				TextView comfirm=(TextView) myDialog.getWindow().findViewById(R.id.tv_confirm); 
				comfirm.setOnClickListener(new OnClickListener() {
					@Override  
					public void onClick(View v) {
						//删除文件
						myDialog.dismiss();
						if(video instanceof EventVideoEntity){
							new DelVideo().execute((EventVideoEntity)video);
						}else if(video instanceof VideoEntity){
							new DelOriVideo().execute((VideoEntity)video);
						}
						//刷新数据
						Intent b = new Intent();
						b.setAction(VideoFragment.ACTION_REFRESH_VIDEO_LIST);
						mContext.sendBroadcast(b);

					}  
				});  
				TextView cancle=(TextView) myDialog.getWindow().findViewById(R.id.tv_cancle); 
				cancle.setOnClickListener(new OnClickListener() {
					@Override  
					public void onClick(View v) {  
						myDialog.dismiss();
					}  
				}); 
			}
			
		});
		
//		tv_add_to_pic = (TextView) findViewById(R.id.tv_add_to_pic);
//		tv_add_to_pic.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				saveToGallery(videoPath);
//				ToastUtil.show(mContext, "已添加到相册！");
//			}
//		});
		
		//btn_player =  (ImageView) this.findViewById(R.id.btn_player); 
		//btn_player.setOnClickListener(new ClickEvent());
		
		iv_btn_play = (ImageView) this.findViewById(R.id.iv_btn_play);
		iv_btn_play.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!status){
					iv_surfaceView.setVisibility(View.INVISIBLE);
            		status = true;
            		iv_btn_play.setImageResource(R.mipmap.btn_player_pause);
            		iv_play.setVisibility(View.GONE);
            		player.play();  
            		mHandler.sendEmptyMessageDelayed(HIDE_VIEW, 2000);
            	}else{
            		status = false;
            		player.pause();
            		iv_btn_play.setImageResource(R.mipmap.btn_player_start);
            		iv_play.setVisibility(View.VISIBLE);
            		mHandler.sendEmptyMessage(SHOW_VIEW);
            	}
			}
			
		});
		iv_play = (ImageView) this.findViewById(R.id.iv_play); 
		
		rl_videoview = (RelativeLayout) this.findViewById(R.id.rl_videoview); 
		rl_videoview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!status){
					iv_surfaceView.setVisibility(View.INVISIBLE);
            		status = true;
            		iv_btn_play.setImageResource(R.mipmap.btn_player_pause);
            		iv_play.setVisibility(View.GONE);
            		player.play(); 
            		mHandler.sendEmptyMessageDelayed(HIDE_VIEW, 2000);
            	}else{
            		status = false;
            		player.pause();
            		iv_btn_play.setImageResource(R.mipmap.btn_player_start);
            		iv_play.setVisibility(View.VISIBLE);
            		mHandler.sendEmptyMessage(SHOW_VIEW);
            	}
			}
			
		});
		
		
		head_relative = (RelativeLayout) this.findViewById(R.id.ic_header); 
		
		
		bottom_relative  = (RelativeLayout) this.findViewById(R.id.bottom_relative); 
		

		TextView tv_delete= (TextView) this.findViewById(R.id.tv_delete);
		tv_delete.setTextColor(getResources().getColor(R.color.white));
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		player.stop();
	}
	class ClickEvent implements OnClickListener {  
		  
        @Override  
        public void onClick(View arg0) {  
        	switch (arg0.getId()) {
//			case R.id.btn_player:
//				if (!status){
//					iv_surfaceView.setVisibility(View.INVISIBLE);
//            		setButtonStatus(true);
//            		
//            		player.playUrl(videoPath);  
//            	}else{
//            		setButtonStatus(false);
//            		player.pause();
//            	}    
//				break;
//			case R.id.ll_shoot:
//				//startActivity(new Intent(PlayerActivity.this,UploadEventActivity.class));
//				
//				new Thread(takePictureRunnable).run();
//				
//				break;
			case R.id.head_left:
				if(player!=null)
				player.stop();
				
				finish();
				break;
//			case R.id.head_right_text:
//				Intent report=new Intent(mContext,UploadEditActivity.class);
//				report.putExtra("video", event_video);
//				mContext.startActivity(report);
//				
//				break;
			default:
				break;
			}
  
        }  
    }  
  
 
    public void setButtonStatus(boolean play){
    	status = play;
    	if(!status){
    		iv_play.setVisibility(View.VISIBLE);
    		iv_btn_play.setImageResource(R.mipmap.btn_player_start);
    	}else{
    		iv_play.setVisibility(View.GONE);
    		iv_btn_play.setImageResource(R.mipmap.btn_player_pause);
    	}
    	
    }


	public void updateListener(int duration, int currentTime){
		Message msg = new Message();
		msg.what = UPDATE_TIME;
		msg.arg1 = duration;
		msg.arg2 = currentTime;
		mHandler.sendMessage(msg);
	}

	

	/**
	 * int 转化成 格式：   分钟:秒
	 * @param paramInt
	 * @return
	 */
	private String formatTime(int paramInt) {
		int i = paramInt / 60 ;
		int j = paramInt % 60 ;
		Object[] arrayOfObject = new Object[3];
		arrayOfObject[0] = Integer.valueOf(i);
		arrayOfObject[1] = Integer.valueOf(j);
		return String.format("%02d:%02d", arrayOfObject);
	}
	private class DelVideo extends AsyncTask<EventVideoEntity, Void, EventVideoEntity>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			((BaseActivity) mContext).showLoadingDialog(mContext.getResources().getString(R.string.del_going));
		}
		@Override
		protected EventVideoEntity doInBackground(EventVideoEntity... params) {
			EventVideoEntity video=params[0];
			
			if(FileUtils.isFileExist(video.savedPath)){
				new File(video.savedPath).delete();
			}
			
			if(FileUtils.isFileExist(video.picture)){
				new File(video.picture).delete();
			}
			
			if(FileUtils.isFileExist(video.videoMergePic)){
				new File(video.videoMergePic).delete();
			}
			
			int morePicLength=video.videoMorePic.length()-1;
			if(morePicLength>0){
				String[] pic_paths=video.videoMorePic.substring(0, morePicLength).split(",");
				for(String path:pic_paths){
					if(FileUtils.isFileExist(path)){
						new File(path).delete();
					}
				}
			}
			
			video.delete();
			
			return null;
		}
		@Override
		protected void onPostExecute(EventVideoEntity result) {
			super.onPostExecute(result);
			finish();
//			((BaseActivity) mContext).dismissLoadingDialog();
		}
	}
	
	private class DelOriVideo extends AsyncTask<VideoEntity, Void, VideoEntity> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// ((BaseActivity)
			// mContext).showLoadingDialog(mContext.getResources().getString(R.string.del_going));
		}

		@Override
		protected VideoEntity doInBackground(VideoEntity... params) {
			VideoEntity video = params[0];

			VideoEntity.delete(video);
			return null;
		}

		@Override
		protected void onPostExecute(VideoEntity result) {
			super.onPostExecute(result);
			finish();
			// ((BaseActivity) mContext).dismissLoadingDialog();
		}
	}

}
