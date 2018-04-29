package com.richard.record.utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.richard.record.base.BaseApplication;
import com.richard.record.constants.Constants;


public class SharedPreferenceHelper {
    /**
     * SharedPreferences的名字
     */
    private static final String NAME = "beehoo.travel";
	private static int mode = Context.MODE_PRIVATE;    
	private static final String LOGIN_USER = "beehoo.travel.login.user";
    
//    public static User getLoginUser(){
//    	if(!getAutoLoginStatus()){
//    		return null;
//    	}
//    	SharedPreferences sp = getContext().getSharedPreferences(LOGIN_USER, mode);
//
//		User user = new User();
//		user.setId(sp.getLong(Constants.UID, 0));
//		user.setName(sp.getString(Constants.USERNAME, ""));
//		user.setBirthday(sp.getString(Constants.BIRTHDAY,""));
//		user.setEmail(sp.getString(Constants.EMAIL,""));
//		user.setPhone(sp.getString(Constants.PHONE,""));
//		user.setPassword(sp.getString(Constants.PASSWORD,""));
//		user.setLocalHeadPhoto(sp.getString(Constants.HEAD_IMAGE_URI,""));
//		user.setProfileImageUrl(sp.getString(Constants.HEAD_IMAGE_URL,""));
//		user.setIdcard(sp.getString(Constants.IDCARD,""));
//		user.setArea(sp.getString(Constants.AREA, ""));
//		user.setScreenName(sp.getString(Constants.NICK, ""));
//		user.setAge(sp.getInt(Constants.AGE, 1));
//		user.setSex(sp.getInt(Constants.GENDER, 1));
//		user.setWallet(sp.getLong(Constants.WEALTH, 0));
////		user.setHeart_minutes(sp.getInt(Constants.LOOKAFTER_SETTING_MESSAGE_INTERVAL, BeehooSetting.LOOKAFTER_DEF_MSG_INTERVAL));
////		user.setIs_send_sms(sp.getInt(Constants.LOOKAFTER_SETTING_IS_SEND_SMS, BeehooSetting.LOOKAFTER_DEF_IS_SEND_MSG));
////		user.setIs_address_change(sp.getInt(Constants.LOOKAFTER_SETTING_IS_LOCATION_CHANGE, BeehooSetting.LOOKAFTER_DEF_IS_SEND_MSG_LOC_CHANGE));
//
//
//		user.setWeight(sp.getFloat(Constants.WEIGHT, 0));
//		user.setHeight(sp.getFloat(Constants.HEIGHT, 0));
//		user.setContact(sp.getString(Constants.EMERGENCY_CONTACT, ""));
//
//		user.setHas_receive_message(sp.getInt(Constants.MESSAGE_SETTING_RECEIVE_MESSAGE, AppSetting.MESSAGE_SETTING_RECEIVE_MESSAGE));
//		user.setHas_send_message(sp.getInt(Constants.MESSAGE_SETTING_SEND_MESSAGE, AppSetting.MESSAGE_SETTING_SEND_MESSAGE));
//
//		return user;
//    }
//    public static void saveLoginUser(User user){
//    	if(user ==null){
//    		return;
//    	}
//    	Editor editor = getContext().getSharedPreferences(LOGIN_USER, mode).edit();
//    	editor.putLong(Constants.UID, user.getId());
//		editor.putString(Constants.USERNAME, user.getName());
//		editor.putString(Constants.PASSWORD, user.getPassword());
//		editor.putString(Constants.BIRTHDAY, user.getBirthday());
//		editor.putString(Constants.EMAIL, user.getEmail());
//		editor.putString(Constants.PHONE, user.getPhone());
//		editor.putString(Constants.IDCARD, user.getIdcard());
//		editor.putString(Constants.AREA, user.getArea());
//		editor.putInt(Constants.AGE, user.getAge());
//		editor.putInt(Constants.GENDER, user.getSex());
//		editor.putString(Constants.NICK, user.getScreenName());
//		editor.putString(Constants.HEAD_IMAGE_URI, user.getLocalHeadPhoto());
//		editor.putString(Constants.HEAD_IMAGE_URL, user.getProfileImageUrl());
//		editor.putBoolean(Constants.IS_FIRST, false);
//		editor.putBoolean(Constants.AUTO_LOGIN, true);
//		editor.putLong(Constants.WEALTH, user.getWallet());
//
//        editor.putInt(Constants.MESSAGE_SETTING_SEND_MESSAGE, user.getHas_send_message());
//        editor.putInt(Constants.MESSAGE_SETTING_RECEIVE_MESSAGE, user.getHas_receive_message());
//
//        editor.putFloat(Constants.HEIGHT, user.getHeight());
//        editor.putFloat(Constants.WEIGHT, user.getWeight());
//        editor.putString(Constants.EMERGENCY_CONTACT, user.getContact());
//        editor.commit();
//    }
    public static void setUserInfo(String key, String value){
    	Editor editor = getContext().getSharedPreferences(LOGIN_USER, mode).edit();
    	editor.putString(key, value);
    	editor.commit();
    }
    
    public static boolean getAutoLoginStatus(){
    	 SharedPreferences sp = getContext().getSharedPreferences(NAME, 0);
         return sp.getBoolean(Constants.AUTO_LOGIN, false);
    }
    public static void setAutoLoginStatus( boolean status){

    	Editor editor = getContext().getSharedPreferences(NAME, 0).edit();
   	    editor.putBoolean(Constants.AUTO_LOGIN, status).commit();
    }    
    
    public static boolean getFirstInStatus(){
   	 SharedPreferences sp = getContext().getSharedPreferences(NAME, 0);
     return sp.getBoolean(Constants.IS_FIRST, true);   	
    	
    }
    public static void setFirstInStatus(boolean status){	
     	Editor editor = getContext().getSharedPreferences(NAME, 0).edit();
   	    editor.putBoolean(Constants.IS_FIRST, status).commit();   	
    }    

	public static void save(String key, String value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void save(String key, float value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	public static void save(String key, double value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		float newvalue = (float) value;
		editor.putFloat(key, newvalue);
		editor.commit();
	}
	public static String getString(String key) {
		return getString(key, "");
	}

	public static String getString(String key, String defaultValue) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		return settings.getString(key, defaultValue);
	}

	public static void save(String key, boolean value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void save(String key, int value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void save(String key, long value) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		Editor editor = settings.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static boolean getBoolean(String key) {
		return getBoolean(key, false);

	}

	public static boolean getBoolean(String key, boolean defualtVal) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		return settings.getBoolean(key, defualtVal);

	}

	public static int getInt(String key) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);

		return settings.getInt(key, 0);
	}
	public static int getInt(String key, int defaultVal) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		return settings.getInt(key, defaultVal);
	}
	public static long getLong(String key, long defaultValue) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);

		return settings.getLong(key, defaultValue);
	}
	

	public static Float getFloat(String key, float defaultValue) {
		SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
		
		return settings.getFloat(key, defaultValue);
	}	

    public static double getDouble(String key, double defaultValue) {
        SharedPreferences settings = getContext().getSharedPreferences(NAME, mode);
        double doubleVal = settings.getFloat(key, (float) defaultValue);
        return doubleVal;
    }   	
	public static Context getContext() {
		return BaseApplication.getInstance();
	}

}
