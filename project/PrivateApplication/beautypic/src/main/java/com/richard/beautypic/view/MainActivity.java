package com.richard.beautypic.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.beautypic.R;
import com.richard.beautypic.base.BaseActivity;
import com.richard.beautypic.dialog.UpdateTipDialog;
import com.richard.beautypic.entity.VersionEntity;
import com.richard.beautypic.entity.bean.RestWayBean;
import com.richard.beautypic.net.request.BaseView;
import com.richard.beautypic.net.request.HomeRequest;
import com.richard.beautypic.view.home.novel.NovelListActivity;
import com.richard.beautypic.view.home.pic.PicListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{
    @BindView(R.id.clv_content)
    RecyclerView clv_content;

    private RestWaysAdapter waysAdapter;
    private List<RestWayBean> restWays = new ArrayList<>();
    private String ways[] = {"图片", "小说", "视频"};
    private int wayBg[] = {R.drawable.bg_round_random1,R.drawable.bg_round_random2,R.drawable.bg_round_random3};

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initList();

    }

    private void initList() {
        waysAdapter = new RestWaysAdapter(R.layout.item_rest_way,restWays);
        clv_content.setLayoutManager(new GridLayoutManager(this,3));
        clv_content.setAdapter(waysAdapter);
    }

    @Override
    protected void initData() {
        for (int i = 0;i<ways.length;i++){
            RestWayBean bean = new RestWayBean(i,ways[i],wayBg[i]);
            restWays.add(bean);
        }
        waysAdapter.notifyDataSetChanged();
        updateVersion();
    }


    private void updateVersion() {
        //TODO
        HomeRequest.getVersion(new BaseView<VersionEntity>() {
            @Override
            public void onSuccess(VersionEntity version) {
                if (version != null && version.getCode() == 0) {
                    try {
                        //getPackageName()是你当前类的包名，0代表是获取版本信息
                        PackageManager packageManager = getPackageManager();
                        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
                        if (version.getData().getVersionCode() > packInfo.versionCode) {
                            UpdateTipDialog down = new UpdateTipDialog(MainActivity.this, version.getData());
                            down.show();
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }



    private class RestWaysAdapter extends BaseQuickAdapter<RestWayBean,BaseViewHolder>{

        public RestWaysAdapter(int layoutResId, List<RestWayBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final RestWayBean item) {
            helper.setText(R.id.tv_name,item.getName());
            helper.setBackgroundRes(R.id.rl_content, item.getBgId());

            helper.getView(R.id.rl_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   switch (item.getId()){
                       case 0:
                           startActivity(new Intent(mContext, PicListActivity.class));
                           break;
                       case 1:
                           startActivity(new Intent(mContext, NovelListActivity.class));
                           break;
                       case 2:
                           break;
                   }
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

}
