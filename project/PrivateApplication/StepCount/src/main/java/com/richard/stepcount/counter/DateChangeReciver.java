package com.richard.stepcount.counter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.richard.stepcount.db.DbUtils;
import com.richard.stepcount.entity.home.DayStepEntity;


/**
 * Created by Administrator on 2017/3/29.
 */

public class DateChangeReciver extends BroadcastReceiver {
    private static final String ACTION_DATE_CHANGED = Intent.ACTION_DATE_CHANGED;
    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction();

        if (ACTION_DATE_CHANGED.equals(action)) {

            int steps = context.getSharedPreferences("relevant_data", Context.MODE_WORLD_READABLE | Context.MODE_MULTI_PROCESS).getInt(StepService.STEP_COUNT,0);
            DayStepEntity dayStepEntity = new DayStepEntity();
            dayStepEntity.setDate(System.currentTimeMillis()-1000*60*60);
            dayStepEntity.setStepCount(steps);
            dayStepEntity.setUpload(false);
            DbUtils.getInstance(context).insert(dayStepEntity);

            //再次启动，执行startCommand方法(重要)
            Intent i = new Intent(context, StepService.class);
            context.startService(i);

        }
    }
}
