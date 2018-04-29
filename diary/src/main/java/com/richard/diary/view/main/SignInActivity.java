package com.richard.diary.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.cache.AppCache;
import com.richard.diary.common.cache.UserPrefer;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.user.UserEntity;
import com.richard.diary.http.entity.user.UserInfo;
import com.richard.diary.view.base.BaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {
    @BindView(R.id.iv_login)
    ImageView iv_login;

    private UserInfo userInfo;
    @Override
    protected int getLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        userInfo = UserPrefer.getUserInfo();
        if(userInfo != null){
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
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
                String sex = data.get("gender");

                int sexCode = 0;
                if("男".equals(sex)) {
                    sexCode = 1;
                }else if("女".equals(sex)){
                    sexCode = 0;
                }else{
                    sexCode = 2;
                }
                thirdQQLogin(uid, iconurl, name, sexCode);
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

    private void thirdQQLogin(final String uid, final String iconurl, final String name, final int sexCode) {
        HttpRequest httpRequest = new HttpRequest<UserEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put("name", name);
                map.put("qqOpenId", uid);
                map.put("header", iconurl);
                map.put("sex", sexCode);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(UserEntity baseEntity) {
                super.onSuccess(baseEntity);
                UserInfo userInfo = baseEntity.getData();
                AppCache.setUserInfo(userInfo);
                SignInActivity.start(mContext, MainActivity.class);
                finish();
            }
        };
        httpRequest.start(HomeService.class, "signIn", true);
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
