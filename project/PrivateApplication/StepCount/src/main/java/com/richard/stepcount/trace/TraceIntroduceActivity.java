package com.richard.stepcount.trace;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TraceIntroduceActivity extends BaseActivity {
    @BindView(R.id.tv_title_left)
    TextView tv_title;
    @BindView(R.id.tv_stop)
    TextView tv_stop;


    @Override
    protected int getLayout() {
        return R.layout.activity_trace_introduce;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tv_title.setText("轨迹说明");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_back, R.id.tv_stop})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_stop:
                TraceHelper.getInstance(this).stopTraceService();
                tv_stop.setText("轨迹已关闭");
                break;
        }
    }
}
