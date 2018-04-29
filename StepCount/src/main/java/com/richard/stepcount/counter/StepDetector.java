package com.richard.stepcount.counter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class StepDetector implements SensorEventListener {
  float ThreadValue;
  int continueUpCount;
  int continueUpFormerCount;
  float gravityNew;
  float gravityOld;
  final float initialValue;
  boolean isDirectionUp;
  boolean lastStatus;
  private float[] mScale;
  private StepCountListener mStepListeners;
  float[] oriValues;
  float peakOfWave;
  int tempCount;
  float[] tempValue;
  long timeOfLastPeak;
  long timeOfNow;
  long timeOfThisPeak;
  float valleyOfWave;
  final int valueNum;

  public StepDetector() {
    super();
    float v8 = 0.5f;
    int v4 = 4;
    this.mScale = new float[2];
    this.oriValues = new float[3];
    this.valueNum = v4;
    this.tempValue = new float[v4];
    this.tempCount = 0;
    this.isDirectionUp = false;
    this.continueUpCount = 0;
    this.continueUpFormerCount = 0;
    this.lastStatus = false;
    this.peakOfWave = 0f;
    this.valleyOfWave = 0f;
    this.timeOfThisPeak = 0;
    this.timeOfLastPeak = 0;
    this.timeOfNow = 0;
    this.gravityNew = 0f;
    this.gravityOld = 0f;
    this.initialValue = 1.3f;
    this.ThreadValue = 2f;
    int v1;
    for(v1 = 0; v1 < v4; ++v1) {
      this.tempValue[v1] = 0f;
    }

    this.mScale[0] = -((((float)480)) * v8 * 0.050986f);
    this.mScale[1] = -((((float)480)) * v8 * 0.016667f);
  }

  public void DetectorNewStep(float values) {
    if (gravityOld == 0) {
      gravityOld = values;
    } else {
      if (DetectorPeak(values, gravityOld)) {
        timeOfLastPeak = timeOfThisPeak;
        timeOfNow = System.currentTimeMillis();
        if (timeOfNow - timeOfLastPeak >= 250
                && (peakOfWave - valleyOfWave >= ThreadValue)) {
          timeOfThisPeak = timeOfNow;
                    /*
                     * 更新界面的处理，不涉及到算法
                     * 一般在通知更新界面之前，增加下面处理，为了处理无效运动：
                     * 1.连续记录10才开始计步
                     * 2.例如记录的9步用户停住超过3秒，则前面的记录失效，下次从头开始
                     * 3.连续记录了9步用户还在运动，之前的数据才有效
                     * */
          mStepListeners.countStep();
        }
        if (timeOfNow - timeOfLastPeak >= 250
                && (peakOfWave - valleyOfWave >= initialValue)) {
          timeOfThisPeak = timeOfNow;
          ThreadValue = Peak_Valley_Thread(peakOfWave - valleyOfWave);
        }
      }
    }
    gravityOld = values;
  }

  public boolean DetectorPeak(float newValue, float oldValue) {
    lastStatus = isDirectionUp;
    if (newValue >= oldValue) {
      isDirectionUp = true;
      continueUpCount++;
    } else {
      continueUpFormerCount = continueUpCount;
      continueUpCount = 0;
      isDirectionUp = false;
    }

    if (!isDirectionUp && lastStatus
            && (continueUpFormerCount >= 2 || oldValue >= 20)) {
      peakOfWave = oldValue;
      return true;
    } else if (!lastStatus && isDirectionUp) {
      valleyOfWave = oldValue;
      return false;
    } else {
      return false;
    }
  }


  public float Peak_Valley_Thread(float value) {
    int v5 = 4;
    float v1 = this.ThreadValue;
    if(this.tempCount < v5) {
      this.tempValue[this.tempCount] = value;
      ++this.tempCount;
      return v1;
    }

    v1 = this.averageValue(this.tempValue, v5);
    int v0 = 1;

    while (true){
      if(v0 < v5) {
        this.tempValue[v0 - 1] = this.tempValue[v0];
        ++v0;
      }else{
        break;
      }
    }


    this.tempValue[3] = value;
    return v1;
  }

  public float averageValue(float[] value, int n) {
    float v5 = 8f;
    float v4 = 7f;
    float v3 = 4f;
    float v0 = 0f;
    int v1;
    for(v1 = 0; v1 < n; ++v1) {
      v0 += value[v1];
    }

    v0 /= v3;
    if(v0 >= v5) {
      v0 = 4.3f;
    }
    else {
      if(v0 >= v4 && v0 < v5) {
        return 3.3f;
      }

      if(v0 >= v3 && v0 < v4) {
        return 2.3f;
      }

      if(v0 >= 3f && v0 < v3) {
        return 2f;
      }

      v0 = 1.3f;
    }

    return v0;
  }

  public void initListener(StepCountListener sl) {
    this.mStepListeners = sl;
  }

  public void onAccuracyChanged(Sensor arg0, int arg1) {
  }

  public void onSensorChanged(SensorEvent event) {
    int v5 = 2;
    int v0;
    for(v0 = 0; v0 < 3; ++v0) {
      this.oriValues[v0] = event.values[v0];
    }

    this.gravityNew = ((float)Math.sqrt(((double)(this.oriValues[0] * this.oriValues[0] + this.oriValues[
            1] * this.oriValues[1] + this.oriValues[v5] * this.oriValues[v5]))));
    this.DetectorNewStep(this.gravityNew);
  }

  public void setSensitivity(int sensitivity) {
  }
}

