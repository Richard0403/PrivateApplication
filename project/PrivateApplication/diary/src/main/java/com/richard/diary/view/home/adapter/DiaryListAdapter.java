package com.richard.diary.view.home.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.utils.FormatUtils;
import com.richard.diary.common.utils.ImageLoader;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryListEntity;
import com.richard.diary.http.entity.diary.DiaryTagEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Richard on 2018/4/26.
 */

public class DiaryListAdapter extends BaseQuickAdapter<DiaryListEntity.DataBean, BaseViewHolder> {

    public DiaryListAdapter(List<DiaryListEntity.DataBean> data) {
        super(R.layout.item_diary_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DiaryListEntity.DataBean item) {
        ImageLoader.getInstance().displayRoundImage(mContext,item.getPicture(), (ImageView) helper.getView(R.id.iv_bg));
        helper.setText(R.id.tv_date, FormatUtils.getFormatDateTime("MM月dd日", item.getCreateTime()))
                .setText(R.id.tv_title, item.getTitle());
        if(item.getIsPraise() == 0){
            helper.setImageResource(R.id.iv_like, R.mipmap.icon_like_false);
        }else{
            helper.setImageResource(R.id.iv_like, R.mipmap.icon_like);
        }

        helper.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                praiseDiary(item.getId(), getData().indexOf(item));
            }
        });
    }

    private void praiseDiary(final long diaryId, final int position){
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put("diaryId", diaryId);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                if(getData().get(position).getIsPraise() == 0){
                    getData().get(position).setIsPraise(1);
                }else{
                    getData().get(position).setIsPraise(0);
                }
                notifyDataSetChanged();
                ToastUtil.showSingleToast(result.getMsg());
            }
        };
        httpRequest.start(HomeService.class, "praiseDiary");
    }

}
