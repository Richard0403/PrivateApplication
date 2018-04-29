package com.richard.record.widget;



import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.richard.record.R;


public class CustomProgressDialog extends Dialog {
	
	public CustomProgressDialog(Context context){
		super(context);
	}
	
	public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }
	
	public CustomProgressDialog(Context context, int theme,int text) {
		super(context, R.style.CustomProgressDialog);

		setContentView(R.layout.loading_progress);
		TextView tvMsg = (TextView)findViewById(R.id.id_tv_loadingmsg);
		tvMsg.setText(text);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		
//        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
//        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.rotate);  
//        LinearInterpolator lin = new LinearInterpolator();  
//        operatingAnim.setInterpolator(lin); m
//        imageView.startAnimation(operatingAnim);
		
		//customProgressDialog.show();
	
	}
	
	public CustomProgressDialog(Context context, String text) {
		super(context, R.style.CustomProgressDialog);

		setContentView(R.layout.loading_progress);
		TextView tvMsg = (TextView)findViewById(R.id.id_tv_loadingmsg);
		tvMsg.setText(text);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		
//        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
//        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.rotate);  
//        LinearInterpolator lin = new LinearInterpolator();  
//        operatingAnim.setInterpolator(lin); m
//        imageView.startAnimation(operatingAnim);
		
		//customProgressDialog.show();
	
	}	
	public static CustomProgressDialog createDialog(Context context,int text){
		CustomProgressDialog customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.loading_progress);
		TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
		tvMsg.setText(text);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		
//        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
//        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.rotate);  
//        LinearInterpolator lin = new LinearInterpolator();  
//        operatingAnim.setInterpolator(lin); 
//        imageView.startAnimation(operatingAnim);
		
		
		return customProgressDialog;
	}
 
}
