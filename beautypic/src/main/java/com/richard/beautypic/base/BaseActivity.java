package com.richard.beautypic.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import butterknife.ButterKnife;

/**
 * Created by 45820 on 2016/11/5.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected BaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        translucentStatusBar();

        ButterKnife.bind(this);
        //添加activity
        initView(savedInstanceState);
        initData();
    }


    protected abstract int getLayout();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();






    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() { super.onResume(); }

    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

}
