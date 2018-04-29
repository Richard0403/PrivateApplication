package com.richard.record.recoder;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.richard.record.constants.Config;

/** 
 * 一个检测手机摇晃的监听器 
 * 加速度传感器 
 * values[0]： x-axis 方向加速度 
 * values[1]： y-axis 方向加速度 
 * values[2]： z-axis 方向加速度 
 */  
public class ShakeListener implements SensorEventListener {  
    // 速度的阈值，当摇晃速度达到这值后产生作用  
    private static final int SPEED_SHRESHOLD = 3000;  
    // 两次检测的时间间隔  
    private static final int UPTATE_INTERVAL_TIME = 40;  
    // 传感器管理器  
    private SensorManager sensorManager;  
    // 传感器  
    private Sensor sensor;  
    // 重力感应监听器  
    private OnShakeListener onShakeListener;  
    // 加速度监听器  
    private OnAcceListener onAcceListener;  
    
    // 上下文  
    private Context mContext;  
    // 手机上一个位置时重力感应坐标  
    private float lastX;  
    private float lastY;  
    private float lastZ;  
    // 上次检测时间  
    private long lastUpdateTime;  
  
    // 构造器  
    public ShakeListener(Context c) {  
        // 获得监听对象  
        mContext = c;  
        start();  
    }  
  
    // 开始  
    public void start() {  
        // 获得传感器管理器  
        sensorManager = (SensorManager) mContext  
                .getSystemService(Context.SENSOR_SERVICE);  
        
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                50000);
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                50000);
        
        
//        if (sensorManager != null) {  
//            // 获得重力传感器  
//            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);  
//        }  
//        // 注册  
//        if (sensor != null) {  
//              
//             //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，  
//              //根据不同应用，需要的反应速率不同，具体根据实际情况设定  
//            sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL);  
//        }  
  
    }  
  
    // 停止检测  
    public void stop() {  
        sensorManager.unregisterListener(this);  
    }  
  
    // 设置重力感应监听器  
    public void setOnShakeListener(OnShakeListener listener) {  
        onShakeListener = listener;  
    }  
  
    // 重力感应器感应获得变化数据  
    public void onSensorChanged(SensorEvent event) {  
//        // 现在检测时间  
//        long currentUpdateTime = System.currentTimeMillis();  
//        // 两次检测的时间间隔  
//        long timeInterval = currentUpdateTime - lastUpdateTime;  
//        // 判断是否达到了检测时间间隔  
//        if (timeInterval < UPTATE_INTERVAL_TIME)  
//            return;  
//        // 现在的时间变成last时间  
//        lastUpdateTime = currentUpdateTime;  
    	
    	 float x=0,y=0,z=0;//加速度值
    	 float a=0,b=0,c=0;//方向值
  
    	switch (event.sensor.getType()) {
		case Sensor.TYPE_LINEAR_ACCELERATION:
			
	        x = event.values[0];  
	        y = event.values[1];  
	        z = event.values[2];  
//	        Log.i("ShakeListener", "===>"+x+"=="+y+"=="+z);
			break;
		case Sensor.TYPE_ORIENTATION:
			a = event.values[0];  
			b = event.values[1];  
			c = event.values[2];  
//			Log.i("ShakeListener", "===>"+a+"=="+b+"=="+c);
			break;

		default:
			break;
		}
  
  

        double acceleration = Math.sqrt(x * x + y * y +z*z);
        
//        Log.i("ShakeListener", "===>"+acceleration);
        // 达到速度阀值，发出提示  
//        if (speed >= SPEED_SHRESHOLD) {  
//            onShakeListener.onShake();  
//        }  
        if (acceleration >= Config.getConfig(mContext).getShakeCaptureAcceleration()&&onShakeListener!=null) {
          onShakeListener.onShake(acceleration);  
        }  
        
        if(onAcceListener!=null)
        	onAcceListener.onAcce(acceleration);
        
    }  
    //当传感器精度改变时回调该方法  
    public void onAccuracyChanged(Sensor sensor, int accuracy) {  
  
    }  
  
    // 摇晃监听接口  
    public interface OnShakeListener {  
        public void onShake(double acceleration);  
    }  
    
 // 加速度传输接口
    public interface OnAcceListener {  
        public void onAcce(double acceleration);  
    } 
    // 设置加速度传输接口监听器  
    public void setOnAcceListener(OnAcceListener listener) {  
    	onAcceListener = listener;  
    }  
}
