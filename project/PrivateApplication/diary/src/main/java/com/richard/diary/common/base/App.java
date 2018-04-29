package com.richard.diary.common.base;

import android.support.multidex.MultiDexApplication;

import com.umeng.socialize.PlatformConfig;

public class App extends MultiDexApplication {
    public static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        PlatformConfig.setQQZone("1106309587","hTQwH9JyDt92nwPr");
    }


    public static App getInstance(){
        return INSTANCE;
    }

}
