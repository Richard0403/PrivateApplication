package com.richard.diary.common.cache;

import com.richard.diary.common.db.SharedPreferenceHelper;
import com.richard.diary.http.entity.user.UserInfo;

/**
 * By Richard on 2018/4/21.
 */

public class UserPrefer {

    public static final String USER_INFO = "USER_INFO";



    public static void saveUserInfo(UserInfo userInfo){
        SharedPreferenceHelper.setObject(USER_INFO, userInfo);
    }
    public static UserInfo getUserInfo(){
        return SharedPreferenceHelper.getObject(USER_INFO, UserInfo.class);
    }
}
