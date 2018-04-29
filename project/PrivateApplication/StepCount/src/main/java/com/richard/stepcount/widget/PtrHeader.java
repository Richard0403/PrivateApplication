package com.richard.stepcount.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.richard.stepcount.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import in.srain.cube.views.ptr.indicator.PtrTensionIndicator;

/**
 * By Richard on 2017/12/26.
 */

public class PtrHeader extends FrameLayout implements PtrUIHandler {
    ImageView headerImg;
    private PtrTensionIndicator mPtrTensionIndicator;
    PtrFrameLayout mPtrFrameLayout;
    View view;
    public PtrHeader(Context context) {
        super(context);
        init(context);
    }

    public PtrHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PtrHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.footer_header_use_pic, this, false);
        addView(view);
        headerImg = (ImageView) view.findViewById(R.id.iv_header);
    }
    public void setUp(PtrFrameLayout ptrFrameLayout) {
        mPtrFrameLayout = ptrFrameLayout;
        mPtrTensionIndicator = new PtrTensionIndicator();
        mPtrFrameLayout.setPtrIndicator(mPtrTensionIndicator);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        AnimationDrawable animationDrawable;
        headerImg.setImageResource(R.drawable.anim_refresh_b);
        animationDrawable= (AnimationDrawable) headerImg.getDrawable();
        animationDrawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        headerImg.clearAnimation();
        headerImg.setImageResource(R.mipmap.icon_refresh_begin);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh ) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                headerImg.setImageResource(R.mipmap.icon_refresh_begin);
                headerImg.setScaleX((float) currentPos / mOffsetToRefresh);
                headerImg.setScaleY((float) currentPos / mOffsetToRefresh);
            }
        } else if (currentPos > mOffsetToRefresh ) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                AnimationDrawable animationDrawable;
                headerImg.setImageResource(R.drawable.anim_refresh_a);
                animationDrawable= (AnimationDrawable) headerImg.getDrawable();
                animationDrawable.setOneShot(true);
                animationDrawable.start();
            }
        }
    }
}