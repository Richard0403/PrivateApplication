package com.richard.stepcount.constants;


import android.os.Environment;

public class AppConstant {
	
	public static final String BASE_URL ="http://api.xbfine.cn/";
//	public static final String BASE_URL ="http://192.168.0.152:8080/";

	//本地目录
    public static final String APP_NAME = "zhixing";
    public static final String APP_PATH = getSdCard()+"/" + APP_NAME;
    public static final String APP_IMAGE_PATH = APP_PATH + "/image";

	public static String MEDIATYPE_FORMAT_JSON = "application/json; charset=utf-8";
	public static String MEDIATYPE_FORMAT_IMG = "image/* ; charset=utf-8";
	public static String MEDIATYPE_FORMAT_MP3 = "audio/mp3; charset=utf-8";


	private static String getSdCard() {
		String sdCardState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(sdCardState)) {
			return Environment.getExternalStorageDirectory().getPath();
		}
		return null;
	}


}
