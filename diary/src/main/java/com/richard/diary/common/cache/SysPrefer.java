package com.richard.diary.common.cache;

import com.richard.diary.common.base.App;
import com.richard.diary.common.db.SharedPreferenceHelper;
import com.richard.diary.common.utils.VersionUtil;


public class SysPrefer {

    public static final String PREFERENCE_NAME = "config";

    public static final boolean IS_DEBUG = true;

    public static boolean getIsFirstRun(){
        return SharedPreferenceHelper.getBoolean(VersionUtil.getVerName(App.getInstance()), true);
    }
    public static void setIsFirstRun(boolean isFirstRun){
        SharedPreferenceHelper.save(VersionUtil.getVerName(App.getInstance()), false);
    }

}
