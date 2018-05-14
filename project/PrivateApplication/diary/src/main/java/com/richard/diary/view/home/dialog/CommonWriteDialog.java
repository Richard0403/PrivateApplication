package com.richard.diary.view.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.richard.diary.R;
import com.richard.diary.common.utils.ScreenUtils;
import com.richard.diary.common.utils.StringUtils;
import com.richard.diary.common.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Richard on 2017/2/25.
 * 选择私密类型
 */

public class CommonWriteDialog extends Dialog {

    @BindView(R.id.tv_select_tip)
    TextView tv_select_tip;
    @BindView(R.id.et_content)
    EditText et_content;


    private Context mContext;
    private OnViewClick viewClick;


    /**
     * @param context
     */
    public CommonWriteDialog(Context context, OnViewClick viewClick) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.viewClick = viewClick;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_write);
        ButterKnife.bind(this);
        initView();
        setSize();
    }
    private void initView() {

    }

    @OnClick({R.id.tv_confirm, R.id.tv_cancle})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                String content = et_content.getText().toString();
                if(StringUtils.isEmpty(content)){
                    ToastUtil.showSingleToast("请填写内容");
                    return;
                }
                viewClick.onContent(content);
                dismiss();
                break;
            case R.id.tv_cancle:
                dismiss();
                break;
        }
    }

    public void setSize() {
        int winW = ScreenUtils.getScreenWidth();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 8;
//        p.height = (int) (winW / 10 * 8 * 1.2);
        getWindow().setAttributes(p);
        setCancelable(true);
    }

    public interface OnViewClick{
        void onContent(String content);
    }
}


