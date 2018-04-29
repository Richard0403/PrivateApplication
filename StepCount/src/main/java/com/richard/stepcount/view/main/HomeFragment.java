package com.richard.stepcount.view.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseFragment;
import com.richard.stepcount.view.home.fragment.StepCountFragment;
import com.richard.stepcount.view.home.fragment.StepTraceFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_step)
    TextView tv_step;

    @BindView(R.id.tv_step_count)
    TextView tv_step_count;
    @BindView(R.id.tv_step_trace)
    TextView tv_step_trace;

    @BindView(R.id.v_step_count)
    View v_step_count;
    @BindView(R.id.v_step_trace)
    View v_step_trace;

    @BindColor(R.color.txt_blue)
    int blue;
    @BindColor(R.color.accent)
    int yellow;
    @BindColor(R.color.txt_middle_grey)
    int grey;
    @BindColor(R.color.transparent)
    int transparent;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction = null;
    private int latestIndex = -1;
    private BaseFragment stepCountFragment;
    private BaseFragment stepTraceFragment;

    public HomeFragment() {
    }
    /**
     *
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_step_count, R.id.rl_step_trace})
    protected void Onclick(View view) {
        switch (view.getId()) {
            case R.id.rl_step_count:
                setTabSelection(0);
                break;
            case R.id.rl_step_trace:
                setTabSelection(1);
                break;
            default:
                break;
        }
    }


    public void setTabSelection(int index) {
        if (index == latestIndex && latestIndex != -1) {
            return;
        }
        setTextAndImage();
        /** 隐藏软键盘 **/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        switch (index) {
            case 0:
                if (stepCountFragment == null) {
                    stepCountFragment = StepCountFragment.newInstance();
                }
                tv_step_count.setTextColor(blue);
                v_step_count.setBackgroundColor(yellow);
                break;
            case 1:
                if (stepTraceFragment == null) {
                    stepTraceFragment = StepTraceFragment.newInstance();
                }
                tv_step_trace.setTextColor(blue);
                v_step_trace.setBackgroundColor(yellow);
                break;
            default:
                break;
        }
        switchFragment(latestIndex, index);
        latestIndex = index;
    }

    private void setTextAndImage() {
        tv_step_trace.setTextColor(grey);
        tv_step_count.setTextColor(grey);

        v_step_trace.setBackgroundColor(transparent);
        v_step_count.setBackgroundColor(transparent);
    }


    private void switchFragment(int from, int to) {
        BaseFragment fromFragment = null;
        BaseFragment toFragment = null;

        if (from == to) {
            return;
        }
        fragmentTransaction = fragmentManager.beginTransaction();

        if (from == -1) {
            toFragment = findFragmentByTag(to);
            fragmentTransaction.add(R.id.fl_step_main, toFragment)
                    .commit();
        } else {

            fromFragment = findFragmentByTag(from);
            toFragment = findFragmentByTag(to);

            if (fromFragment == null) {
                fragmentTransaction.add(R.id.fl_step_main, toFragment)
                        .commit();
            } else {
                if (!toFragment.isAdded()) { // 先判断是否被add过
                    fragmentTransaction
                            .add(R.id.fl_step_main, toFragment)
                            .hide(fromFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    fragmentTransaction.show(toFragment);
                    fragmentTransaction.hide(fromFragment);
                    fragmentTransaction.commit(); // 隐藏当前的fragment，显示下一个
                }
            }
        }

    }

    private BaseFragment findFragmentByTag(int index) {
        switch (index) {
            case 0:
                return stepCountFragment;
            case 1:
                return stepTraceFragment;

        }
        return null;
    }


    public void setStep(int step){
        tv_step.setText("current Step:"+step);
        ((StepCountFragment)stepCountFragment).setStep(step);
    }

    public void setStepCountFragmentTraceStatus(){
        if(stepCountFragment != null){
            ((StepCountFragment)stepCountFragment).setTraceStatus();
        }
    }
}
