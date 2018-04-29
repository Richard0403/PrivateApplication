package com.richard.record.constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Size;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
	public static final int BIG_VIDEO_COUNT_DEFAULT = 3;
	public static final String BIG_VIDEO_COUNT = "BIG_VIDEO_COUNT"; 
	
	public static final int BIG_VIDEO_DURATION_DEFAULT = 15;  //视频最长15分钟
	public static final String BIG_VIDEO_DURATION = "BIG_VIDEO_DURATION";  	

	public static final String PREFERENCE_NAME = "config";
	public static final String KEY_VOICE_MODE = "KEY_VOICE_MODE";
	public static final String KEY_GESTURE_MODE = "KEY_GESTURE_MODE";
	public static final String KEY_BLUETOOTH_MODE = "KEY_BLUETOOTH_MODE";
	private static final String KEY_AUTO_UPLOAD_WIFI_MODE = "KEY_AUTO_UPLOAD_WIFI_MODE";
	
	private static final String KEY_AUTO_READ_WECHAT_MODE = "KEY_AUTO_READ_WECHAT_MODE";

	
	private static final String KEY_SHAKE_MODE = "KEY_SHAKE_MODE";
	
	public static final boolean KEY_SHAKE_MODE_DEFAULT = true;   //默认打开碰撞检测
	
	private static final String KEY_SHAKE_ACCELERATION = "KEY_SHAKE_ACCELERATION";
	
	private static final String VIDEO_WIDTH ="VIDEO_WIDTH";
	
	private static final String VIDEO_HEIGHT = "VIDEO_HEIGHT";
	private static final String PIO_HISTORY = "PIO_HISTORY";
	
	public static final int VIDEO_WIDTH_DEFAULT = 1280;
	public static final int VIDEO_HEIGHT_DEFAULT = 720;	
	
	private static Config current;
	private SharedPreferences preferences;
	private Context mContext;

	public static synchronized Config getConfig(Context context) {
		if (current == null) {
			current = new Config(context.getApplicationContext());
		}
		return current;
	}

	private Config(Context context) {
		mContext = context;
		preferences = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	public boolean isVoiceCapture() {
		return preferences.getBoolean(KEY_VOICE_MODE, true);
	}

	public void setVoiceCapture(boolean enable) {
		preferences.edit().putBoolean(KEY_VOICE_MODE, enable).apply();
	}

	/**
	 * 是否开启手势命令，默认开启
	 * @return
	 */
	public boolean isGestureCapture() {
		return preferences.getBoolean(KEY_GESTURE_MODE, true);
	}

	public void setGestureCapture(boolean enable) {
		preferences.edit().putBoolean(KEY_GESTURE_MODE, enable).apply();
	}

	public boolean isBlueToothCapture() {
		return preferences.getBoolean(KEY_BLUETOOTH_MODE, false);
	}

	public void setBlueToothCapture(boolean enable) {
		preferences.edit().putBoolean(KEY_BLUETOOTH_MODE, enable).apply();
	}
	
	public boolean isAutoUploadInWIFI() {
		return preferences.getBoolean(KEY_AUTO_UPLOAD_WIFI_MODE, false);
	}	
	public void setAutoUploadInWIFI(boolean enable) {
		preferences.edit().putBoolean(KEY_AUTO_UPLOAD_WIFI_MODE, enable).apply();
	}

	public int getBigVideoCount(){
		return preferences.getInt(BIG_VIDEO_COUNT, BIG_VIDEO_COUNT_DEFAULT);
	}
	public void setBigVideoCount(int count) {
		preferences.edit().putInt(BIG_VIDEO_COUNT, count).apply();
	}	
	public int getVideoMaxDuraion(){
		return preferences.getInt(BIG_VIDEO_DURATION, BIG_VIDEO_DURATION_DEFAULT);
	}
	public void setVideoMaxDuraion(int duration) {
		preferences.edit().putInt(BIG_VIDEO_DURATION, duration).apply();
	}

	public boolean isAutoReadWechat() {
		return preferences.getBoolean(KEY_AUTO_READ_WECHAT_MODE, false);
	}	
	public void setAutoReadWechat(boolean enable) {
		preferences.edit().putBoolean(KEY_AUTO_READ_WECHAT_MODE, enable).apply();
	}
	public boolean isShakeCapture() {
		return preferences.getBoolean(KEY_SHAKE_MODE, KEY_SHAKE_MODE_DEFAULT);
	}
	public void setShakeCapture(boolean enable) {
		preferences.edit().putBoolean(KEY_SHAKE_MODE, enable).apply();
	}
	
	public float getShakeCaptureAcceleration() {
		return preferences.getFloat(KEY_SHAKE_ACCELERATION,15);
	}
	public void setShakeCaptureAcceleration(float acceleration) {
		preferences.edit().putFloat(KEY_SHAKE_ACCELERATION, acceleration).apply();
	}
	
	public void setVideoSize(int width, int height){
		preferences.edit().putInt(VIDEO_WIDTH, width);
		preferences.edit().putInt(VIDEO_HEIGHT, height);
		preferences.edit().apply();
	}
	
	public void setVideoWidth(int width){
		preferences.edit().putInt(VIDEO_WIDTH, width);
		preferences.edit().apply();
	}
	public void setVideoHeight(int height){
		preferences.edit().putInt(VIDEO_HEIGHT, height);
		preferences.edit().apply();
	}	
	public int getVideoWidth(){
		return preferences.getInt(VIDEO_WIDTH, VIDEO_WIDTH_DEFAULT);
	}
	
	public int getVideoHeight(){
		return preferences.getInt(VIDEO_HEIGHT, VIDEO_HEIGHT_DEFAULT);
	}

	/**
	 * POI搜索纪录
	 * @param datalist
	 */
	public <T> void setPOIHistoryList(List<T> datalist) {
		if (null == datalist || datalist.size() <= 0)
			return;

		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(datalist);
		preferences.edit().putString(PIO_HISTORY, strJson).apply();

	}


	/**
	 * POI搜索纪录
	 * @return
	 */
	public <T> List<T> getPOIHistoryList(Class<T[]> type) {
		List<T> datalist=new ArrayList<T>();
		String strJson = preferences.getString(PIO_HISTORY, null);
		if (null == strJson) {
			return datalist;
		}
		Gson gson = new Gson();
		T[] list = gson.fromJson(strJson, type);
		datalist.addAll(Arrays.asList(list));
		return datalist;

	}

}
