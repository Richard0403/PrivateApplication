package com.richard.stepcount.view.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.richard.stepcount.R;
import com.richard.stepcount.utils.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

import static android.R.attr.path;

/**
 * 通用图片列表的展示界面
 */
public class ImageActivity extends BaseActivity {

    @BindView(R.id.vp_img)
    ViewPager vpImg;
    ImageAdapter imageAdapter;

    private List<View> viewList = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private int code;//0.网络地址  1.内存卡
    private int position;//图片位置
    Handler handler = new Handler();

    @Override
    protected int getLayout() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        strings.addAll(getIntent().getStringArrayListExtra("img"));
        code = getIntent().getIntExtra("code", 0);
        position = getIntent().getIntExtra("position", 0);
        initData();
        imageAdapter = new ImageAdapter();
        vpImg.setAdapter(imageAdapter);
        vpImg.setCurrentItem(position);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < strings.size(); i++) {
            final int index = i;
            ImageView imageView = new ImageView(this);
            imageView.setMaxHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setMaxWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setBackgroundColor(getResources().getColor(R.color.white));
            switch (code) {
                case 0:
                    ImageLoader.getInstance().displayAlbumImage(strings.get(i), this, imageView);
                    break;
                case 1:
                    ImageLoader.getInstance().displayAlbumImage(this, new File(strings.get(i)), imageView);
                    break;
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            if (code == 0) {
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        new AlertDialog.Builder(ImageActivity.this).setTitle("系统提示").setMessage("是否保存图片")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                super.run();
                                                Bitmap bitmap = null;
                                                try {
                                                    bitmap = Glide.with(ImageActivity.this)
                                                            .load(strings.get(index))
                                                            .asBitmap()
                                                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                                            .get();
                                                    if (bitmap != null) {
                                                        // 在这里执行图片保存方法
                                                        savePic(bitmap, ImageActivity.this);
                                                    }
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }.start();
                                        Toast.makeText(ImageActivity.this, "图片保存成功", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                        return true;
                    }
                });
            }

            viewList.add(imageView);
        }
    }

    private void savePic(Bitmap bitmap, Context context) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

}
