package com.richard.diary.view.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.richard.diary.common.cache.ActivityCollector;
import com.richard.diary.common.cache.SysPrefer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;

/**
 * Created Richard
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        mContext = this;
        MobclickAgent.setCatchUncaughtExceptions(!SysPrefer.IS_DEBUG);
        initView(savedInstanceState);
        initData();
//        setStateBar();
    }

    protected abstract int getLayout();// 兼容原版

    protected abstract void initView(Bundle savedInstanceState);//兼容原版

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

//    private void setStateBar(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public static <T> void start(Context context, Class <T> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

}
