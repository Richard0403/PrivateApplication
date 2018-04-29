package com.richard.beautypic.view.home.novel;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.beautypic.R;
import com.richard.beautypic.base.BaseActivity;
import com.richard.beautypic.entity.NovelListEntity;
import com.richard.beautypic.entity.PictureEntity;
import com.richard.beautypic.entity.bean.NovelBean;
import com.richard.beautypic.net.request.BaseView;
import com.richard.beautypic.net.request.HomeRequest;
import com.richard.beautypic.utils.ImageLoader;
import com.richard.beautypic.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class NovelListActivity extends BaseActivity {
    @BindView(R.id.tv_title_left)
    TextView tv_title_left;
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.sp_refresh)
    SwipeRefreshLayout sp_refresh;

    private int pageNo = 0;
    private int pageSize = 20;

    private NovelAdapter novelAdapter;
    private List<NovelBean> novelList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_novel_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tv_title_left.setText("小说");
        initList();
    }

    private void initList() {
        sp_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sp_refresh.setRefreshing(false);
                refresh();
            }
        });
        novelAdapter = new NovelAdapter(R.layout.item_novel_list, novelList);
        clv_content.setLayoutManager(new LinearLayoutManager(this));
        clv_content.setAdapter(novelAdapter);
        novelAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                queryNovel();
            }
        }, clv_content);
    }

    @Override
    protected void initData() {
        queryNovel();
    }

    public void refresh(){
        pageNo = 1;
        queryNovel();
    }

    private void queryNovel() {
        HomeRequest.queryNovels(pageNo, pageSize, new BaseView<NovelListEntity>() {
            @Override
            public void onSuccess(NovelListEntity result) {
                if(result.getCode() == 0){
                    List<NovelBean> temp = result.getData().getNovel();
                    int pageCount = result.getData().getPageCount();
                    if(temp.size()>0){
                        if(pageNo == 0){
                            novelList.clear();
                        }
                        novelList.addAll(temp);
                        novelAdapter.notifyDataSetChanged();
                        pageNo++;
                        pageNo = RandomUtil.getRandom(0,pageCount);
                    }
                }
                novelAdapter.loadMoreComplete();

            }

            @Override
            public void onFailure(Throwable e) {
                novelAdapter.loadMoreFail();
            }

        });

    }

    private class NovelAdapter extends BaseQuickAdapter<NovelBean,BaseViewHolder> {

        public NovelAdapter(int layoutResId, List<NovelBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final NovelBean item) {
            helper.setText(R.id.tv_name, Html.fromHtml(item.getTitle()));
            helper.getView(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(mContext, NovelDetailActivity.class);
                    detail.putExtra("id", item.getId());
                    startActivity(detail);
                }
            });
        }
    }
}
