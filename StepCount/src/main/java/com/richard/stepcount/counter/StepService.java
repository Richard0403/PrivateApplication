package com.richard.stepcount.counter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.*;
import android.os.PowerManager.WakeLock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import com.richard.stepcount.view.main.MainActivity;
import com.richard.stepcount.R;
import com.richard.stepcount.utils.LogUtil;

import java.util.Calendar;


public class StepService extends Service implements SensorEventListener {
    private final static int GRAY_SERVICE_ID = 1001;
    private String New_Process_Name = "com.nehcbai.wdc:stepcounterkeeper";

    public static final String STEP_COUNT = "steps";
    public static final String STEP_LAST_VALUE0 = "last_value0";
    public static final String STEP_DAY = "day";
    public static final String STEP_USER_ID = "userId";
    //默认为30秒进行一次存储
    private static int duration = 30000;
    private SensorManager sensorManager;
    private StepDetector stepDetector;
    private NotificationManager nm;
    private NotificationCompat.Builder builder;
    private WakeLock mWakeLock;
    private int  CURRENT_SETP;
    public static int mSteps;
    //计步传感器类型 0-counter 1-detector
    private static int stepSensor = -1;
    private boolean hasRecord = false;
    private int lastBaseStep = 0;//上次记录的基本步数，并把本次最后value[0]用作下次记录

    private boolean dateChanged = false;//日期是否变化的标志


    Notification notification;

    Sensor countSensor,detectorSensor;

    private Handler mHandler = new MessengerHandler();
    private Messenger clientMessage;
    private Messenger mMessenger = new Messenger(mHandler);

    private SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    private static final int NOTIFICATION_STEPS_MSG = 1;



//    private StrongServicelInterface keeperSeviceImp = new StrongServicelInterface.Stub() {
//        @Override
//        public void startService() throws RemoteException {
//            Intent i = new Intent(getBaseContext(), KeeperService.class);
//            getBaseContext().startService(i);
//        }
//
//        @Override
//        public void stopService() throws RemoteException {
//            Intent i = new Intent(getBaseContext(), KeeperService.class);
//            getBaseContext().stopService(i);
//        }
//    };


    private  class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    try {
                        clientMessage = msg.replyTo;
                        Message replyMsg = Message.obtain(null, Constant.MSG_FROM_SERVER);
                        Bundle bundle = new Bundle();
                        bundle.putInt(STEP_COUNT,CURRENT_SETP);
                        replyMsg.setData(bundle);
                        clientMessage.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case NOTIFICATION_STEPS_MSG:
                    updateNotification(mySharedPreferences.getInt(STEP_COUNT, 0));
                    try {
                        if(clientMessage != null){
                            Message replyMsg = Message.obtain(null, Constant.MSG_FROM_SERVER);
                            Bundle bundle = new Bundle();
                            bundle.putInt(STEP_COUNT,CURRENT_SETP);
                            replyMsg.setData(bundle);
                            clientMessage.send(replyMsg);
                        }
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setLog("StepService","Service onCreate");
        initSharepreference();

        startStepDetector();
    }

    private void initSharepreference() {
        try {
            Context appContext = this.createPackageContext(getPackageName(),
                    Context.CONTEXT_IGNORE_SECURITY);
            //全局存储
//            this.mySharedPreferences = appContext.getSharedPreferences("relevant_data", Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS);
            //部分存储
            this.mySharedPreferences = getSharedPreferences("relevant_data", Context.MODE_PRIVATE);
            this.editor = this.mySharedPreferences.edit();
            this.nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            this.CURRENT_SETP = this.mySharedPreferences.getInt(STEP_COUNT, 0);
            this.lastBaseStep = this.mySharedPreferences.getInt(STEP_LAST_VALUE0,0);
            this.updateNotification(CURRENT_SETP);//通知栏数据
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void updateNotification(int steps) {


        builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_MIN);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setTicker("知行");
        builder.setContentTitle("知识和身体总有一个要在路上");
        builder.setWhen(System.currentTimeMillis());
        builder.setOnlyAlertOnce(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        //设置不可清除
        builder.setOngoing(true);
        builder.setContentText("今天已走："+steps + "步");
        notification = builder.build();

        startForeground(GRAY_SERVICE_ID, notification);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(GRAY_SERVICE_ID, notification);
    }

//    private void updateNotification(int steps) {
//        if(notification == null){
//
//            NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
//
//
//            remoteViews = new RemoteViews(getPackageName(),R.layout.notification_layout);
//            remoteViews.setTextViewText(R.id.tv_desc,"今天已走："+steps + "步");
//
//            builder = new NotificationCompat.Builder(this);
//            builder.setTicker("校U");
//            builder.setSmallIcon(R.mipmap.logo);
//            builder.setContentTitle("校U日行千步，提升价值");
//            builder.setPriority(Notification.PRIORITY_MAX);
////            builder.setStyle(mediaStyle);
//            //设置不可清除
//            builder.setOngoing(true);
//            builder.setContent(remoteViews);
//            notification = builder.build();
//
//            startForeground(GRAY_SERVICE_ID, notification);
//            nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            nm.notify(GRAY_SERVICE_ID, notification);
//        }else{
//            remoteViews.setTextViewText(R.id.tv_desc,"今天已走："+steps + "步");
//        }
//    }




    @Override
    public IBinder onBind(Intent intent) {
//        return (IBinder) keeperSeviceImp;
        return mMessenger.getBinder();
    }



    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initDate();
        return START_STICKY;
    }



    //判断是否先清空数据
    private void initDate() {
        int day = Calendar.getInstance().get(Calendar.DATE);
        if (this.mySharedPreferences.getInt(STEP_DAY, -1) != day) {
            this.editor = this.mySharedPreferences.edit();
            this.editor.putInt(STEP_COUNT, 0);
            this.editor.putInt(STEP_LAST_VALUE0, 0);
            this.editor.putInt(STEP_DAY, day);
            this.editor.commit();
            this.mSteps = 0;
            this.CURRENT_SETP = 0;
            this.dateChanged = true;
//            this.lastBaseStep = 0;//不能把lastBaseStep清零，在零点如果持续运行的话，清零会导致步数异常增高(和hasRecord保持一致即可)
            updateNotification(mSteps);
        }
    }

    private void save() {
        mSteps = CURRENT_SETP;
        //TODO 去存储
        editor.putInt(STEP_COUNT, mSteps);
        editor.putInt(STEP_LAST_VALUE0, lastBaseStep);
        editor.commit();
    }





    /**
     * 获取传感器实例
     */
    private void startStepDetector() {
        if (sensorManager != null ) {
            sensorManager = null;
        }
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);

        LogUtil.setLog("StepService","Service startStepDetector");
        //android4.4以后可以使用计步传感器
        int VERSION_CODES = Build.VERSION.SDK_INT;
        if (VERSION_CODES >= 19) {
            addCountStepListener();
        } else {
//            addBasePedoListener();
        }
    }

    /**
     * 添加传感器监听
     */
    private void addCountStepListener() {
        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            stepSensor = 0;
            Log.v("xf", "==countSensor");
            sensorManager.registerListener(StepService.this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else if (detectorSensor != null) {
            stepSensor = 1;
            Log.v("xf", "==detectorSensor");
            sensorManager.registerListener(StepService.this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.v("xf", "==Count sensor not available!");
            addBasePedoListener();
        }
    }

    private StepDetector mStepDetector;
    private StepCount mStepCount;

    //兼容4.4以上，却没有计步传感器的系统
    private void addBasePedoListener() {
        // 获得传感器的类型，这里获得的类型是加速度传感器
        // 此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        this.mStepDetector = new StepDetector();
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // sensorManager.unregisterListener(stepDetector);
        sensorManager.registerListener(mStepDetector, sensor,
                SensorManager.SENSOR_DELAY_UI);


        this.mStepCount = new StepCount();
        this.mStepCount.initListener(new StepValuePassListener() {
            @Override
            public void passValue() {

            }

            @Override
            public void stepsChanged(int paramInt) {
                //TODO paramInt会返回总步数，
//                CURRENT_SETP =  storedStep + paramInt;
                CURRENT_SETP += 1;
                mSteps = CURRENT_SETP;
                save();
                mHandler.sendMessage(mHandler.obtainMessage(NOTIFICATION_STEPS_MSG));
            }
        });
        this.mStepDetector.initListener(this.mStepCount);

    }

    /**
     * 传感器监听回调
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        LogUtil.setLog("StepService","Service onSensorChanged："+event.sensor.getName()
                                    +"==stepSensor:"+stepSensor
                                    +"==value"+event.values[0]
                                    +"==lastvalue"+lastBaseStep);

        //   i++;
        if (stepSensor == 0) {
            int tempStep = (int) event.values[0];
            if (!hasRecord) {
                hasRecord = true;
                if(tempStep>lastBaseStep && lastBaseStep>0){
                    //这次的起始步数大于上次的的value[0],说明中间关闭了应用，补上中间差
                    //小于上次的的value[0],说明中间进行了关机，不需要累加
                    //上次的的value[0]==0,说明进行了日期更换，不需要累加
//                    CURRENT_SETP+=(tempStep - lastBaseStep);
                    if(!dateChanged){//日期没有变化，当天持续累加
                        CURRENT_SETP+=(tempStep - lastBaseStep);
                    }else{//日期变化，不累加，
//                        dateChanged = false;//已在外部设置
                    }
                }
            } else {
                int thisStepCount = tempStep - lastBaseStep;//本次value[0]的diff, （高危操作）不能随便把lastBaseStep置于零
                if(thisStepCount>0){
                    CURRENT_SETP += thisStepCount;
                }
            }
        } else if (stepSensor == 1) {
            if (event.values[0] == 1.0) {
             CURRENT_SETP++;
            }

        }
        mSteps = CURRENT_SETP;
        lastBaseStep = (int)event.values[0];//把本次的value[0]作为下次启动的依据
        dateChanged = false;
        save();
        mHandler.sendMessage(mHandler.obtainMessage(NOTIFICATION_STEPS_MSG));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




    @Override
    public void onDestroy() {
        LogUtil.setLog("StepService","Service onDestroy");
        sensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
