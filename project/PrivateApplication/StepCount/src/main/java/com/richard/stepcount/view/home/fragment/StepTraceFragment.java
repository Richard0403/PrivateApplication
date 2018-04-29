package com.richard.stepcount.view.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseFragment;
import com.richard.stepcount.db.DbUtils;
import com.richard.stepcount.entity.home.DayStepEntity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StepTraceFragment extends BaseFragment {
    @BindView(R.id.clv_step_hostory)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private MyAdapter myAdapter;
    private List<DayStepEntity> stepList  = new ArrayList<>();
    private int pageNo = 0;
    private int pageSize = 20;

    public StepTraceFragment() {
    }


    public static StepTraceFragment newInstance() {
        StepTraceFragment fragment = new StepTraceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_step_trace;
    }


    @Override
    protected void initView() {
        refresh.setColorSchemeResources(R.color.color_title_yellow);
        myAdapter = new MyAdapter(R.layout.item_step_trace, stepList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
//                            onScrolledToTop();
                } else if (!recyclerView.canScrollVertically(1)) {
                    loadMore();
                }
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFirstData();
                refresh.setRefreshing(false);
            }
        });

        getFirstData();
    }

    @Override
    protected void initData() {
        List<DayStepEntity> tempSteps = DbUtils.getInstance(getActivity()).getQueryByWhereLength(DayStepEntity.class,pageNo*pageSize,pageSize);
        stepList.addAll(tempSteps);
        myAdapter.notifyDataSetChanged();
    }

    public void getFirstData() {
        stepList.clear();
        pageNo = 0;
        initData();
    }

    public void loadMore() {
        pageNo++;
        initData();
    }

    private class MyAdapter extends BaseQuickAdapter<DayStepEntity, BaseViewHolder> {
        public MyAdapter(int layoutResId, List<DayStepEntity> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final DayStepEntity item) {
            ProgressBar stepProgress = helper.getView(R.id.pb_steps);
            int progress = item.getStepCount()*100/8000;
            stepProgress.setProgress(progress<=100?progress:100);
        }
    }

}
