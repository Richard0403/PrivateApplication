package com.richard.beautypic.base;

import android.app.Application;
import android.content.Context;
import com.richard.beautypic.db.Config;
import com.richard.beautypic.entity.UserInfo;
import com.umeng.socialize.PlatformConfig;

/**
 * by Richard on 2017/9/7
 * desc:
 */
public class App extends Application{
    private static App INSTANCE;
    public static App getInstance() {
        return INSTANCE;
    }

    private static UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        setLoginPlateform();
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public static UserInfo getUserInfo(){
        if(userInfo == null){
            userInfo = Config.getConfig(INSTANCE).getUserInfo();
        }
        return userInfo;
    }
    public static void setUserInfo(UserInfo user){
        userInfo = user;
        Config.getConfig(INSTANCE).saveUserInfo(userInfo);
    }

    private void setLoginPlateform() {
        PlatformConfig.setQQZone("1106309587","hTQwH9JyDt92nwPr");
    }


}
