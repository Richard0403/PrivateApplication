package com.richard.beautypic.view.home.pic;

import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.richard.beautypic.entity.PictureEntity;
import com.richard.beautypic.utils.ImageLoader;

import java.util.List;

class PaintingsPagerAdapter extends RecyclePagerAdapter<PaintingsPagerAdapter.ViewHolder> {

    private final ViewPager viewPager;
    private final List<PictureEntity.DataBean.PicturesBean> paintings;
    private final SettingsSetupListener setupListener;

    PaintingsPagerAdapter(ViewPager pager, List<PictureEntity.DataBean.PicturesBean> paintings, SettingsSetupListener listener) {
        this.viewPager = pager;
        this.paintings = paintings;
        this.setupListener = listener;
    }

    @Override
    public int getCount() {
        return paintings.size();
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup container) {
        ViewHolder holder = new ViewHolder(container);
        holder.image.getController().enableScrollInViewPager(viewPager);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        if (setupListener != null) {
            setupListener.onSetupGestureView(holder.image);
        }
        ImageLoader.getInstance().displayBigImage(paintings.get(position).getUrl(), holder.image);
//        ImageLoader.getInstance().displayImage(paintings.get(position),holder.image);
//        GlideHelper.loadResource(paintings[position].imageId, holder.image);
    }

    static GestureImageView  getImage(RecyclePagerAdapter.ViewHolder holder) {
        return ((ViewHolder) holder).image;
    }


    static class ViewHolder extends RecyclePagerAdapter.ViewHolder {
        final GestureImageView image;

        ViewHolder(ViewGroup container) {
            super(new GestureImageView(container.getContext()));
            image = (GestureImageView) itemView;
        }
    }

}
