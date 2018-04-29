package com.richard.record.recoder;

import java.lang.reflect.Method;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class WakeLocker {

    private static WakeLock mWakeLock;

    public static void acquire(Context context) {
        if (mWakeLock == null) {
            PowerManager powerManager = (PowerManager) (context.getSystemService(Context.POWER_SERVICE));
            int level = PowerManager.SCREEN_BRIGHT_WAKE_LOCK;
            int flag = PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE;
            mWakeLock = powerManager.newWakeLock(level | flag, context.getPackageName());
        }
        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
//            Log.d("WakeLock acquire");
        }
    }

    public static void release() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            mWakeLock.release();
//            Log.d("WakeLock release");
        }
    }

    /**
     * screen�Ƿ�ر�״̬
     *
     * @param pm
     * @return
     */
    public static boolean isScreenOff(Context context) {
        boolean screenState = false;
        try {
            PowerManager powerManager = (PowerManager) (context.getSystemService(Context.POWER_SERVICE));
            Method mReflectScreenState = PowerManager.class.getMethod("isScreenOn",
                    new Class[]{});
            screenState = (Boolean) mReflectScreenState.invoke(powerManager);
            return !screenState;
        } catch (NoSuchMethodException nsme) {
//            Log.d("API < 7," + nsme);
        } catch (Exception e) {

        }
        return screenState;
    }
}
