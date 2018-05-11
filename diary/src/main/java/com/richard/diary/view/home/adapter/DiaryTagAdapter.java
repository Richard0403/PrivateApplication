package com.richard.diary.view.home.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.diary.R;
import com.richard.diary.common.utils.ImageLoader;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.view.home.activity.DiaryListActivity;

import java.util.List;

/**
 * By Richard on 2018/4/26.
 */

public class DiaryTagAdapter extends BaseQuickAdapter<DiaryTagEntity.DataBean, BaseViewHolder> {

    public DiaryTagAdapter(List<DiaryTagEntity.DataBean> data) {
        super(R.layout.item_diary_tag, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DiaryTagEntity.DataBean item) {
        ImageLoader.getInstance().displayRoundImage(mContext, item.getPicture(), (ImageView) helper.getView(R.id.iv_bg));
        helper.setText(R.id.tv_tag, item.getName()+"("+item.getDiaryCount()+")");
    }
}
