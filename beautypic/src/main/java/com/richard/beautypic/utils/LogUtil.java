package com.richard.beautypic.utils;
/**
 * Created by GaoZhen on 2017/2/16.
 * 设置日志是否开启
 */

public class LogUtil {

    private static boolean isOpen = true;

    public static void setIsOpen(boolean open) {
        isOpen = open;
    }

    public static void setLog(String key, String log) {
        if (isOpen) {
            android.util.Log.e(key, log);
        }
    }

}
