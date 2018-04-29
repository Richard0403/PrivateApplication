package com.richard.beautypic.db;

import android.content.Context;
import android.content.SharedPreferences;
import com.richard.beautypic.entity.UserInfo;

public class Config {

    public static final String PREFERENCE_NAME = "config";
    public static final String USER_INFO = "USER_INFO";



//    public static final String ABORT_GROUP_MSG = "ABORT_GROUP_MSG";


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


    public void saveUserInfo(UserInfo userInfo){
        SharedPreferenceHelper.setObject(USER_INFO, userInfo);
    }

    public UserInfo getUserInfo(){
        return SharedPreferenceHelper.getObject(USER_INFO, UserInfo.class);
    }

}
