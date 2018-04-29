package com.richard.beautypic.utils;

import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.richard.beautypic.R;
import com.richard.beautypic.base.App;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import java.io.File;


public class ImageLoader {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static class ImageLoaderHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    private ImageLoader() {

    }

    public static final ImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }

    //直接加载网络图片
    public void displayImage(String url, ImageView imageView, int placeholderImage) {
        Glide.with(App.getInstance())
                .load(url)
//                .placeholder(placeholderImage)
                .error(R.mipmap.icon_head_default)
                .override(1080, 1920)
//                .centerCrop()
                .into(imageView);
    }


    public void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, R.mipmap.icon_head_default);
    }

    //直接加载网络图片相册
    public void displayAlbumImage(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .fitCenter()
                .into(imageView);
    }

    //加载SD卡图片
    public void displayAlbumImage(File file, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(file)
                .fitCenter()
                .into(imageView);

    }

    //加载SD卡图片
    public void displayImage(File file, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(file)
                .centerCrop()
                .into(imageView);

    }

    //加载SD卡图片并设置大小
    public void displayImage(File file, ImageView imageView, int width, int height) {
        Glide.with(App.getInstance())
                .load(file)
                .override(width, height)
                .centerCrop()
                .into(imageView);

    }

    //加载网络图片并设置大小
    public void displayImage(String url, ImageView imageView, int width, int height) {
        Glide.with(App.getInstance())
                .load(url)
                .centerCrop()
                .override(width, height)
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(int resId, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(resourceIdToUri(resId))
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片显示为圆形图片
    public void displayCricleImage(int resId, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(resourceIdToUri(resId))
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(App.getInstance()))
                .into(imageView);
    }

    //加载网络图片显示为圆形图片
    public void displayCricleImage(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(App.getInstance()))
                .error(R.mipmap.icon_head_default)
                .placeholder(R.mipmap.icon_head_default)
                .into(imageView);
    }

    //加载SD卡图片显示为圆形图片
    public void displayCricleImage(File file, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(file)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(App.getInstance()))
                .into(imageView);
    }

    //加载网络圆角图片
    public void displayRoundImage(String url, ImageView imageView, int placeholderImage, int failureImage) {
        Glide.with(App.getInstance())
                .load(url)
                .crossFade()
                .bitmapTransform(new RoundedCornersTransformation(App.getInstance(), 15, 9))
                .placeholder(placeholderImage)
                .error(failureImage)
                .into(imageView);
    }

    //加载网络圆角图片
    public void displayRoundImage(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .crossFade()
                .bitmapTransform(new RoundedCornersTransformation(App.getInstance(), 15, 9))
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .into(imageView);
    }

    //将资源ID转为Uri
    public Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + App.getInstance().getPackageName() + FOREWARD_SLASH + resourceId);
    }


    public void displayGifImage(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .into(new GlideDrawableImageViewTarget(imageView, 1));

    }

    public void displayGifImage(int resId, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(resourceIdToUri(resId))
                .crossFade()
                .placeholder(R.mipmap.icon_head_default)
                .error(R.mipmap.icon_head_default)
                .into(new GlideDrawableImageViewTarget(imageView, 1));

    }


    public void displayBigImage(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .asBitmap()
//                .placeholder(placeholderImage)
                .error(R.mipmap.icon_head_default)
                .override(1080, 1920)
                .centerCrop()
                .into(imageView);
    }
    public void displaySmallImage(String url, ImageView imageView) {
        Glide.with(App.getInstance())
                .load(url)
                .asBitmap()
                .thumbnail(0.2f)
//                .placeholder(placeholderImage)
                .error(R.mipmap.icon_head_default)
                .centerCrop()
                .into(imageView);
    }
}