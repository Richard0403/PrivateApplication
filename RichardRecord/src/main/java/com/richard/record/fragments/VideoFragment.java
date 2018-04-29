package com.richard.record.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.richard.record.R;
import com.richard.record.base.BaseActivity;
import com.richard.record.base.BaseFragment;
import com.richard.record.entity.EventVideoEntity;
import com.richard.record.entity.VideoEntity;
import com.richard.record.videos.VideoOriListAdapter;
import com.richard.record.videos.VedioListAdapter;
import com.richard.record.widget.MaxListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.Query;


public class VideoFragment extends BaseFragment {
    @BindView(R.id.rl_no_record) RelativeLayout rl_no_record;
    @BindView(R.id.lv_vedio_short) MaxListView lv_vedio_short;
    @BindView(R.id.lv_vedio_long) MaxListView lv_vedio_long;



    private static final String TAG = "VideoFragment";
    public static String ACTION_REFRESH_VIDEO_LIST = "action_refresh_list";
    private final int  INIT_DATA=0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View parentView;
    private Context mContext;


    private List<EventVideoEntity> shortVideo=new ArrayList<EventVideoEntity>();
    private VedioListAdapter shortVideoAdapter;
    private List<VideoEntity> longVideo=new ArrayList<VideoEntity>();
    private VideoOriListAdapter longVideoAdapter;

    private OnFragmentInteractionListener mListener;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(ACTION_REFRESH_VIDEO_LIST)){
                new GetLongVideoListTask().execute();
            }
        }

    };
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {

                case INIT_DATA:
                    initData();
                    break;
            }
        }
    };

    public VideoFragment() {
        // Required empty public constructor
    }
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (parentView != null) {
            ViewGroup parent = (ViewGroup) parentView.getParent();
            if (parent != null) {
                parent.removeView(parentView);
            }
            return parentView;
        }
        parentView = inflater.inflate(R.layout.fragment_video, container, false);
        this.mContext = getActivity();
        ButterKnife.bind(this,parentView);
        initView();
        mHandler.sendEmptyMessage(INIT_DATA);
        return parentView;
    }

    private void initData() {
        //异步加载数据库
        new GetLongVideoListTask().execute();
        //注册更新视频数量的广播
        registerBoradcastReceiver();
    }

    private void initView() {
        shortVideoAdapter=new VedioListAdapter(mContext, shortVideo, this);
        lv_vedio_short.setAdapter(shortVideoAdapter);
        longVideoAdapter = new VideoOriListAdapter(mContext, longVideo);
        lv_vedio_long.setAdapter(longVideoAdapter);
    }


    /**
     * 获取短视频
     * @author richard
     *
     */
    private class GetVideoListTask extends AsyncTask<Void, Void, List<EventVideoEntity>> {
        @Override
        protected void onPreExecute() {
            ((BaseActivity) getActivity()).showLoadingDialog("loading...", new BaseActivity.CancleListener() {
                @Override
                public void canleByUser(boolean isCancle) {
                    cancel(true);
                }
            });
            super.onPreExecute();
        }

        @Override
        protected List<EventVideoEntity> doInBackground(Void... params) {
            Log.i(TAG, "GetVideoListTask start....");
            CursorList result= Query.many(
                    EventVideoEntity.class,
                    "select * from"
                            + " event_video"
                            + " order by time desc", new Object[0]).get();
            List<EventVideoEntity> videoList=result.asList();
            shortVideo.clear();
            shortVideo = videoList;
            Log.i(TAG, "GetVideoListTask over");
            return videoList;
        }
        @Override
        protected void onPostExecute(List<EventVideoEntity> result) {

            shortVideoAdapter.setList(shortVideo);
            shortVideoAdapter.notifyDataSetChanged();
            onRefresh();
            ((BaseActivity) getActivity()).dismissLoadingDialog();
            super.onPostExecute(result);
        }
    }

    /**
     * 获取长视频
     * @author richard
     *
     */
    private class GetLongVideoListTask extends AsyncTask<Void, Void, List<VideoEntity>>{
        @Override
        protected List<VideoEntity> doInBackground(Void... params) {
            Log.i(TAG, "GetLongVideoListTask start....");
            CursorList result=Query.many(
                    VideoEntity.class,
                    "select * from "
                            + "video"
                            + " order by startTime desc", new Object[0]).get();
            List<VideoEntity> videoList=result.asList();
            Log.i(TAG, "GetLongVideoListTask over");
            return videoList;
        }
        @Override
        protected void onPostExecute(List<VideoEntity> result) {
            longVideo.clear();
            longVideo.addAll(result);
            longVideoAdapter.notifyDataSetChanged();
            onRefresh();
            new GetVideoListTask().execute();
            super.onPostExecute(result);
        }
    }

    private void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_REFRESH_VIDEO_LIST);
        mContext.registerReceiver(mBroadcastReceiver, myIntentFilter);


//		IntentFilter refreshFilter = new IntentFilter();
//		refreshFilter.addAction(ACTION_SHARED_REFRESH);
        //注册广播
        //registerReceiver(mBroadcastReceiver, myIntentFilter);
        //registerReceiver(mBroadcastReceiver, refreshFilter);
    }
    @Override
    public void onDestroy() {
        mContext.unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    public void onRefresh(){
        if(shortVideoAdapter.getCount()==0 && longVideoAdapter.getCount()==0){
            rl_no_record.setVisibility(View.VISIBLE);
        }else{
            rl_no_record.setVisibility(View.INVISIBLE);
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
