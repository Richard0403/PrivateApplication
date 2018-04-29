package com.richard.stepcount.view.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.richard.stepcount.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Richard on 2017/2/25.
 * 通用提示框
 */

public class ConfirmDialog extends Dialog {
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cancle)
    TextView tv_cancle;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;


    private Context mContext;
    private int winW;
    private OnViewClick viewClick;


    /**
     * @param context
     * @param winW
     */
    public ConfirmDialog(Context context, int winW, int styleId, OnViewClick viewClick) {
        super(context, styleId);
        this.mContext = context;
        this.winW = winW;
        this.viewClick = viewClick;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        ButterKnife.bind(this);
        initView();
        setSize();
    }

    @Override
    public void show() {
        super.show();
    }




    private void initView() {

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(viewClick!=null){
                    viewClick.onCancle();
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(viewClick!=null){
                    viewClick.onConfirm();
                }
            }
        });
    }

    public void setSize() {
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 8;
//        p.height = (int) (winW / 10 * 8 * 1.2);
        getWindow().setAttributes(p);
        setCancelable(true);
    }

    public interface OnViewClick{
        void onConfirm();
        void onCancle();
    }

    public void setCancelText(String cancelText){
        tv_cancle.setText(cancelText);
    }
    public void setConfirmText(String confirmText){
        tv_confirm.setText(confirmText);
    }
    public void setContentText(String contentText){tv_content.setText(contentText);}

}


