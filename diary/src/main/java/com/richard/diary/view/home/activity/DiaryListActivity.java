package com.richard.diary.view.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryListEntity;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.view.base.BaseActivity;
import com.richard.diary.view.home.adapter.DiaryListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryListActivity extends BaseActivity {
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private static final String EXTRA_TAG_ID = "EXTRA_TAG_ID";
    private static final String EXTRA_TAG_NAME = "EXTRA_TAG_NAME";

    private int pageNo = 0;
    private int pageSize = 10;
    private String tagId, tagName;

    private DiaryListAdapter listAdapter;
    private List<DiaryListEntity.DataBean> diaryList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_diary_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tagId = getIntent().getStringExtra(EXTRA_TAG_ID);
        tagName = getIntent().getStringExtra(EXTRA_TAG_NAME);

        tv_title.setText(tagName);
        listAdapter = new DiaryListAdapter(diaryList);
        clv_content.setLayoutManager(new GridLayoutManager(this, 2));
        clv_content.setAdapter(listAdapter);
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getListByTag();
            }
        }, clv_content);
    }

    @Override
    protected void initData() {
        getListByTag();
    }

    public static void start(Context context, String tagId, String tagName) {
        Intent intent = new Intent(context, DiaryListActivity.class);
        intent.putExtra(EXTRA_TAG_ID, tagId);
        intent.putExtra(EXTRA_TAG_NAME, tagName);
        context.startActivity(intent);
    }


    private void getListByTag() {
        HttpRequest httpRequest = new HttpRequest<DiaryListEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put("tagId", tagId);
                map.put("pageNo", pageNo);
                map.put("pageSize", pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(DiaryListEntity result) {
                super.onSuccess(result);
                if(pageNo == 1){
                    diaryList.clear();
                }
                listAdapter.loadMoreComplete();
                listAdapter.setEnableLoadMore(result.getData().size()>=pageSize);
                diaryList.addAll(result.getData());
                if(result.getData().size() >= pageSize){
                    pageNo++;
                }
                listAdapter.notifyDataSetChanged();
            }
        };
        httpRequest.start(HomeService.class, "queryDiaryByTag");
    }

    @OnClick(R.id.rl_back)
    protected void onBackClick(){
        finish();
    }
}
