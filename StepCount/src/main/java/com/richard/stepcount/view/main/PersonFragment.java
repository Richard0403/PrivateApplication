package com.richard.stepcount.view.main;

import android.content.Context;
import android.os.Bundle;

import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseFragment;

/**
 * create an instance of this fragment.
 */
public class PersonFragment extends BaseFragment {

    public PersonFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
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
        return R.layout.fragment_person;
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
