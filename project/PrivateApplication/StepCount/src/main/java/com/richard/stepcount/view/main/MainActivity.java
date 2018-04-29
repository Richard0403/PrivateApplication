package com.richard.stepcount.view.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.richard.stepcount.R;
import com.richard.stepcount.view.base.BaseActivity;
import com.richard.stepcount.view.base.BaseFragment;
import com.richard.stepcount.counter.Constant;
import com.richard.stepcount.counter.StepService;
import com.richard.stepcount.trace.TraceConfig;
import com.richard.stepcount.trace.TraceHelper;
import com.richard.stepcount.trace.TraceStatusCallback;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import com.richard.stepcount.utils.LogUtil;

public class MainActivity extends BaseActivity{
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_rank)
    ImageView iv_rank;
    @BindView(R.id.iv_discover)
    ImageView iv_discover;
    @BindView(R.id.iv_person)
    ImageView iv_person;

    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.tv_rank)
    TextView tv_rank;
    @BindView(R.id.tv_discover)
    TextView tv_discover;
    @BindView(R.id.tv_person)
    TextView tv_person;

    @BindColor(R.color.color_title_yellow)
    int yellow;
    @BindColor(R.color.txt_light_grey)
    int grey;


    private Context stepContext;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction = null;
    private int latestIndex = -1;
    private BaseFragment homePageFragment;
    private BaseFragment rankFragment;
    private BaseFragment findFragment;
    private BaseFragment personFragment;

    private static final int INIT_CREATE = 0x01;
    private static final int REFRESH_STEPS = 0x02;

    private boolean isBound = false;

    //serviceMessenger表示的是Service端的Messenger，其内部指向了MyService的ServiceHandler实例
    //可以用serviceMessenger向MyService发送消息
    private Messenger serviceMessenger = null;


    Handler delayHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case INIT_CREATE:
                    break;
                case Constant.MSG_FROM_SERVER:
                    Bundle data = msg.getData();
                    if(data != null){
                        int step = data.getInt("steps");
                        ((HomeFragment)homePageFragment).setStep(step);
                        LogUtil.setLog("DemoLog", "客户端收到Service的消息: " + step);
                    }
                    break;
                    default:
                        break;
            }
        }
    };

    /**
     *    clientMessenger是客户端自身的Messenger，内部指向了ClientHandler的实例
     *    MyService可以通过Message的replyTo得到clientMessenger，从而MyService可以向客户端发送消息，
     *    并由ClientHandler接收并处理来自于Service的消息
     */
    private Messenger clientMessenger = new Messenger(delayHandler);
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        intTab();
        initTrace();
    }

    @Override
    protected void initData() {

    }

    private void intTab(){
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }

    @OnClick({R.id.rl_home, R.id.rl_rank, R.id.rl_discover,  R.id.rl_person})
    protected void Onclick(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                setTabSelection(0);
                break;
            case R.id.rl_rank:
                setTabSelection(1);
                break;
            case R.id.rl_discover:
                setTabSelection(2);
                break;
            case R.id.rl_person:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    public void setTabSelection(int index) {
        if (index == latestIndex && latestIndex != -1) {
            return;
        }
        setTextAndImage();
        /** 隐藏软键盘 **/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        switch (index) {
            case 0:
                if (homePageFragment == null) {
                    homePageFragment = HomeFragment.newInstance();
                }
                iv_home.setSelected(true);
                tv_home.setTextColor(yellow);
                break;
            case 1:
                if (rankFragment == null) {
                    rankFragment = RankFragment.newInstance();
                }
                iv_rank.setSelected(true);
                tv_rank.setTextColor(yellow);
                break;
            case 2:
                if (findFragment == null) {
                    findFragment = FindFragment.newInstance();
                }
                iv_discover.setSelected(true);
                tv_discover.setTextColor(yellow);
                break;
            case 3:
                if (personFragment == null) {
                    personFragment = PersonFragment.newInstance();
                }
                iv_person.setSelected(true);
                tv_person.setTextColor(yellow);
                break;
            default:
                break;
        }
        switchFragment(latestIndex, index);
        latestIndex = index;
    }

    private void setTextAndImage() {
        iv_rank.setSelected(false);
        iv_person.setSelected(false);
        iv_discover.setSelected(false);
        iv_home.setSelected(false);
        tv_person.setTextColor(grey);
        tv_rank.setTextColor(grey);
        tv_discover.setTextColor(grey);
        tv_home.setTextColor(grey);
    }


    private void switchFragment(int from, int to) {
        BaseFragment fromFragment = null;
        BaseFragment toFragment = null;

        if (from == to) {
            return;
        }
        fragmentTransaction = fragmentManager.beginTransaction();

        if (from == -1) {
            toFragment = findFragmentByTag(to);
            fragmentTransaction.add(R.id.fl_main, toFragment)
                    .commit();
        } else {

            fromFragment = findFragmentByTag(from);
            toFragment = findFragmentByTag(to);

            if (fromFragment == null) {
                fragmentTransaction.add(R.id.fl_main, toFragment)
                        .commit();
            } else {
                if (!toFragment.isAdded()) { // 先判断是否被add过
                    fragmentTransaction
                            .add(R.id.fl_main, toFragment)
                            .hide(fromFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    fragmentTransaction.show(toFragment);
                    fragmentTransaction.hide(fromFragment);
                    fragmentTransaction.commit(); // 隐藏当前的fragment，显示下一个
                }
            }
        }

    }

    private BaseFragment findFragmentByTag(int index) {
        switch (index) {
            case 0:
                return homePageFragment;
            case 1:
                return rankFragment;
            case 2:
                return findFragment;
            case 3:
                return personFragment;
        }
        return null;
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
    @Override
    protected void onStart() {
        super.onStart();
        setupService();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    private void setupService() {
        Intent intent = new Intent(getApplicationContext(), StepService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
//        startService(intent);
    }


    private void initTrace() {
        TraceHelper.getInstance(this).initListener()
                .setTimeCycles(TraceConfig.DEFAULT_GATHER_INTERVAL, TraceConfig.DEFAULT_PACK_INTERVAL)
                .startTraceService()
                .setTraceStatusCallback(new TraceStatusCallback() {
                    @Override
                    public void onStopTraceCallback() {
                        if(homePageFragment!=null){
                            ((HomeFragment)homePageFragment).setStepCountFragmentTraceStatus();
                        }
                    }

                    @Override
                    public void onStartGatherCallback() {
                        if(homePageFragment!=null){
                            ((HomeFragment)homePageFragment).setStepCountFragmentTraceStatus();
                        }
                    }
                });
    }




    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //客户端与Service建立连接
            LogUtil.setLog("DemoLog", "客户端 onServiceConnected");
            //我们可以通过从Service的onBind方法中返回的IBinder初始化一个指向Service端的Messenger
            serviceMessenger = new Messenger(binder);
            isBound = true;

            Message msg = Message.obtain();
            msg.what = Constant.MSG_FROM_CLIENT;
            //此处跨进程Message通信不能将msg.obj设置为non-Parcelable的对象，应该使用Bundle
            Bundle data = new Bundle();
            data.putString("msg", "你好，MyService，我是客户端");
            msg.setData(data);

            //需要将Message的replyTo设置为客户端的clientMessenger，
            msg.replyTo = clientMessenger;
            try {
                LogUtil.setLog("DemoLog", "客户端向service发送信息");
                serviceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.setLog("DemoLog", "客户端向service发送消息失败: " + e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //客户端与Service失去连接
            serviceMessenger = null;
            isBound = false;
            LogUtil.setLog("DemoLog", "客户端 onServiceDisconnected");
        }
    };

}
