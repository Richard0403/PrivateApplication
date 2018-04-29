package com.richard.stepcount.view.find.adapter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.stepcount.R;
import com.richard.stepcount.base.App;
import com.richard.stepcount.entity.find.ArticleListEntity;
import com.richard.stepcount.utils.ImageLoader;
import com.richard.stepcount.utils.PhoneUtils;
import com.richard.stepcount.utils.StringUtils;
import com.richard.stepcount.view.base.ConfirmDialog;
import com.richard.stepcount.view.base.ImageAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * by Richard on 2017/10/6
 * desc:
 */
    public class ArticleListAdapter extends BaseQuickAdapter<ArticleListEntity.DataBean.ArticleBean, BaseViewHolder> {
        private ImageAdapter adapter;
        public ArticleListAdapter( List<ArticleListEntity.DataBean.ArticleBean> data) {
            super(R.layout.item_article_list, data);
            mData = data;
        }

        @Override
        protected void convert(final BaseViewHolder helper, final ArticleListEntity.DataBean.ArticleBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            final TextView attention_cnt = helper.getView(R.id.tv_attention_cnt);
            String content = item.getContent();
            Map<Integer, Object> images = StringUtils.getAllImgSrc(content);
            List<String> imgs = (List<String>) images.get(0);
            List<String> srcs = (List<String>) images.get(1);
            for(String img:imgs){
                content = content.replace(img, "");
            }
            content = content.replaceAll("<br/>","");
            attention_cnt.setText(Html.fromHtml(content));



            SimpleDateFormat format =  new SimpleDateFormat("MM-dd HH:mm");
            String date = format.format(item.getUpdateTime());

            String crDate = date.split(" ")[0];
            String crTime = date.split(" ")[1];

            ImageLoader.getInstance().displayCricleImage(mContext, item.getUser().getHeader(), (ImageView) helper.getView(R.id.iv_attention_avatar));
            helper.setText(R.id.tv_attention_name, item.getUser().getName())
                    .setText(R.id.tv_attention_favorite, String.valueOf(item.getPraiseUsers().size()))
                    .setText(R.id.tv_attention_comment, "评论");

            TextView tv_time_read = helper.getView(R.id.tv_time_read);
            tv_time_read.setText("128"+"阅");

//            if (bean.getViewUser().getSex() == 1) {
//                ImageLoader.getInstance().displayImage(mContext, R.mipmap.icon_nan, (ImageView) helper.getView(R.id.iv_attention_sex));
//            } else {
//                ImageLoader.getInstance().displayImage(mContext, R.mipmap.iocn_nv, (ImageView) helper.getView(R.id.iv_attention_sex));
//            }


            RecyclerView recyclerView = helper.getView(R.id.clv_attention_img);


            if(srcs.size()!=0){
                List<String> tempPics = new ArrayList<>();
                if(srcs.size()>3){
                    tempPics.addAll(srcs.subList(0,3));
                }else{
                    tempPics.addAll(srcs);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));
                adapter = new ImageAdapter(tempPics, mContext);
                recyclerView.setAdapter(adapter);
            }


//            删除
            if(App.getLoginUser().getId().equals(item.getUser().getId())){
                helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfirmDialog confirmDialog = new ConfirmDialog(mContext, PhoneUtils.getWindowWidth(mContext),
                                R.style.CustomDialog, new ConfirmDialog.OnViewClick() {
                            @Override
                            public void onConfirm() {
                                deleteDynamic(item.getId()+"",mData.indexOf(item));
                            }

                            @Override
                            public void onCancle() {

                            }
                        });
                        confirmDialog.show();
                    }
                });
            }else{
                helper.getView(R.id.tv_delete).setVisibility(View.INVISIBLE);
            }

            //点赞
            helper.getView(R.id.ll_attention_favorite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    FindRequest.addClubDynaLike(bean.getId(), new BaseView<BaseEntity>() {
//                        @Override
//                        public void onSuccess(BaseEntity o) {
//                            if(o.getCode() == 0){
//                                ImageLoader.getInstance().displayImage(mContext, R.mipmap.btn_se_xh, (ImageView) helper.getView(R.id.iv_attention_favorite));
//                            }
//                            ToastUtils.showSingleToast(o.getMsg());
//                        }
//
//                        @Override
//                        public void onFailure(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
                }
            });


//            helper.getView(R.id.ll_attention_share).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    initShareData(bean);
//                }
//            });
        }

    private void deleteDynamic(String id, final int index) {
//        FindRequest.delClubDyna(id, new BaseView<BaseEntity>() {
//            @Override
//            public void onSuccess(BaseEntity o) {
//                if(o.getCode() == 0){
//                    mData.remove(index);
//                    notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }
}
