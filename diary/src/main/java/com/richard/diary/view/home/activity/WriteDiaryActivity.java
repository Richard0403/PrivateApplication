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
import com.richard.diary.common.utils.StringUtils;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.http.entity.diary.UploadEntity;
import com.richard.diary.view.base.BaseActivity;
import com.richard.diary.view.home.dialog.CommonWriteDialog;
import com.richard.diary.view.home.dialog.ConfirmDialog;
import com.richard.diary.view.home.dialog.SelectTypeDialog;
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
    private int selectPrivateType = 0;
    private String selectImage;
    private String diaryTitle;
    private String diaryContent;

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

    @OnClick({R.id.tv_pic, R.id.iv_close, R.id.tv_tag, R.id.tv_type, R.id.tv_save})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.tv_pic:
                photoPicker();
                break;
            case R.id.iv_close:
                finishPage();
                break;
            case R.id.tv_tag:
                TagSelectActivity.startForResult(this);
                break;
            case R.id.tv_type:
                selectType();
                break;
            case R.id.tv_save:
                saveDiary();
                break;

        }
    }

    private void finishPage(){
        if(StringUtils.isEmpty(ret_content.getEditData())){
            finish();
        }else{
            ConfirmDialog confirmDialog = new ConfirmDialog(this,  new ConfirmDialog.OnViewClick() {
                @Override
                public void onConfirm() {
                    finish();
                }
                @Override
                public void onCancle() {
                }
            });
            confirmDialog.setCancelText("退出后将无法保存内容");
            confirmDialog.show();
        }
    }

    private void saveDiary() {
        if(checkSavable()){
            HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
                @Override
                public String createJson() {
                    Map map = new HashMap();
                    map.put("title", diaryTitle);
                    map.put("content", diaryContent);
                    map.put("pubStatus", selectPrivateType);
                    map.put("picture", selectImage);
                    map.put("tagId", selectTag.getId());
                    return new Gson().toJson(map);
                }

                @Override
                protected void onSuccess(BaseEntity result) {
                    super.onSuccess(result);
                    ToastUtil.showSingleToast(result.getMsg());
                    finish();
                }
            };
            httpRequest.start(HomeService.class, "addDiary", true);
        }
    }

    private boolean checkSavable(){
        diaryContent = ret_content.getEditData();
        if(StringUtils.isEmpty(ret_content.getEditData())){
            ToastUtil.showSingleToast("请编写笔记内容");
            return false;
        }
        if(selectTag == null){
            ToastUtil.showSingleToast("请选择标签");
            return false;
        }
        if(StringUtils.isEmpty(diaryTitle)){
            CommonWriteDialog writeDialog = new CommonWriteDialog(this, new CommonWriteDialog.OnViewClick() {
                @Override
                public void onContent(String content) {
                    diaryTitle = content;
                    saveDiary();
                }
            });
            writeDialog.show();
            ToastUtil.showSingleToast("请填写标题");
            return false;
        }
        return true;
    }


    private void selectType() {
        SelectTypeDialog typeDialog = new SelectTypeDialog(this, selectPrivateType, new SelectTypeDialog.OnViewClick() {
            @Override
            public void onClickType(int type) {
                selectPrivateType = type;
                if(selectPrivateType == 0){
                    tv_type.setText("私密");
                }else if(selectPrivateType == 1){
                    tv_type.setText("公开");
                }else if(selectPrivateType == 2){
                    tv_type.setText("给Ta");
                }
            }
        });
        typeDialog.show();
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
                    if(StringUtils.isEmpty(selectImage)){
                        selectImage = result.getData().get(0);
                    }
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
        if(requestCode == AppConstant.Req.TAG_SELECT && resultCode == AppConstant.Req.TAG_SELECT){
            selectTag = (DiaryTagEntity.DataBean) data.getSerializableExtra(AppConstant.Extra.EXTRA_TAG);
            tv_tag.setText(selectTag.getName());
        }
    }

    @Override
    public void onBackPressed() {
        finishPage();
    }
}
