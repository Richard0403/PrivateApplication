package com.richard.stepcount.view.base;

/**
 * By Richard on 2017/12/26.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.richard.stepcount.R;
import com.richard.stepcount.constants.AppConstant;
import com.richard.stepcount.utils.ImageLoader;
import com.richard.stepcount.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/15.
 * Desc: 通用图片列表适配器
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.AttentionImgVH> {

    private List<String> data = new ArrayList<>();
    private Context context;

    public ImageAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public AttentionImgVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AttentionImgVH(LayoutInflater.from(context).inflate(R.layout.item_common_image, parent, false));
    }

    @Override
    public void onBindViewHolder(AttentionImgVH holder, final int position) {
        if(!StringUtils.isEmpty(data.get(position))){
            ImageLoader.getInstance().displayImage(data.get(position), context, holder.iv_img);
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageActivity.class);
                    intent.putStringArrayListExtra("img", (ArrayList<String>) data);
                    intent.putExtra("code", 0);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class AttentionImgVH extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_img)
        ImageView iv_img;

        public AttentionImgVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
