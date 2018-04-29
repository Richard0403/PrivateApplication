package com.richard.stepcount.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.richard.stepcount.R;
import com.richard.stepcount.entity.find.ArticleListEntity;
import com.richard.stepcount.net.request.BaseView;
import com.richard.stepcount.net.request.FindRequest;
import com.richard.stepcount.view.base.BaseFragment;
import com.richard.stepcount.view.find.adapter.ArticleListAdapter;
import com.richard.stepcount.view.richedit.RichEditActivity;
import com.richard.stepcount.widget.LoadMoreRecyclerView;
import com.richard.stepcount.widget.PtrHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class FindFragment extends BaseFragment {
    @BindView(R.id.tv_complete)
    TextView tv_complete;
    @BindView(R.id.v_status)
    View v_status;

    @BindView(R.id.clv_content)
    LoadMoreRecyclerView clv_content;
    @BindView(R.id.pfl_refresh)
    PtrFrameLayout pfl_refresh;

    private List<ArticleListEntity.DataBean.ArticleBean> articles = new ArrayList<>();
    private ArticleListAdapter articleAdapter;

    private int pageNo = 1;
    private final static int pageSize = 20;

    public FindFragment() {
    }
     public static FindFragment newInstance() {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView() {
        setHeader();
        setListView();
    }


    @Override
    protected void initData() {
        getArticles();
    }

    private void setHeader(){
        tv_complete.setText("发布");
        setStatusBarHeight(v_status);
    }
    private void setListView() {
        clv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        articleAdapter = new ArticleListAdapter(articles);
        clv_content.setAdapter(articleAdapter);
        clv_content.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                //TODO 加载更多
                loadMore();
            }
        });

        PtrHeader header = new PtrHeader(getContext());
        pfl_refresh.setHeaderView(header);
        pfl_refresh.addPtrUIHandler(header);
        pfl_refresh.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //TODO 刷新
                refresh();
            }
        });
    }


    @OnClick({R.id.rl_complete})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.rl_complete:
                startActivity(new Intent(getContext(), RichEditActivity.class));
                break;
        }
    }

    private void refresh(){
        pageNo = 0;
        getArticles();
    }
    private void loadMore(){
        getArticles();
    }


    private void getArticles(){
        FindRequest.getArticles(pageNo, pageSize, getContext(), new BaseView<ArticleListEntity>() {
            @Override
            public void onSuccess(ArticleListEntity result) {
                List<ArticleListEntity.DataBean.ArticleBean> temp = result.getData().getArticle();
                if(temp.size()>0){
                    if(pageNo == 0){
                        articles.clear();
                        pfl_refresh.refreshComplete();
                    }
                    articles.addAll(temp);
                    clv_content.notifyMoreFinish(temp.size()>=pageSize);
                    pageNo++;
                }
            }
            @Override
            public void onFailure(Throwable e) {

            }
        });
    }
}
