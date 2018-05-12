package com.richard.diary.view.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.db.AppConstant;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.view.base.BaseActivity;
import com.richard.diary.view.home.adapter.DiaryTagAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TagSelectActivity extends BaseActivity {
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_complete)
    ImageView iv_complete;

    private DiaryTagAdapter tagAdapter;
    private List<DiaryTagEntity.DataBean> diaryTags = new ArrayList<>();

    private int pageNo = 0;
    private int pageSize = 20;
    @Override
    protected int getLayout() {
        return R.layout.activity_tag_select;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tv_title.setText("选择标签");
        iv_complete.setImageResource(R.mipmap.icon_add);
        tagAdapter = new DiaryTagAdapter(diaryTags);
        clv_content.setLayoutManager(new GridLayoutManager(this,2));
        clv_content.setAdapter(tagAdapter);
        tagAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getUserTag();
            }
        }, clv_content);
        tagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(AppConstant.Extra.EXTRA_TAG, diaryTags.get(position));
                setResult(AppConstant.Req.TAG_SELECT, intent);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        getUserTag();
    }

    public static void startForResult(Activity activity){
        Intent intent = new Intent(activity, TagSelectActivity.class);
        activity.startActivityForResult(intent, AppConstant.Req.TAG_SELECT);
    }

    @OnClick({R.id.rl_back, R.id.rl_complete})
    protected void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_complete:
                TagCreateActivity.startForResult(this);
                break;
        }
    }

    private void getUserTag() {
        HttpRequest httpRequest = new HttpRequest<DiaryTagEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put("pageNo", pageNo);
                map.put("pageSize", pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(DiaryTagEntity result) {
                super.onSuccess(result);
                if(pageNo == 0){
                    diaryTags.clear();
                }
                tagAdapter.loadMoreComplete();
                tagAdapter.setEnableLoadMore(result.getData().size()>=pageSize);
                diaryTags.addAll(result.getData());
                if(result.getData().size() >= pageSize){
                    pageNo++;
                }
                tagAdapter.notifyDataSetChanged();
            }
        };
        httpRequest.start(HomeService.class, "getUserTag");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstant.Req.TAG_CREATE && resultCode == AppConstant.Req.TAG_CREATE){
            pageNo = 0;
            getUserTag();
        }
    }
}
