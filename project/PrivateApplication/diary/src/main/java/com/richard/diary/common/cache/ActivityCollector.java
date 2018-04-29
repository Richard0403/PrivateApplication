package com.richard.diary.common.cache;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * By Richard on 2018/1/20.
 * Description:管理所有的栈中的Activity
 * usage:
 *      1. 设置控件的数据，
 *          MainActivity mainActivity = ActivityCollector.getActivity(MainActivity.class);
 *          if (mainActivity!=null) mainActivity.main_radio.check(R.id.radio_button1);
 *     2. 判断一个 Activity是否存在
 *          ActivityCollector.isActivityExist(MainActivity.class);
 */
public class ActivityCollector {

    /**
     * 存放activity的列表
     */
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isActivityExist(Activity activity) {
        return activities.contains(activity);
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static Activity getTopActivity() {
        return activities.get(activities.size()-1);
    }
}
