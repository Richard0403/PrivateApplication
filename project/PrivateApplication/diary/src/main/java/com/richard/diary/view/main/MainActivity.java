package com.richard.diary.view.main;

import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.richard.diary.R;
import com.richard.diary.interfaces.DrawerMainEventListener;
import com.richard.diary.view.base.BaseActivity;
import com.richard.diary.widget.layout.DrawerMainView;
import com.richard.diary.widget.layout.DrawerSideView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.dm_view)
    DrawerMainView dm_view;
    @BindView(R.id.ds_view)
    DrawerSideView ds_view;
    @BindView(R.id.draw_layout)
    DrawerLayout draw_layout;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        dm_view.setEventListener(new DrawerMainEventListener() {
            @Override
            public void onDrawerClick() {
                if(draw_layout.isDrawerOpen(Gravity.LEFT)){
                    draw_layout.closeDrawer(Gravity.LEFT);
                }else {
                    draw_layout.openDrawer(Gravity.LEFT);
                }
            }

            @Override
            public void onAddClick() {

            }
        });
        draw_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 滑动的过程当中不断地回调 slideOffset：0~1
                View content = draw_layout.getChildAt(0);
                float scale = 1 - slideOffset;//1~0
                float leftScale = (float) (1 - 0.2 * scale);
                float rightScale = (float) (0.9f + 0.1 * scale);//0.8~1
                drawerView.setScaleX(leftScale);//1~0.7
                drawerView.setScaleY(leftScale);//1~0.7
                content.setScaleX(rightScale);
                content.setScaleY(rightScale);
                content.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));//0~width
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
