package com.richard.record.recoder;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.richard.record.utils.SharedPreferenceHelper;

public class ProximitySensor implements SensorEventListener {
	private SensorManager sensorManager;
	private float max_range;
	private float min_range;
	private float last_distance;
	private ProximityChangedListener proximityChangedListener;

	public ProximitySensor(Context paramContext,
			ProximityChangedListener paramProximityChangedListener) {
		this.proximityChangedListener = paramProximityChangedListener;
		this.sensorManager = ((SensorManager) paramContext
				.getSystemService(Context.SENSOR_SERVICE));
		this.sensorManager.registerListener(this,
				this.sensorManager.getDefaultSensor(8), SensorManager.SENSOR_DELAY_NORMAL);
		this.min_range = SharedPreferenceHelper.getFloat(
				"sensor_proximity_min_range", -1.0F);
		this.max_range = SharedPreferenceHelper.getFloat(
				"sensor_proximity_max_range", -1.0F);
		// this.a.b("距离", new Object[0]);
	}

	public void close() {
		this.sensorManager.unregisterListener(this);
		SharedPreferenceHelper.save("sensor_proximity_min_range",
				this.min_range);
		SharedPreferenceHelper.save("sensor_proximity_max_range",
				this.max_range);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
        float[] its = event.values;
        if (its != null && event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            String value = "";
            for (int i = 0; i < its.length; i++) {
                value += "its[i]:=" + its[i];
            }

            if (min_range == max_range) {//获取传感器最大,最小值
                if(last_distance==-1){
                	last_distance=its[0];
                    return;
                }
                if(last_distance>its[0]){
                	max_range=last_distance;
                	min_range=its[0];
                }else {
                	min_range=last_distance;
                    max_range=its[0];
                }
                return;
            }
            //经过测试，当手贴近距离感应器的时候its[0]返回值为0.0，当手离开时返回1.0
            if (its[0] == min_range) {// 贴近手机
            	this.proximityChangedListener.isChanged(true);
            } else {// 远离手机
            	this.proximityChangedListener.isChanged(false);
            }
        }
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public static abstract interface ProximityChangedListener {
		public abstract void isChanged(boolean paramBoolean);
	}
}
