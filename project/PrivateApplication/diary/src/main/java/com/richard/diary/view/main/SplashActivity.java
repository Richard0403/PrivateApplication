package com.richard.diary.view.main;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.richard.diary.R;
import com.richard.diary.common.cache.AppCache;
import com.richard.diary.common.cache.SysPrefer;
import com.richard.diary.common.cache.UserPrefer;
import com.richard.diary.common.utils.LogUtil;
import com.richard.diary.http.entity.user.UserInfo;
import com.richard.diary.view.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();

    CountDownTimer timer = new CountDownTimer(2*1000+50, 500) {
        @Override
        public void onTick(long millisUntilFinished) {
            LogUtil.i(TAG,"=="+millisUntilFinished);
        }
        @Override
        public void onFinish() {
            finishPage();
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LogUtil.i(TAG, SysPrefer.getIsFirstRun() + "");
        timer.start();
    }

    @Override
    protected void initData() {

    }

    private void finishPage(){
        if(SysPrefer.getIsFirstRun()){
            SysPrefer.setIsFirstRun(false);
            GuideActivity.start(SplashActivity.this, GuideActivity.class);
        }else{
            UserInfo userInfo = AppCache.getUserInfo();
            if(null == userInfo){
                SignInActivity.start(SplashActivity.this, SignInActivity.class);
            }else{
                start(SplashActivity.this, MainActivity.class);
            }
        }
        finish();
    }
}
