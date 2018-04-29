package com.richard.record;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richard.record.base.BaseActivity;
import com.richard.record.fragments.CommunityFragment;
import com.richard.record.fragments.HomeFragment;
import com.richard.record.fragments.PersonFragment;
import com.richard.record.fragments.RecorderFragment;
import com.richard.record.fragments.VideoFragment;
import com.richard.record.recoder.VideoHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements
        HomeFragment.OnFragmentInteractionListener,
        VideoFragment.OnFragmentInteractionListener,
        PersonFragment.OnFragmentInteractionListener,
        RecorderFragment.OnFragmentInteractionListener,
        CommunityFragment.OnFragmentInteractionListener{

//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.btn_main_home) RelativeLayout btn_main_home;
    @BindView(R.id.btn_main_video) RelativeLayout btn_main_video;
    @BindView(R.id.btn_main_person) RelativeLayout btn_main_person;
    @BindView(R.id.btn_main_community) RelativeLayout btn_main_community;



    @BindView(R.id.tv_main_home) TextView tvMainHome;
    @BindView(R.id.tv_main_video) TextView tv_main_video;
    @BindView(R.id.tv_main_person) TextView tv_main_person;
    @BindView(R.id.tv_main_community) TextView tv_main_community;

    @BindView(R.id.iv_main_home) ImageView iv_main_home;
    @BindView(R.id.iv_main_video) ImageView iv_main_video;
    @BindView(R.id.iv_main_person) ImageView iv_main_person;
    @BindView(R.id.iv_main_community) ImageView iv_main_community;
    @BindView(R.id.iv_main_recorder_big) ImageView iv_main_recorder_big;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private VideoFragment videoFragment;
    private PersonFragment personFragment;
    private RecorderFragment recorderFragment;
    private CommunityFragment communityFragment;

    private long exitTime = 0;

    private static final String Tag = "MainActivity";
//    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();

    }


    @Override
    protected void initView() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mToolbar.setTitle(R.string.app_name);
//        setSupportActionBar(mToolbar);

    }

    @Override
    protected void initData() {
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }


    public void setTabSelection(int index) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        setBottomButtonBgFalse();
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance("首页","Home");
                    transaction.add(R.id.layout_main_replace, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                iv_main_home.setImageResource(R.mipmap.icon_home_true);
                btn_main_home.setClickable(false);
                break;
            case 1:
                if (videoFragment == null) {
                    videoFragment = VideoFragment.newInstance("视频","Video");
                    transaction.add(R.id.layout_main_replace, videoFragment);
                } else {
                    transaction.show(videoFragment);
                }
                iv_main_video.setImageResource(R.mipmap.icon_video_true);
                btn_main_video.setClickable(false);
                break;
            case 2:
                if (recorderFragment == null) {
                    recorderFragment = RecorderFragment.newInstance("录像","Recorder");
                    transaction.add(R.id.layout_main_replace, recorderFragment);
                } else {
                    transaction.show(recorderFragment);
                }
                iv_main_recorder_big.setImageResource(R.mipmap.icon_recorder_true);
                iv_main_recorder_big.setClickable(false);
                break;
            case 3:
                if (communityFragment == null) {
                    communityFragment = CommunityFragment.newInstance("社区","Community");
                    transaction.add(R.id.layout_main_replace, communityFragment);
                } else {
                    transaction.show(communityFragment);
                }
                iv_main_community.setImageResource(R.mipmap.icon_community_true);
                btn_main_community.setClickable(false);
                break;
            case 4:
                if (personFragment == null) {
                    personFragment = PersonFragment.newInstance("我的","Person");
                    transaction.add(R.id.layout_main_replace, personFragment);
                } else {
                    transaction.show(personFragment);
                }
                iv_main_person.setImageResource(R.mipmap.icon_person_true);
                btn_main_person.setClickable(false);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void setBottomButtonBgFalse() {
        iv_main_home.setImageResource(R.mipmap.icon_home_false);
        iv_main_video.setImageResource(R.mipmap.icon_video_false);
        iv_main_person.setImageResource(R.mipmap.icon_person_false);
        iv_main_community.setImageResource(R.mipmap.icon_community_false);
        iv_main_recorder_big.setImageResource(R.mipmap.icon_recorder_false);

        btn_main_person.setClickable(true);
        btn_main_video.setClickable(true);
        btn_main_home.setClickable(true);
        btn_main_community.setClickable(true);
        iv_main_recorder_big.setClickable(true);

    }

    public void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (videoFragment != null) {
            transaction.hide(videoFragment);
        }
        if (personFragment != null) {
            transaction.hide(personFragment);
        }
        if(communityFragment!=null){
            transaction.hide(communityFragment);
        }
        if(recorderFragment!=null){
            transaction.hide(recorderFragment);
        }
    }

    @OnClick({ R.id.btn_main_home, R.id.btn_main_video, R.id.btn_main_person, R.id.btn_main_community, R.id.iv_main_recorder_big })
    public void OnTabclick(View v){
        switch (v.getId()){
            case R.id.btn_main_home:
                setTabSelection(0);
                break;
            case R.id.btn_main_video:
                setTabSelection(1);
                break;
            case R.id.iv_main_recorder_big:
                setTabSelection(2);
                break;
            case R.id.btn_main_community:
                setTabSelection(3);
                break;
            case R.id.btn_main_person:
                setTabSelection(4);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (VideoHandler.isRunning) {
//				 moveTaskToBack(false);
                Toast.makeText(this, R.string.main_handle_video, Toast.LENGTH_LONG).show();
                return true;
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), R.string.main_exit_app,
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    //System.exit(0);
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
