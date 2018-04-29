package com.richard.record.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.richard.record.widget.CustomProgressDialog;

/**
 * Created by richard on 2016/10/24.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected CustomProgressDialog mLoadingDialog;
    protected CancleListener mCancleListener;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected abstract void initView();
    protected abstract void initData();


    /**
     * 展示loading进度条
     *
     * @param msg
     */
    public void showLoadingDialog(String msg,CancleListener cancleListener) {
        if(this==null){
            return;
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            return;
        }
        mCancleListener = cancleListener;
        mLoadingDialog = new CustomProgressDialog(this, msg);
        mLoadingDialog.setOnKeyListener(mOnKeyListener);
        if (this!=null && mLoadingDialog == null) {
            return;
        }
        mLoadingDialog.show();
    }

    /**
     * 取消loading进度条
     *
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
    /*
	 * Loading按钮监听事件
	 */
    private DialogInterface.OnKeyListener mOnKeyListener = new DialogInterface.OnKeyListener() {

        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (mLoadingDialog != null && mLoadingDialog.isShowing() && mCancleListener!=null) {
                    dismissLoadingDialog();
                    mCancleListener.canleByUser(true);
                }
            }
            return true;
        }
    };
    public interface CancleListener{
        void canleByUser(boolean isCancle);
    }
}
