package com.richard.stepcount.view.richedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseActivity;
import com.richard.stepcount.entity.BaseEntity;
import com.richard.stepcount.entity.home.UploadFileEntity;
import com.richard.stepcount.net.request.BaseView;
import com.richard.stepcount.net.request.FindRequest;
import com.richard.stepcount.net.request.HomeRequest;
import com.richard.stepcount.utils.StringUtils;
import com.richard.stepcount.utils.ToastUtil;
import com.richard.stepcount.view.richedit.view.RichTextEditor;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageLoader;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RichEditActivity extends BaseActivity {
    @BindView(R.id.tv_title_left)
    TextView tv_title_left;
    @BindView(R.id.tv_complete)
    TextView tv_right;

    @BindView(R.id.et_new_title)
    EditText et_new_title;
    @BindView(R.id.et_new_content)
    RichTextEditor et_new_content;
    @BindView(R.id.sv_content)
    ScrollView sv_content;
    @BindView(R.id.rl_select_pic)
    RelativeLayout rl_select_pic;



    @Override
    protected int getLayout() {
        return R.layout.activity_rich_edit;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        setInitData();

    }

    @Override
    protected void initData() {
        tv_right.setText("完成");
    }

    private void setInitData() {
        sv_content.setFocusable(true);
        sv_content.setFocusableInTouchMode(true);
        sv_content.requestFocus();
        sv_content.requestFocusFromTouch();
        rl_select_pic.requestLayout();
    }



    @OnClick({R.id.iv_pic, R.id.rl_complete,
            R.id.rl_back})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.iv_pic:
                photoPicker(3);
                break;
            case R.id.rl_complete:
                publishData();
                break;

            case R.id.rl_back:
                finish();
                break;
                default:
                    break;
        }
    }

    private void publishData() {
        String content = getEditData();
        String title = String.valueOf(et_new_title.getText());
        if(StringUtils.isEmpty(content)||StringUtils.isEmpty(title)){
            ToastUtil.showSingleToast("标题和内容不能为空哟");
            return;
        }
        FindRequest.uploadArticle(title, content, mContext,new BaseView<BaseEntity>() {
            @Override
            public void onSuccess(BaseEntity result) {
                if(result.getCode() == 0){
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    /**
     * 图片选择器
     */
    private void photoPicker(int picNum) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.color_title_yellow))
                .titleBgColor(getResources().getColor(R.color.color_title_yellow))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .mutiSelectMaxSize(picNum)
                // 开启拍照功能 （默认关闭）
//                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();

        ImageSelector.open(this, imageConfig);
    }
    /**
     * 图片选择返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List 图片的集合
            final List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            uploadImage(pathList);
        }
    }

    /**
     * 负责处理编辑数据提交等事宜
     */
    private String getEditData() {
        List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append(itemData.inputStr);
            } else if (itemData.imagePath != null) {
                content.append("<img style='max-width: 100%;'src=\"").append(itemData.imagePath).append("\"/>");
            }
        }
        String result = content.toString();
        result = result.replaceAll("\n","<br/>");
        return result;
    }

    private void uploadImage(List<String> filePath){
        if(filePath.size()<=0){
            return;
        }
        HomeRequest.uploadImage(filePath, mContext, new BaseView<UploadFileEntity>() {
            @Override
            public void onSuccess(UploadFileEntity userImgCBMsg) {
                List<String> fileUrl = userImgCBMsg.getData();
                if(fileUrl.size()>0){
                    et_new_content.insertImage(fileUrl.get(0));
                }
            }
            @Override
            public void onFailure(Throwable e) {
                ToastUtil.showSingleToast("上传图片失败，请重试一下");
            }
        });
    }


    public class GlideLoader implements ImageLoader {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                    .centerCrop()
                    .into(imageView);
        }
    }
}
