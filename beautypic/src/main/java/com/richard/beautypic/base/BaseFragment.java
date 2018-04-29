package com.richard.beautypic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;

/**
 * Created by Mr.zhou on 2016/10/6.
 */

public abstract class BaseFragment extends Fragment {

    public View layout_nodata;
    private TextView btn_nodata;
    private TextView tv_nodata;

    protected abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected abstract void initView();

    protected abstract void initData();




}
