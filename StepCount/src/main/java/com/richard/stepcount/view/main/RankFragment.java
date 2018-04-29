package com.richard.stepcount.view.main;

import android.content.Context;
import android.os.Bundle;

import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseFragment;

public class RankFragment extends BaseFragment {


    public RankFragment() {
    }

    /**
     * @return A new instance of fragment RankFragment.
     */
    public static RankFragment newInstance() {
        RankFragment fragment = new RankFragment();
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
        return R.layout.fragment_rank;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
