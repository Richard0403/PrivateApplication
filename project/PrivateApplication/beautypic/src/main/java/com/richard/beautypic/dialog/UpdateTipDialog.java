package com.richard.beautypic.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.richard.beautypic.R;
import com.richard.beautypic.base.App;
import com.richard.beautypic.entity.VersionEntity;
import com.richard.beautypic.utils.DownloadUtil;
import com.richard.beautypic.utils.PhoneUtils;

import java.io.File;


public class UpdateTipDialog extends Dialog implements View.OnClickListener{
    private static final int DOWNLOAD_SUCCESS = 0x01;
    private static final int DOWNLOAD_GOING = 0x02;
    private static final String DOWNLOAD_PROGRESS = "DOWNLOAD_PROGRESS";

    private TextView tv_confirm;
	private String mFileUrl;
	private ProgressBar pb_download;
	private Context mContext;
	private RelativeLayout rl_comfirm;
	private ImageView iv_close;
	private TextView tv_version_name;
	private TextView tv_version_size;
	private TextView tv_version_content;
	private TextView tv_percent;
    private static String savePath = Environment.getExternalStorageDirectory().toString()+"/xiaou.apk";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DOWNLOAD_SUCCESS:
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(savePath)), "application/vnd.android.package-archive");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					App.getInstance().startActivity(intent);
					dismiss();
                    break;
                case DOWNLOAD_GOING:
                    int progress = msg.arg1;
                    pb_download.setIndeterminate(false);
                    pb_download.setMax(100);
                    pb_download.setProgress(progress);
                    tv_percent.setText("已下载 "+progress+"%");
                    break;
            }
        }
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setCancelable(false);
	}
	public UpdateTipDialog(Context context, VersionEntity.DataBean version) {
		super(context, R.style.CustomProgressDialog);
		mContext = context;
		mFileUrl = version.getUrl();
		setContentView(R.layout.dialog_update_tip);
		tv_confirm=(TextView) findViewById(R.id.tv_confirm);
		pb_download=(ProgressBar) findViewById(R.id.pb_download);
		rl_comfirm = (RelativeLayout) findViewById(R.id.rl_comfirm);
		iv_close = (ImageView) findViewById(R.id.iv_close);
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
		tv_version_size = (TextView) findViewById(R.id.tv_version_size);
		tv_version_content = (TextView) findViewById(R.id.tv_version_content);
		tv_percent = (TextView) findViewById(R.id.tv_percent);

		String content = version.getUpdateContent().replace("{n}","\n");
		tv_version_name.setText(version.getVersionName());
		tv_version_size.setText(version.getSize()+"M");
		tv_version_content.setText(content);

		tv_confirm.setOnClickListener(this);
		iv_close.setOnClickListener(this);

		int winW = PhoneUtils.getWindowWidth(context);
		WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 8;
        p.height = (int) (winW / 10 * 8 * 1.8);
		getWindow().setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        getWindow().setAttributes(p);
		setCancelable(!version.isForce());
		iv_close.setClickable(!version.isForce());

	}
 
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		switch(keyCode){
		case KeyEvent.KEYCODE_HOME:return true;
		case KeyEvent.KEYCODE_BACK:return true;
		}
		return super.onKeyDown(keyCode, event);
		}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_close:
			dismiss();
			break;
		case R.id.tv_confirm:
			//TODO
			setOtherInvisible();
			startDownload(mFileUrl);
			pb_download.setProgress(0);
			tv_percent.setText("");
			break;
		default:
			break;
		}
	}
	private void setOtherInvisible() {
		tv_confirm.setVisibility(View.INVISIBLE);
		iv_close.setClickable(false);
		tv_percent.setVisibility(View.VISIBLE);
		pb_download.setVisibility(View.VISIBLE);
	}
	
	
	public void startDownload(String fileUrl) {
//		new DownloadTask(mContext).execute(fileUrl);
//		new DownloadTask(mContext).execute("http://apionline.beehoo.cn/armeabi-v7a-neon/ffmpeg");
        DownloadUtil.get().download(fileUrl, savePath, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                handler.sendEmptyMessage(DOWNLOAD_SUCCESS);
            }
            @Override
            public void onDownloading(int progress) {
                Message message=new Message();
                message.arg1=progress;
                message.what = DOWNLOAD_GOING;
                handler.sendMessage(message);
            }
            @Override
            public void onDownloadFailed() {
                Toast.makeText(mContext,"Download failed", Toast.LENGTH_SHORT).show();
            }
        });
	}

	
}
