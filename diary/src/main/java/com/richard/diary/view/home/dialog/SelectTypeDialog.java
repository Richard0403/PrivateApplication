package com.richard.diary.view.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.richard.diary.R;
import com.richard.diary.common.utils.ScreenUtils;
import com.richard.diary.common.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Richard on 2017/2/25.
 * 选择私密类型
 */

public class SelectTypeDialog extends Dialog {

    @BindView(R.id.rg_fee_type)
    RadioGroup rg_fee_type;


    private Context mContext;
    private int winW;
    private OnViewClick viewClick;
    private int selectType;


    /**
     * @param context
     */
    public SelectTypeDialog(Context context, int selectType, OnViewClick viewClick) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.viewClick = viewClick;
        this.selectType = selectType;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_type);
        ButterKnife.bind(this);
        initView();
        setSize();
    }

    @Override
    public void show() {
        super.show();
    }




    private void initView() {
        if(selectType == 0){
            rg_fee_type.check(R.id.rb_private);
        }else if(selectType == 1){
            rg_fee_type.check(R.id.rb_public);
        }else if(selectType == 2){
            rg_fee_type.check(R.id.rb_protect);
        }
        rg_fee_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_private){
                    viewClick.onClickType(0);
                }else if(checkedId == R.id.rb_protect){
                    viewClick.onClickType(2);
                }else if(checkedId == R.id.rb_public){
                    viewClick.onClickType(1);
                }
                dismiss();
            }
        });
    }

    public void setSize() {
        winW = ScreenUtils.getScreenWidth();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 8;
//        p.height = (int) (winW / 10 * 8 * 1.2);
        getWindow().setAttributes(p);
        setCancelable(true);
    }

    public interface OnViewClick{
        void onClickType(int type);
    }
}


