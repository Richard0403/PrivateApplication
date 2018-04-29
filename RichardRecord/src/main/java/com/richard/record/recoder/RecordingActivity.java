package com.richard.record.recoder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.richard.record.R;
import com.richard.record.base.BaseActivity;
import com.richard.record.constants.Config;
import com.richard.record.entity.VideoEntity;
import com.richard.record.constants.AppSetting;
import com.richard.record.utils.SDCardTool;
import com.richard.record.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

public class RecordingActivity extends BaseActivity implements MediaRecorder.OnErrorListener {
    private static final String TAG = "RecordingActivity";
    // private ScreenSwitchUtils instance;

    private TextureView surfaceView;
    private RelativeLayout layout;

    ImageView recordbutton;

    ImageView head_left;
    // ImageView head_right;
    TextView head_title;
    private TextView tv_timer;

    private TextView voice_status;


    // private int cameraId;
    // private Camera mCamera;

    // Intent locationIntent = null;

    ProximitySensor proximitySensor = null;
    // 抓拍提示音
    private MediaPlayer mediaPlayer;

    RecordingHelper recorder;

    VideoEntity video = null;

    Context mContext;

    private long start_time;
    private int record_time;
    private int latest_record_time;

    private boolean recording;
    private boolean isFirstRecord = true;
    private boolean isAutoCallClick = false;
    // SensorManager sensorManager = null; // 传感器管理器
    // Sensor mProximiny = null; // 传感器实例

    float distance = -1.0F; // 最小距离
    /**
     * 唤醒震动提示
     */
    // private Vibrator mVibrator;

    // 唤醒结果内容
    private String resultString;

    private static final int UPDATE_STATUS_TEXT = 11;
    private static final int INIT_VIEW = 1;
    private static final int UPDATE_RECORD_TIME = 4;
    private static final int PLAY_CATCH_SOUND = 5;
    private static final int PLAY_START_SOUND = 6;
    private static final int PLAY_STOP_SOUND = 7;
    private static final int CAPTURE_VIDEO = 8;

    private static final int START_RECORD = 9;
    private static final int STOP_RECORD = 10;
    private static final int CHECK_DIRECTION = 12;
//	private static final int PHONE_IN = 13;

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

//    private ImageView mWave1, mWave2, mWave3;
//    private SosView iv_sos;
//    private AnimationSet mAnimationSet1, mAnimationSet2, mAnimationSet3;

    private static final int OFFSET = 400; // 每个动画的播放时间间隔
    private static final int MSG_WAVE2_ANIMATION = 2;
    private static final int MSG_WAVE3_ANIMATION = 3;


    private Boolean isFirstRcordVideoPlayed = false;

    String VoiceLogFile;


    int mOrientation = -1;
    RecorderOrientationEventListener mRecorderOrientationEventListener;
    //create是否执行完成
    private boolean isCreated = false;
    // private VoiceAsrHelper mVoiceAsr;
    private boolean  isPause = false;
    private int pauseType = -1;

    ShakeListener mShakeListener = null;

    private RecordingHelper.TextureDestroyListener textureDestroyListener = new RecordingHelper.TextureDestroyListener() {
        //TODO texture销毁的监听
        @Override
        public void onTextureDestroy() {
            if(recording){
                isPause = true;
                stopRecording();
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer =null;
                    isFirstRcordVideoPlayed  = true;
                }
            }
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case INIT_VIEW:
                    // 初始化本地离线唤醒服务
                    // voiceServiceInit();
                    initRecordService();

                    mHandler.sendEmptyMessageDelayed(START_RECORD, 300);
                    mRecorderOrientationEventListener = new RecorderOrientationEventListener(mContext, SensorManager.SENSOR_DELAY_NORMAL);
                    if (mRecorderOrientationEventListener.canDetectOrientation()) {
                        mRecorderOrientationEventListener.enable();
                    } else {
                        Log.d("chengcj1", "Can't Detect Orientation");
                    }
                    mHandler.sendEmptyMessageDelayed(CHECK_DIRECTION, 1000);
                    //TODO other thing


                    break;
                case UPDATE_STATUS_TEXT:
                    voice_status.setText((String) msg.obj);
                    break;
                case UPDATE_RECORD_TIME:
                    if (recording) {
                        // head_title.setText(VideoUtils.formatTime(record_time));
                        tv_timer.setText(VideoUtils.formatTime(record_time));
                    }
                    break;
                case PLAY_CATCH_SOUND:
                    playCatchSound();
                    break;
                case PLAY_START_SOUND:
                    if (isFirstRecord) {
                        playStartSound();
                    }
                    break;
                case PLAY_STOP_SOUND:
                    playStopSound();
                    break;
                case CAPTURE_VIDEO:
                    // 语音提示
                    // mHandler.sendEmptyMessage(PLAY_SOUND);
                    catchVideo();

                    break;

                case START_RECORD:
                    startRecording();
                    //初始化轨迹纪录
//                    traceHelper = new RecordingTraceHelper(getApplicationContext());
//                    traceHelper.startRecordingTrace();
                    //模拟点击聚焦
                    surfaceView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                            SystemClock.uptimeMillis(),
                            MotionEvent.ACTION_DOWN,
                            surfaceView.getLeft()+100,
                            surfaceView.getBottom()/2+100,
                            0));

                    break;

                case STOP_RECORD:
                    stopRecording();
                    break;

//                case MSG_WAVE2_ANIMATION:
//                    mWave2.startAnimation(mAnimationSet2);
//                    break;
//                case MSG_WAVE3_ANIMATION:
//                    mWave3.startAnimation(mAnimationSet3);
//                    break;
                case CHECK_DIRECTION:


                    // TODO Auto-generated method stub
                    if(mRecorderOrientationEventListener!=null){
                        mRecorderOrientationEventListener.disable();
                        mRecorderOrientationEventListener=null;
                    }
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        // instance = ScreenSwitchUtils.init(this.getApplicationContext());
        // mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mContext = this;
        initView();

        //初始化录像
        recorder = new RecordingHelper(mContext, surfaceView);
        recorder.setOnTextureDestroyListener(textureDestroyListener);
        mHandler.sendEmptyMessageDelayed(INIT_VIEW,200);
        // add other thing to msg----INIT_VIEW


//        mPhoneReceiver = new PhoneReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
//        intentFilter.setPriority(Integer.MAX_VALUE);
//        registerReceiver(mPhoneReceiver, intentFilter);
//
        mAm = (AudioManager) getSystemService(AUDIO_SERVICE);

    }
    @Override
    protected void initView() {
        surfaceView = (TextureView) this.findViewById(R.id.surfaceView);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getY()>surfaceView.getBottom()/2)
                    recorder.focusOnTouch(event);
                return false;
            }
        });
//		recorder = new RecordingHelper(mContext, surfaceView);

        layout = (RelativeLayout) this.findViewById(R.id.layout);
        recordbutton = (ImageView) this.findViewById(R.id.recordbutton);

        tv_timer = (TextView) findViewById(R.id.tv_timer);
        head_left = (ImageView) this.findViewById(R.id.head_left);
        head_left.setImageResource(R.mipmap.ic_back);
        head_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doBack();
            }

        });
        // head_right = (ImageView) this.findViewById(R.id.head_right);
        // head_right.setImageResource(R.drawable.icon_menu_white);
        // head_right.setOnClickListener(new View.OnClickListener(){
        //
        // @Override
        // public void onClick(View v) {
        // goVideoList(v);
        // }
        // });
        head_title = (TextView) this.findViewById(R.id.head_title);
        tv_timer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_timer.setLayoutParams(layoutParams);
        tv_timer.setBackgroundResource(R.drawable.edit_round_rect);
        // tv_timer.setBackgroundColor(getResources().getColor(R.color.white));
        // tv_timer.getBackground().setAlpha(30);
        tv_timer.setText(VideoUtils.formatTime(record_time));

        voice_status = (TextView) this.findViewById(R.id.voice_status);

//        mWave1 = (ImageView) findViewById(R.id.wave1);
//        mWave2 = (ImageView) findViewById(R.id.wave2);
//        mWave3 = (ImageView) findViewById(R.id.wave3);
//
//        mAnimationSet1 = initAnimationSet();
//        mAnimationSet2 = initAnimationSet();
//        mAnimationSet3 = initAnimationSet();

//        iv_sos = (SosView) findViewById(R.id.iv_sos);
//        iv_sos.setVibratorListener(new VibratorStopCallback() {
//
//            @Override
//            public void vibratorStop(boolean isStop) {
//                clearWaveAnimation();
//            }
//        });
//        iv_sos.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        showWaveAnimation();
//                        iv_sos.startVibrator();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        clearWaveAnimation();
//                        iv_sos.stopVibrator();
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        clearWaveAnimation();
//                        iv_sos.stopVibrator();
//                }
//                return true;
//            }
//        });
    }

    @Override
    protected void initData() {

    }

//    private AnimationSet initAnimationSet() {
//        AnimationSet as = new AnimationSet(true);
//        ScaleAnimation sa = new ScaleAnimation(1f, 8.3f, 1f, 8.3f,
//                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
//                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//        sa.setDuration(OFFSET * 3);
//        sa.setRepeatCount(Animation.INFINITE);// 设置循环
//        AlphaAnimation aa = new AlphaAnimation(1, 0.1f);
//        aa.setDuration(OFFSET * 3);
//        aa.setRepeatCount(Animation.INFINITE);// 设置循环
//        as.addAnimation(sa);
//        as.addAnimation(aa);
//        return as;
//    }

//    private void showWaveAnimation() {
//        mWave1.startAnimation(mAnimationSet1);
//        mHandler.sendEmptyMessageDelayed(MSG_WAVE2_ANIMATION, OFFSET);
//        mHandler.sendEmptyMessageDelayed(MSG_WAVE3_ANIMATION, OFFSET * 2);
//    }
//
//    private void clearWaveAnimation() {
//        mWave1.clearAnimation();
//        mWave2.clearAnimation();
//        mWave3.clearAnimation();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            layout.setVisibility(ViewGroup.VISIBLE);
        }
        return super.onTouchEvent(event);
    }


    public void record(View v) {
        switch (v.getId()) {
            case R.id.recordbutton:
                WakeLocker.acquire(this);
                if (!recording) {
                    try {
                        //点击防止
                        if(!isFirstRecord)
                            return;
                        boolean res = recorder.startRecording();
                        if (!res) {
                            setCaptureStatus(false);
                            setStatusText("无法启动录像");
                            ToastUtil.show(mContext,
                                    R.string.videorecording_cont_start_recording);
                            return;
                        } else {
                            setCaptureStatus(true);
                        }
                        if (!isAutoCallClick) {
                            startTimer();
                        }

                        if (isFirstRcordVideoPlayed) {
                            startRecordService();
                        }

                        recording = !recording;

                        // 语音提示
                        mHandler.sendEmptyMessage(PLAY_START_SOUND);

                        VoiceLogFile = AppSetting.APP_CACHE_PATH + "/Voice_"
                                + System.currentTimeMillis() + ".txt";

                    } catch (Exception e) {
                        e.printStackTrace();
                        setStatusText("启动录像异常");
                    }
                } else {
                    WakeLocker.release();
                    isFirstRecord = false;

                    if (!isAutoCallClick) {
                        stopTimer();
                    }

                    recording = !recording;
                    setCaptureStatus(false);
                    recorder.stopRecording();

                    stopRecordService();

                    mHandler.sendEmptyMessage(PLAY_STOP_SOUND);
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "==onResume");
        super.onResume();

        if(isPause && pauseType!=2){  //除了来电，重新启动录像
            mHandler.sendEmptyMessageDelayed(START_RECORD, 200);
//	    	recordbutton.callOnClick();
        }
    }

    protected void onStop() {
        Log.i(TAG, "==onStop");
        super.onStop();
    };

    protected void onRestart() {
        Log.i(TAG, "==onRestart");
        super.onRestart();

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "==onPause");
        if (WakeLocker.isScreenOff(this) == false && recording){
//			recordbutton.callOnClick();
            isPause = true;
            stopRecording();
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer =null;
                isFirstRcordVideoPlayed  = true;
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {


        if(mRecorderOrientationEventListener != null){
            mRecorderOrientationEventListener.disable();
        }
        if (recorder != null) {
            recorder.stopVideoRecording();
        }
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 销毁唤醒监听
        destroyRecordService();

        // if(LocationService.isRunning){
        // stopService(locationIntent);
        // }
//        if(traceHelper!=null)
//            traceHelper.stopRecordingTrace();
        mAm.abandonAudioFocus(afChangeListener);
        super.onDestroy();
    }

    private void startRecording() {
        try {
            isPause = false;
            pauseType =-1;
            recorder.setContext(mContext);
            boolean res = recorder.startRecording();
            if (!res) {
                setCaptureStatus(false);
                setStatusText("启动录像失败");
                ToastUtil.show(mContext,
                        R.string.videorecording_cont_start_recording);
                return;
            } else {
                setCaptureStatus(true);
            }
            if (!isAutoCallClick) {
                startTimer();
            }
            if (isFirstRcordVideoPlayed) {
                startRecordService();
            }
            recording = !recording;

            // 语音提示
            mHandler.sendEmptyMessage(PLAY_START_SOUND);

            VoiceLogFile = AppSetting.APP_CACHE_PATH + "/Voice_"
                    + recorder.video.startTime + ".txt";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {

        WakeLocker.release();
        isFirstRecord = false;

        stopTimer();

        recording = !recording;
        setCaptureStatus(false);
        recorder.stopRecording();

        // 停止录制服务
        stopRecordService();
        if(!isPause)
            mHandler.sendEmptyMessage(PLAY_STOP_SOUND);

    }

    public void startRecordService() {
        if (Config.getConfig(getApplicationContext()).isVoiceCapture()){
//            voiceServiceStart();
            mAm.abandonAudioFocus(afChangeListener);
        }


        if (Config.getConfig(getApplicationContext()).isGestureCapture())
            startProximitySensor();

        if (Config.getConfig(getApplicationContext()).isBlueToothCapture()) {

        }

        if(Config.getConfig(getApplicationContext()).isShakeCapture()){
            startShakeSensor();
        }

    }

    public void stopRecordService() {
        if (Config.getConfig(getApplicationContext()).isVoiceCapture())
//            voiceServiceStop();

        if (Config.getConfig(getApplicationContext()).isGestureCapture())
            stopProximitySensor();

        if (Config.getConfig(getApplicationContext()).isBlueToothCapture()) {

        }
        if(Config.getConfig(getApplicationContext()).isShakeCapture()){
            stopShakeSensor();
        }
    }

    public void initRecordService() {
        if (Config.getConfig(mContext).isVoiceCapture())
//            voiceServiceInit();

        if (Config.getConfig(mContext).isGestureCapture()) {
            // stopProximitySensor();
        }

        if (Config.getConfig(mContext).isBlueToothCapture()) {

        }
    }

    public void destroyRecordService() {

        // 销毁唤醒监听
        if (Config.getConfig(mContext).isVoiceCapture())
//            voiceServiceDestroy();

        if (Config.getConfig(mContext).isGestureCapture()) {
            stopProximitySensor();
        }

        if (Config.getConfig(mContext).isBlueToothCapture()) {

        }
    }









    public void goVideoList(View v) {

//        startActivity(new Intent(RecordingActivity.this,
//               VedioListActivity.class));
        // VideoRecordingActivity.this.finish();
    }

    public void doBack() {
        if (recording) {
            recordbutton.callOnClick();
        }
        finish();
    }

    private ProximitySensor.ProximityChangedListener proxListener = new ProximitySensor.ProximityChangedListener() {

        @Override
        public void isChanged(boolean paramBoolean) {
            if (paramBoolean) {
                //TODO
                if (!recording) {
                    return;
                }
                setStatusText(VideoUtils
                        .formatTime(record_time) + " 已手势抓拍");
                catchVideo();
            }
        }
    };
    public void startProximitySensor() {
        if (proximitySensor == null) {
            proximitySensor = new ProximitySensor(mContext,proxListener);
        }
    }

    public void stopProximitySensor() {
        if(null!=proximitySensor){
            proximitySensor.close();
            proximitySensor = null;
        }


    }

    /**
     * 播放提示音
     */
    public void playSound(final int sound_id) {

        if (null != mediaPlayer) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        //TODO
        requestFocus();
        mediaPlayer = MediaPlayer.create(this, sound_id);
        try {
            // mediaPlayer.prepare();
            mediaPlayer
                    .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {// 播出完毕事件
                        @Override
                        public void onCompletion(MediaPlayer arg0) {
                            // mediaPlayer.release();
                            if (sound_id == R.raw.start) {
                                // voiceServiceStart();
                                isFirstRcordVideoPlayed = true;
                                if(recording)
                                    startRecordService();
                            }else if(sound_id == R.raw.stop || sound_id == R.raw.stop_without_record){
                                finish();
                            }
                            mediaPlayer.release();
                            mediaPlayer = null;
                            mAm.abandonAudioFocus(afChangeListener);
                        }
                    });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {// 错误处理事件
                @Override
                public boolean onError(MediaPlayer player, int arg1,
                                       int arg2) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                    return false;
                }
            });

        } catch (IllegalStateException e) {
            e.printStackTrace();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer.start();// 开始或恢复播放
    }

    public void playStartSound() {
        playSound(R.raw.start);

    }

    public void playStopSound() {
        if (recorder.event_number > 0) {
            playSound(R.raw.stop);
        } else {
            playSound(R.raw.stop_without_record);
        }

    }

    public void playCatchSound() {
        playSound(R.raw.beep);
    }

    public void catchVideo() {
        //距离上次抓拍3秒内无效
        if(record_time-latest_record_time>3){
            if( !SDCardTool.hasEnoughSpace()){
                setStatusText(getResources().getString(R.string.videorecording_no_enough_garbage));
                ToastUtil.show(mContext, R.string.videorecording_no_enough_garbage);
                return;
            }
            //语音提示
            mHandler.sendEmptyMessage(PLAY_CATCH_SOUND);

            //抓取视频
            recorder.captureVideo();
            latest_record_time = record_time;
        }


    }

    public void startShakeSensor() {
        if (mShakeListener == null) {
            mShakeListener = new ShakeListener(mContext);
            mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

                public void onShake(double acceleration) {
//	            	stopShakeSensor();

                    setStatusText(VideoUtils.formatTime(record_time) + " 已碰撞抓拍");
                    catchVideo();
//					startShakeSensor();
//	                new Handler().postDelayed(new Runnable() {
//	                    @Override
//	                    public void run() {
//	                        ToastUtil.show(mContext, "呵呵，成功了！。\n再试一次吧！");
//	                        mShakeListener.start();
//	                    }
//	                }, 2000);
                }
            });
        }
    }

    public void stopShakeSensor() {
        if(null != mShakeListener){
            mShakeListener.stop();
            mShakeListener = null;
        }
    }


    public void setCaptureStatus(boolean isRecording) {
        if (isRecording) {
            recordbutton.setImageResource(R.mipmap.btn_stop);
        } else {
            recordbutton.setImageResource(R.mipmap.btn_start);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (recording) {
                    recordbutton.callOnClick();
                }
                break;
            case  KeyEvent.KEYCODE_ENTER:
                if (recording&&Config.getConfig(mContext).isBlueToothCapture()) {
                    setStatusText(VideoUtils.formatTime(record_time) + " 已蓝牙抓拍");
                    catchVideo();
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (recording&&Config.getConfig(mContext).isBlueToothCapture()) {
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startTimer(){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(UPDATE_RECORD_TIME);
                    record_time ++;
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, 1000, 1000);

    }

    private void stopTimer(){

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        record_time = 0;
        latest_record_time = 0;

    }
    /**
     * 更新状态栏文本
     * @param content
     */
    public void setStatusText(String content){
        Message message = Message.obtain();
        message.obj = content;
        message.what = UPDATE_STATUS_TEXT;

        mHandler.sendMessage(message);
    }





    public class MusicIntentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
                KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(
                        Intent.EXTRA_KEY_EVENT);
                if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                    return;

                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.KEYCODE_HEADSETHOOK:
                    case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PLAY:
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PAUSE:
                        break;
                    case KeyEvent.KEYCODE_MEDIA_STOP:
                        break;
                    case KeyEvent.KEYCODE_MEDIA_NEXT:
                        catchVideo();
                        break;
                    case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                        break;
                }
            }
        }
    }


    private class RecorderOrientationEventListener extends OrientationEventListener {
        public RecorderOrientationEventListener(Context context) {
            super(context);
        }

        public RecorderOrientationEventListener(Context context, int rate) {
            super(context, rate);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                return;
            }

            //保证只返回四个方向
            int newOrientation = ((orientation + 45) / 90 * 90) % 360  ;
            if (newOrientation != mOrientation) {
                mOrientation = newOrientation;
                if(recorder!=null){
                    recorder.setOrientation(mOrientation);
                }
                //返回的mOrientation就是手机方向，为0°、90°、180°和270°中的一个
            }
        }
    }
    public int getOrientation(){
        return mOrientation;
    }

    //MediaRecorder异常错误捕捉
    @Override
    public void onError(MediaRecorder arg0, int arg1, int arg2) {
        recorder.handleMediaRecorderError();
        stopTimer();
        recording = !recording;
        setCaptureStatus(false);
        stopRecordService();
        finish();
    }
    private AudioManager mAm;
    private boolean requestFocus() {
        // Request audio focus for playback
        int result = mAm.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE);
//        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        return result == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE;
    }
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
//                pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
//                resume();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // mAm.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
//                mAm.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                // Stop playback
//                stop();
            }
        }
    };
}
