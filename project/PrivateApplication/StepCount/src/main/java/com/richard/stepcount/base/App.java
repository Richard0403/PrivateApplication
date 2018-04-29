package com.richard.stepcount.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;


import com.baidu.mapapi.SDKInitializer;
import com.richard.stepcount.db.Config;
import com.richard.stepcount.entity.bean.UserInfo;
import com.richard.stepcount.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class App extends Application {
    private static App INSTANCE;
    public static UserInfo loginUser;
    public static ArrayList<BaseActivity> activities = new ArrayList<>();
    public void insertActivity(BaseActivity activity) {
        activities.add(activity);
    }
    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        SDKInitializer.initialize(getApplicationContext());

    }

    public static void setLoginUser(UserInfo loginUser) {
        App.loginUser = loginUser;
        Config.getConfig(INSTANCE).saveUserInfo(loginUser);
    }
    public static UserInfo getLoginUser(){
        if(loginUser == null){
            loginUser = Config.getConfig(INSTANCE).getUserInfo();
        }
        return loginUser;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {

            }
        }
        return processName;
    }



    public static App getInstance() {
        return INSTANCE;
    }

}
