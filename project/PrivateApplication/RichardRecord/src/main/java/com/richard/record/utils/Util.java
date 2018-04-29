package com.richard.record.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;

import com.richard.record.R;
import com.richard.record.constants.AppSetting;

public class Util {

	private static String TAG = "Util";

	public static NotificationCompat.Builder mBuilder;
	public static NotificationManager manager;

	public static boolean isEmpty(String str) {
		return null == str || "".equals(str.trim());

	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static String trim(Object o) {
		String str = "";
		if (null != o) {
			str = o.toString().trim();
		}
		return str;
	}

	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getRandomPwd() {
		Random rd = new Random();
		String n = "";
		int getNum;
		do {
			getNum = Math.abs(rd.nextInt()) % 10 + 48;// 产生数字0-9的随机数
			// getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
			char num1 = (char) getNum;
			String dn = Character.toString(num1);
			n += dn;
		} while (n.length() < 6);
		System.out.println("随机�?6位密码是�?" + n);
		return n;
	}

	public static String convertImageURLToLocal(String imageurl) {
		if (imageurl.equals("")) {
			return "";
		}
		String fName = imageurl.trim();
		String fileName = fName.substring(fName.lastIndexOf("/") + 1);
		String localfile = AppSetting.APP_IMAGE_PATH + "/" + fileName;
		return localfile;
	}

	public static String convertBytesToLocal(byte[] b, String fileName) {

		String outputFile = AppSetting.APP_IMAGE_PATH + "/" + fileName;
		File ret = null;
		BufferedOutputStream stream = null;
		FileOutputStream fstream = null;
		try {
			ret = new File(outputFile);
			if (ret.exists()) {
				ret.delete();
			}
			fstream = new FileOutputStream(ret);
			// stream = new BufferedOutputStream(fstream);
			fstream.write(b);
			fstream.flush();

		} catch (Exception e) {
			// log.error("helper:get file from byte process error!");
			e.printStackTrace();
		} finally {
			if (fstream != null) {
				try {
					fstream.close();
				} catch (IOException e) {
					// log.error("helper:get file from byte process error!");
					e.printStackTrace();
				}
			}
		}

		return outputFile;
	}

	/**
	 * 根据手机分辨率从dp转成px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	public static int getWindowWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	public static int getWindowHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		;
		return height;
	}

	/*
	 * 发送消息通知
	 */
	public static void makeNotification(Context context, String title,
			String content) {
		final int NOTIFICATION_FLAG = 1;
		if (context == null) {
			return;
		}
		if (manager == null) {
			manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		// PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
		// new Intent(context, VedioListActivity.class), 0);
		if (mBuilder == null) {
			mBuilder = new NotificationCompat.Builder(context);
		}
		mBuilder.setContentTitle(title)// 设置通知栏标题
				.setContentText(content) // 设置通知栏显示内容
				// .setContentIntent(pendingIntent) //设置通知栏点击意图
				// .setNumber(number) //设置通知集合的数量
				.setTicker(content) // 通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
				.setPriority(Notification.PRIORITY_DEFAULT) // 设置该通知优先级
				// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_LIGHTS)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
				.setSmallIcon(R.mipmap.ic_launcher)// 设置通知小ICON
				.setAutoCancel(true).setProgress(100, 0, false);

		manager.notify(NOTIFICATION_FLAG, mBuilder.build());

	}

	/**
	 * 判断应用是否在后台运行
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					Log.i("后台", appProcess.processName);
					return true;
				} else {
					Log.i("前台", appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public static boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	/**
	 * 获取栈顶的activity名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Activity context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return (runningTaskInfos.get(0).topActivity).toString();
		else
			return null;
	}

//	// To check if service is enabled (MService)
//	public static boolean isAccessibilitySettingsOn(Context mContext) {
//		int accessibilityEnabled = 0;
//		final String service = mContext.getPackageName() + "/"
//				+ MService.class.getCanonicalName();
//		try {
//			accessibilityEnabled = Settings.Secure.getInt(mContext
//							.getApplicationContext().getContentResolver(),
//					Settings.Secure.ACCESSIBILITY_ENABLED);
//			Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
//		} catch (Settings.SettingNotFoundException e) {
//			Log.e(TAG,
//					"Error finding setting, default accessibility to not found: "
//							+ e.getMessage());
//		}
//		TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(
//				':');
//
//		if (accessibilityEnabled == 1) {
//			Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
//			String settingValue = Settings.Secure.getString(mContext
//							.getApplicationContext().getContentResolver(),
//					Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
//			if (settingValue != null) {
//				mStringColonSplitter.setString(settingValue);
//				while (mStringColonSplitter.hasNext()) {
//					String accessibilityService = mStringColonSplitter.next();
//
//					Log.v(TAG, "-------------- > accessibilityService :: "
//							+ accessibilityService + " " + service);
//					if (accessibilityService.equalsIgnoreCase(service)) {
//						Log.v(TAG,
//								"We've found the correct setting - accessibility is switched on!");
//						return true;
//					}
//				}
//			}
//		} else {
//			Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
//		}
//
//		return false;
//	}
	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param context
	 * @param className
	 *            Activity的全路径类名。包名+Activity的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表没有正在运行
	 */
	public static boolean isActivityRunning(Context context, String className) {
		boolean isAppRunning = false;	
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = manager.getRunningTasks(100);
		
		for (RunningTaskInfo info : list) {
			Log.i(TAG, info.topActivity.getClassName());
			if (info.topActivity.getClassName().equals(className)
					|| info.baseActivity.getClassName().equals(className)) {
				isAppRunning = true;
				Log.i(TAG, info.topActivity.getPackageName()
						+ " info.baseActivity.getPackageName()="
						+ info.baseActivity.getClassName());
				break;
			}
		}
		return isAppRunning;
	}

}
