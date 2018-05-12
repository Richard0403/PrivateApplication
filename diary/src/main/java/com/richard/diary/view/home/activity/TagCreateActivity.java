package com.richard.diary.view.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.db.AppConstant;
import com.richard.diary.common.utils.ImageLoader;
import com.richard.diary.common.utils.StringUtils;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.UploadEntity;
import com.richard.diary.view.base.BaseActivity;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TagCreateActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView  tv_title;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.rl_add)
    RelativeLayout rl_add;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;


    private String picFilePath;
    private String tagTitle;
    private String tagDesc;

    @Override
    protected int getLayout() {
        return R.layout.activity_tag_create;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tv_title.setText("添加标签");
    }

    @Override
    protected void initData() {

    }

    public static void startForResult(Activity activity){
        Intent intent = new Intent(activity, TagCreateActivity.class);
        activity.startActivityForResult(intent, AppConstant.Req.TAG_CREATE);
    }

    @OnClick({R.id.rl_back, R.id.rl_add, R.id.tv_save})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_add:
                photoPicker();
                break;
            case R.id.tv_save:
                tagTitle = et_title.getText().toString();
                tagDesc = et_desc.getText().toString();
                if(StringUtils.isEmpty(tagTitle) || StringUtils.isEmpty(tagDesc) || StringUtils.isEmpty(picFilePath)){
                    ToastUtil.showSingleToast("请填写标题，简介，并选取图片");
                    return;
                }
                uploadImage(picFilePath);
                break;
        }
    }

    private void saveTag(final String selectPicUrl) {
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map map = new HashMap();
                map.put("tagName", tagTitle);
                map.put("description", tagDesc);
                map.put("picture", selectPicUrl);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity uploadEntity) {
                super.onSuccess(uploadEntity);
                setResult(AppConstant.Req.TAG_CREATE);
                finish();
            }
        };
        httpRequest.start(HomeService.class, "addDiaryTag", true);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.Req.PIC_SELECT && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            picFilePath = pathList.get(0);
            ImageLoader.getInstance().displayImage(mContext, new File(picFilePath), iv_pic);
        }
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
                    String selectPic = result.getData().get(0);
                    saveTag(selectPic);
                }
            }
        };
        httpRequest.startMultiFile(HomeService.class, "uploadImage", "files",imgList,true);
    }
}
