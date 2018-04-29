package com.richard.stepcount.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.richard.stepcount.utils.PhoneUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by Mr.zhou on 2016/10/6.
 */

public abstract class BaseFragment extends Fragment {

    public View layout_nodata;
    private TextView btn_nodata;
    private TextView tv_nodata;

    protected abstract int getLayout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected abstract void initView();

    protected abstract void initData();

    /**
     *  设置状态栏高度
     */
    public void setStatusBarHeight(View view) {
        //通过反射的方式获取状态栏高度
        int height = PhoneUtils.getStatusBarHeight(getContext());
//        try {
//            Class<?> c = Class.forName("com.android.internal.R$dimen");
//            Object obj = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = Integer.parseInt(field.get(obj).toString());
//            height =  getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }


}
