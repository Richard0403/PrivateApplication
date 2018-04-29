package com.richard.stepcount.counter;

import android.util.Log;


public class StepCount implements StepCountListener {
  private int count;
  private int mCount;
  private StepValuePassListener mListeners;
  long timeOfLastPeak;
  long timeOfThisPeak;

  public StepCount() {
    super();
    this.mCount = 0;
    this.count = 0;
    this.timeOfThisPeak = 0;
    this.timeOfLastPeak = 0;
  }

  public void countStep() {
    int v4 = 10;
    this.timeOfLastPeak = this.timeOfThisPeak;
    this.timeOfThisPeak = System.currentTimeMillis();
    if(this.timeOfThisPeak - this.timeOfLastPeak >= 3000) {
      this.count = 1;
    }
    else {
      ++this.count;
    }
    Log.e("StepCount","=="+mCount);

    if(this.count == v4) {
      this.mCount += this.count;
      this.notifyListener();
    }
    else if(this.count > v4) {
      ++this.mCount;
      this.notifyListener();
    }
  }
//  public void countStep()
//  {
//    this.timeOfLastPeak = this.timeOfThisPeak;
//    this.timeOfThisPeak = System.currentTimeMillis();
//    if (this.timeOfThisPeak - this.timeOfLastPeak >= 3000L)
//    {
//      this.count = 1;
//      if (this.count != 10)
//        break label76;
//      this.mCount += this.count;
//      notifyListener();
//    }
//    label76:
//    while (this.count <= 10)
//    {
//      return;
//      this.count += 1;
//      break;
//    }
//    this.mCount += 1;
//    notifyListener();
//  }

  public void initListener(StepValuePassListener l) {
    this.mListeners = l;
  }

  public void notifyListener() {
    if(this.mListeners != null) {



      this.mListeners.stepsChanged(this.mCount);
    }
  }

  public void reloadSettings() {
    this.notifyListener();
  }

  public void setSteps(int steps) {
    this.mCount = steps;
    this.notifyListener();
  }

  public void stepsChanged(int value) {
  }

  public void clearCountSteps() {
    mCount = 0;
    count = 0;
  }
}

