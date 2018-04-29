package com.richard.record.videos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.richard.record.R;
import com.richard.record.entity.EventVideoEntity;
import com.richard.record.fragments.VideoFragment;
import com.richard.record.utils.FileUtils;

import static com.richard.record.base.BaseApplication.options;

public class VedioListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<EventVideoEntity> listData;
	
	private Fragment mFragment;
	private Context mContext;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private String imageUrl;
	private int shareLocation=0;
	
	
//	private ShareListener shareListener=new ShareListener() {
//
//		@Override
//		public void onComplete(int status) {
//			if(status==1){
//				EventVideoEntity sharedVedio=listData.get(shareLocation);
//				sharedVedio.isShared=true;
//				sharedVedio.save();
//				Intent mIntent = new Intent(VedioListActivity.ACTION_SHARED_REFRESH);
//				mContext.sendBroadcast(mIntent);
//			}
//
//			//TODO
//		}
//	};
	
//	private CallBack_Share callBack_Share=new CallBack_Share() {
//
//		@Override
//		public void toShare(SHARE_MEDIA media) {
//			Log.i("platform", "platform"+media.toString());
//			Bitmap bitmap=ImageUtil.getimage(listData.get(shareLocation).picture);
//			String content ="";
//			//日期
//			SimpleDateFormat format =  new SimpleDateFormat("MM月dd日HH时mm分");
//			String date = format.format(listData.get(shareLocation).time);
//			content = date;
//
//			//位置
//			String locationFormat = mContext.getResources().getString(R.string.video_location);
//			String location = String.format(locationFormat, listData.get(shareLocation).city+listData.get(shareLocation).street);
//			content +=location +" 发生大事了";
//			ShareUtil.directShare(mContext, media, shareListener, bitmap,imageUrl, content);
////			bitmap.recycle();
//		}
//	};
	public void setList(List<EventVideoEntity> listData){
		this.listData.clear();
		this.listData=listData;
		
	}
	
	public VedioListAdapter(Context context,List<EventVideoEntity> listData, Fragment fragment){
		Log.i("VedioListUnshareAdapter", "VedioListUnshareAdapter");
		this.listData=listData;
		inflater=LayoutInflater.from(context);
		
		this.mContext = context;
		this.mFragment = fragment;
	}
	@Override
	public int getCount() {
		return listData.size();
//		return 8;
	}
	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View converView, ViewGroup parent) {
		final ViewHolder holder;
		final EventVideoEntity video=listData.get(position);
		if(converView==null){
			holder=new ViewHolder();
			converView=inflater.inflate(R.layout.item_vedio_list, null);
			holder.tv_date=(TextView) converView.findViewById(R.id.tv_date);
			holder.tv_location=(TextView) converView.findViewById(R.id.tv_location);
			holder.iv_pic =(ImageView) converView.findViewById(R.id.iv_pic);
			holder.iv_share  = (ImageView) converView.findViewById(R.id.iv_share);
			holder.rl_share = (RelativeLayout) converView.findViewById(R.id.rl_share);
			holder.rl_del = (RelativeLayout) converView.findViewById(R.id.rl_del);
			converView.setTag(holder);
		}else{
			holder=(ViewHolder) converView.getTag();
		}
		
		//日期
		SimpleDateFormat format =  new SimpleDateFormat("MM月dd日  HH时mm分");  
		String date = format.format(video.time);  
		holder.tv_date.setText(date);

		
		//位置
		String locationFormat = mContext.getResources().getString(R.string.video_location);
		String location = String.format(locationFormat,video.city+video.street);
		if(location.contains("null")){
			location = "地点未知";
		}
		holder.tv_location.setText(location);
		//删除
		
		holder.rl_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
						new DelVideo().execute(video);
						listData.remove(video);
						notifyDataSetChanged();
						((VideoFragment)mFragment).onRefresh();
						
						
//						//发送未分享数量广播
//						Intent mIntent = new Intent(VedioListActivity.ACTION_SHARED_NUM);
//						mIntent.putExtra(VedioListActivity.ACTION_SHARED_NUM, listData.size());
//						mContext.sendBroadcast(mIntent);
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
		

		imageLoader.displayImage("file://"+video.picture, holder.iv_pic,options);
		holder.iv_pic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				showImageAndVideo(video.savedPath,"video/*");
				if(!FileUtils.isFileExist(video.savedPath)){
					Toast.makeText(mContext, "视频文件不存在", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Intent iplay=new Intent(mContext,PlayerActivity.class);
				iplay.putExtra("video", video);
				mContext.startActivity(iplay);
				
				//TODO
			}
		});

		holder.rl_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				FileUtils.sendFileByOtherApp(mContext,video.savedPath);
			}
		});
//		//上传
//		holder.rl_upload.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				shareLocation=position;
//				if(BaseApplication.loginUser==null){
//					mContext.startActivity(new Intent(mContext,LoginActivity.class));
//					return;
//				}
//
//				if(!FileUtil.fileIsExists(video.savedPath)){
//					ToastUtil.show(mContext, "文件不存在，无法上传！");
//					return;
//				}
//
//				//判断是否上传过
//				if(video.uploadStatus!=EventVideoEntity.UPLOAD_STATUS_FINISH){
//					Intent report=new Intent(mContext,UploadEditActivity.class);
//					report.putExtra("video", video);
//					mContext.startActivity(report);
////					//非wifi,给个流量提示
////					if(!NetUtil.isWifiNetWork(mContext)){
////						final AlertDialog  myDialog;
////						myDialog = new AlertDialog.Builder(mContext).create();
////						myDialog.setCanceledOnTouchOutside(false);
////						myDialog.show();
////						myDialog.getWindow().setContentView(R.layout.dialog_wifi_tip);
////						TextView comfirm=(TextView) myDialog.getWindow().findViewById(R.id.tv_confirm);
////						comfirm.setOnClickListener(new View.OnClickListener() {
////							@Override
////							public void onClick(View v) {
////								//上传视频文件
////								NetworkManager.uploadShareVideo(video, mContext);
////							}
////						});
////						TextView cancle=(TextView) myDialog.getWindow().findViewById(R.id.tv_cancle);
////						cancle.setOnClickListener(new View.OnClickListener() {
////							@Override
////							public void onClick(View v) {
////								myDialog.dismiss();
////							}
////						});
////					}else{
////						//wifi直接上传
////						NetworkManager.uploadShareVideo(video, mContext);
////					}
//				}else{
//					//已经上传过了，分享去咯
//					if(BaseApplication.loginUser==null){
//						mContext.startActivity(new Intent(mContext,LoginActivity.class));
//						return;
//					}
//					toShare(video.shareURL);
//				}
//
//				//AutoUploadManager.addTask(video);
//
//				//TODO
//			}
//		});
		
		return converView;
	}

	private final class ViewHolder{
		private TextView tv_date,tv_location;
		private ImageView  iv_pic,iv_share;
		private RelativeLayout rl_share,rl_del;
	}
	/**
	 * 
	 * @param path 文件路径
	 * @param type "image/*"--图片   "video/*"--视频
	 */
	private void showImageAndVideo(String path,String type){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//Uri mUri = Uri.parse("file://" + picFile.getPath());Android3.0以后最好不要通过该方法，存在一些小Bug
		File file=new File(path);
		intent.setDataAndType(Uri.fromFile(file), type);
		mContext.startActivity(intent);
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
//			((BaseActivity) mContext).dismissLoadingDialog();
		}
	}
	
	
	
//	public void toShare(String imageUrl){
//		this.imageUrl=imageUrl;
//		DialogShare dialogShare=new DialogShare(mContext, callBack_Share, Util.getWindowWidth(mContext));
//		dialogShare.show();
//	}
}
