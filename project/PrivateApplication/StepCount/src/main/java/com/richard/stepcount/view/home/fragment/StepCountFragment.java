package com.richard.stepcount.view.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseFragment;
import com.richard.stepcount.constants.ConstantConfig;
import com.richard.stepcount.entity.BaseEntity;
import com.richard.stepcount.net.request.BaseView;
import com.richard.stepcount.net.request.HomeRequest;
import com.richard.stepcount.trace.TraceConfig;
import com.richard.stepcount.trace.TraceHelper;
import com.richard.stepcount.trace.TraceIntroduceActivity;
import com.richard.stepcount.widget.InstrumentView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class StepCountFragment extends BaseFragment {
    @BindView(R.id.tv_step_count)
    TextView tv_step_count;
    @BindView(R.id.itv_panel_counter)
    InstrumentView itv_panel_counter;
    @BindView(R.id.tv_trace_status)
    TextView tv_trace_status;
    @BindView(R.id.tv_count_tip)
    TextView tv_count_tip;
    @BindColor(R.color.txt_light_grey)
    int txt_light_grey;
    @BindColor(R.color.accent)
    int accent;

    private int todayStep;
    private int TIME = 1000*60*5;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this, TIME);
                uploadStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public StepCountFragment() {
    }

    public static StepCountFragment newInstance() {
        StepCountFragment fragment = new StepCountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uploadStep();
    }

    @OnClick(R.id.tv_trace_status)
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.tv_trace_status:
                if(TraceHelper.isTraceing){
                    startActivity(new Intent(getContext(), TraceIntroduceActivity.class));
                }else{
                    TraceHelper.getInstance(getContext()).initListener()
                            .setTimeCycles(TraceConfig.DEFAULT_GATHER_INTERVAL, TraceConfig.DEFAULT_PACK_INTERVAL)
                            .startTraceService();
                }
                break;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_step_count;
    }

    @Override
    protected void initView() {
        tv_count_tip.setText("每日目标 "+String.valueOf(ConstantConfig.STEP_TASK_COUNT)+"步");
        handler.postDelayed(runnable, TIME);
    }

    @Override
    protected void initData() {

    }

    private void uploadStep(){
        HomeRequest.uploadStep(todayStep, new BaseView<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity o) {
            }

            @Override
            public void onFailure(Throwable e) {
            }
        });
    }


    public void setStep(int step) {
        this.todayStep = step;
        tv_step_count.setText(step+"");
        float progress = ((float) step)/ConstantConfig.STEP_TASK_COUNT*100;
        itv_panel_counter.setProgress(progress>=100? 100:(int)progress);
    }

    public void setTraceStatus(){
        if(TraceHelper.isTraceing){
            tv_trace_status.setText("记录轨迹中...");
            tv_trace_status.setTextColor(txt_light_grey);
        }else{
            tv_trace_status.setText("开启轨迹记录");
            tv_trace_status.setTextColor(accent);
        }
    }
}
