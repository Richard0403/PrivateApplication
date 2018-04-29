package com.richard.record.videos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.richard.record.R;
import com.richard.record.entity.VideoEntity;
import com.richard.record.utils.FileUtils;
import com.richard.record.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class VideoOriListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<VideoEntity> listData;

	private Context mContext;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	DisplayImageOptions options;

	public void setList(List<VideoEntity> listData) {
		this.listData.clear();
		this.listData = listData;

	}

	public VideoOriListAdapter(Context context, List<VideoEntity> listData) {
		Log.i("VedioListUnshareAdapter", "VedioListUnshareAdapter");
		this.listData = listData;
		inflater = LayoutInflater.from(context);

		this.mContext = context;

		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.product)
				// .showImageForEmptyUri(R.drawable.product)
				// .showImageOnFail(R.drawable.product)
				.resetViewBeforeLoading(false)
				// .delayBeforeLoading(500)
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(100))// 图片加载好后渐入的动画时间
				.build();

	}

	@Override
	public int getCount() {
		return listData.size();
		// return 8;
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
		final VideoEntity video = listData.get(position);
		if (converView == null) {
			holder = new ViewHolder();
			converView = inflater.inflate(R.layout.item_video_ori_list, null);
			holder.tv_date = (TextView) converView.findViewById(R.id.tv_date);
			holder.tv_location = (TextView) converView
					.findViewById(R.id.tv_location);
			holder.iv_pic = (ImageView) converView.findViewById(R.id.iv_pic);
			holder.rl_route = (RelativeLayout) converView
					.findViewById(R.id.rl_route);
			holder.rl_delete = (RelativeLayout) converView
					.findViewById(R.id.rl_delete);
			holder.iv_play = (ImageView) converView.findViewById(R.id.iv_play);
			holder.rl_video = (RelativeLayout) converView
					.findViewById(R.id.rl_video);

			converView.setTag(holder);
		} else {
			holder = (ViewHolder) converView.getTag();
		}

		// 日期
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日  HH:mm");
		String dateFormat = mContext.getResources().getString(
				R.string.from_time_to);
		String startTime = format.format(video.startTime);
		String endTime = new SimpleDateFormat("HH:mm").format(video.endTime);
		String date = String.format(dateFormat, startTime, endTime);
		holder.tv_date.setText(date);

		// 位置
		String locationFormat = mContext.getResources().getString(
				R.string.from_location_to);
		// String location =
		// String.format(locationFormat,video.city+video.street,
		// video.city+video.end_street);
		String location = String.format(locationFormat, video.street,
				video.end_street);
		holder.tv_location.setText(location);
		// 删除

		holder.rl_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 删除视频
				final AlertDialog myDialog;
				myDialog = new AlertDialog.Builder(mContext).create();
				myDialog.setCanceledOnTouchOutside(false);
				myDialog.show();
				myDialog.getWindow().setContentView(R.layout.common_dialog);
				TextView comfirm = (TextView) myDialog.getWindow()
						.findViewById(R.id.tv_confirm);
				comfirm.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// 删除文件
						myDialog.dismiss();
						new DelVideo().execute(video);
						listData.remove(video);
						notifyDataSetChanged();

						// 发送未分享数量广播
						// Intent mIntent = new
						// Intent(VedioListActivity.ACTION_SHARED_NUM);
						// mIntent.putExtra(VedioListActivity.ACTION_SHARED_NUM,
						// listData.size());
						// mContext.sendBroadcast(mIntent);
					}
				});
				TextView cancle = (TextView) myDialog.getWindow().findViewById(
						R.id.tv_cancle);
				cancle.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						myDialog.dismiss();
					}
				});
			}
		});
		imageLoader.displayImage("file://" + video.picture, holder.iv_pic,
				options);
		holder.iv_pic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// showImageAndVideo(video.savedPath,"video/*");

				Intent iplay = new Intent(mContext, PlayerActivity.class);
				iplay.putExtra("video", video);
				mContext.startActivity(iplay);

				// TODO
			}
		});

		holder.rl_route.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

//				Intent i = new Intent (mContext,ShowTraceActivity.class);
//				i.putExtra("video", video);
//				mContext.startActivity(i);
//				if (holder.rl_map_route.getVisibility() == View.GONE) {
//
//					holder.rl_map_route.setVisibility(View.VISIBLE);
//				} else {
//					holder.rl_map_route.setVisibility(View.GONE);
//				}

				// TODO
			}
		});

		holder.rl_video.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!FileUtils.isFileExist(video.path)) {
					ToastUtil.show(mContext, "视频文件不存在");
					return;
				}
				Intent iplay = new Intent(mContext, PlayerActivity.class);
				iplay.putExtra("video", video);
				mContext.startActivity(iplay);
				// TODO
			}
		});

		return converView;
	}

	private final class ViewHolder {
		private TextView tv_date, tv_location;
		private ImageView iv_pic, iv_play;
		private RelativeLayout rl_route;
		private RelativeLayout rl_delete;
		private RelativeLayout rl_video;

		// private TextureView mTextureView;
		// private SeekBar skbProgress;
		// private PlayerHelper player;


	}


	private class DelVideo extends AsyncTask<VideoEntity, Void, VideoEntity> {
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
			// ((BaseActivity) mContext).dismissLoadingDialog();
		}
	}



}
