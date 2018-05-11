package com.richard.diary.view.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.db.AppConstant;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.http.entity.diary.UploadEntity;
import com.richard.diary.view.base.BaseActivity;
import com.richard.diary.widget.richedit.RichTextEditor;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteDiaryActivity extends BaseActivity {
    @BindView(R.id.ret_content)
    RichTextEditor ret_content;
    @BindView(R.id.sv_content)
    ScrollView sv_content;
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    @BindView(R.id.tv_type)
    TextView tv_type;

    private DiaryTagEntity.DataBean selectTag;

    @Override
    protected int getLayout() {
        return R.layout.activity_write_diary;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        sv_content.setFocusable(true);
        sv_content.setFocusableInTouchMode(true);
        sv_content.requestFocus();
        sv_content.requestFocusFromTouch();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_pic, R.id.iv_close, R.id.tv_tag, R.id.tv_type})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.tv_pic:
                photoPicker();
                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_tag:
                TagSelectActivity.startForResult(this);
                break;
            case R.id.tv_type:
                break;
            case R.id.tv_save:
                break;

        }
    }

    /**
     * 图片选择器
     */
    private void photoPicker() {
        ISNav.getInstance().init(new com.yuyh.library.imgsel.common.ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.TRANSPARENT)
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#7F47DD"))
                // 返回图标ResId
                .backResId(R.mipmap.ic_arrow_white_left)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#7F47DD"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 300, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();
        // 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, AppConstant.Req.PIC_SELECT);
    }

    private void uploadImage(String filePath){
        List<String> imgList = new ArrayList<>();
        imgList.add(filePath);
        HttpRequest httpRequest = new HttpRequest<UploadEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(UploadEntity result) {
                super.onSuccess(result);
                if(result.getData().size()>0){
                    ret_content.insertImage(result.getData().get(0));
                }
            }
        };
        httpRequest.startMultiFile(HomeService.class, "uploadImage", "files",imgList,true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.Req.PIC_SELECT && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            uploadImage(pathList.get(0));
        }
        if(requestCode == AppConstant.Req.TAG_SELECT && requestCode == AppConstant.Req.TAG_SELECT){
            selectTag = (DiaryTagEntity.DataBean) data.getSerializableExtra(AppConstant.Extra.EXTRA_TAG);
            tv_tag.setText(selectTag.getName());
        }
    }
}
