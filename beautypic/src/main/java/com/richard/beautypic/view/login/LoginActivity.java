package com.richard.beautypic.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.richard.beautypic.R;
import com.richard.beautypic.base.App;
import com.richard.beautypic.base.BaseActivity;
import com.richard.beautypic.db.Config;
import com.richard.beautypic.entity.BaseEntity;
import com.richard.beautypic.entity.UserEntity;
import com.richard.beautypic.entity.UserInfo;
import com.richard.beautypic.net.request.BaseView;
import com.richard.beautypic.net.request.HomeRequest;
import com.richard.beautypic.utils.ToastUtil;
import com.richard.beautypic.view.MainActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.iv_login)
    ImageView iv_login;

    private UserInfo userInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userInfo = Config.getConfig(mContext).getUserInfo();
        if(userInfo != null){
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }

    }

    @Override
    protected void initData() {

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            iv_login.setClickable(false);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            iv_login.setClickable(true);
            if (platform == SHARE_MEDIA.QQ) {
                String uid = data.get("uid");
                String iconurl = data.get("iconurl");
                String name = data.get("name");
                thirdQQLogin(uid, iconurl, name);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showLongToast("授权失败");
            iv_login.setClickable(true);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showLongToast("授权取消");
            iv_login.setClickable(true);
        }
    };

    private void thirdQQLogin(String uid, String iconurl, String name) {
        HomeRequest.login(uid, iconurl, name, mContext,new BaseView<UserEntity>() {
            @Override
            public void onSuccess(UserEntity result) {
                ToastUtil.showLongToast("欢迎回来");
                userInfo = result.getData();
                App.setUserInfo(userInfo);//保存用户信息
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Throwable e) {

            }


        });
    }

    @OnClick({R.id.iv_login})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.iv_login:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
}
