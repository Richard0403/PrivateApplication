package com.richard.record.navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviStaticInfo;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.view.NextTurnTipView;
import com.amap.api.navi.view.TrafficButtonView;
import com.autonavi.tbt.NaviStaticInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.richard.record.R;
import com.richard.record.base.BaseActivity;
import com.richard.record.recoder.RecordingFragment;


public class RouteNaviActivity extends BaseActivity implements AMapNaviListener, AMapNaviViewListener,OnErrorListener,OnClickListener {
	private static final String Tag = "RouteNaviActivity";
	AMapNaviView mAMapNaviView;
	AMapNavi mAMapNavi;
	TTSController mTtsManager;
	private LinearLayout layout_main_replace;
	private RecordingFragment sf_fragment;
	boolean isCameraExtend = false;
	
	private LinearLayout layout_click_area,layout_main_replace_bg;
	private TextView tv_distance;
	private NextTurnTipView iv_turn;
	private TrafficButtonView tbv_traffic;
	private TextView tv_target;
	
	private ImageView iv_close;
	private TextView tv_rest_distance;
	private TextView tv_rest_time;
	private LinearLayout ll_continue_navi;
	private RelativeLayout rl_navi_bottom;
	private boolean recording;//是否需要录像
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_basic_navi);
		initView();
		
		mTtsManager = TTSController.getInstance(getApplicationContext());
		mTtsManager.init();
		
		mAMapNaviView.onCreate(savedInstanceState);
		mAMapNaviView.setAMapNaviViewListener(this);
		mAMapNaviView.setLockZoom(17);//缩放级别由小到大【3-19】
		mAMapNavi = AMapNavi.getInstance(getApplicationContext());
		mAMapNavi.addAMapNaviListener(this);
		mAMapNavi.addAMapNaviListener(mTtsManager);
		mAMapNavi.setEmulatorNaviSpeed(60);
		boolean gps=getIntent().getBooleanExtra("gps", false);
		if(gps){
			mAMapNavi.startNavi(AMapNavi.GPSNaviMode);
		}else{
			mAMapNavi.startNavi(AMapNavi.EmulatorNaviMode);	
		}
		
		
		customUI();
		
		recording=getIntent().getBooleanExtra("recording", false);
		if(recording){initCameraView();}

	}

	protected void initView() {
		mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
		AMapNaviViewOptions mapOptions = mAMapNaviView.getViewOptions();
		iv_turn = (NextTurnTipView) findViewById(R.id.iv_turn);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		tv_target = (TextView) findViewById(R.id.tv_target);
		tbv_traffic = (TrafficButtonView) findViewById(R.id.tbv_traffic);
		iv_close = (ImageView) findViewById(R.id.iv_close);
		iv_close.setOnClickListener(this);
		tv_rest_distance = (TextView) findViewById(R.id.tv_rest_distance);
		tv_rest_time = (TextView) findViewById(R.id.tv_rest_time);
		ll_continue_navi = (LinearLayout) findViewById(R.id.ll_continue_navi);
		ll_continue_navi.setOnClickListener(this);
		rl_navi_bottom = (RelativeLayout) findViewById(R.id.rl_navi_bottom);
		layout_main_replace_bg = (LinearLayout) findViewById(R.id.layout_main_replace_bg);
		
		 //自定View
		mAMapNaviView.setLazyNextTurnTipView(iv_turn);
		mAMapNaviView.setLazyTrafficButtonView(tbv_traffic);
		mapOptions.setLayoutVisible(false);
		//TODO
		
		layout_click_area = (LinearLayout) findViewById(R.id.layout_click_area);
		layout_click_area.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(recording){
					LayoutParams tempParams = (LayoutParams) layout_main_replace.getLayoutParams();
					layout_main_replace.setLayoutParams(mAMapNaviView.getLayoutParams());
					mAMapNaviView.setLayoutParams(tempParams);
					isCameraExtend = !isCameraExtend;
					layout_click_area.removeAllViews();
					layout_main_replace_bg.bringToFront();
					if(isCameraExtend){
						mAMapNaviView.bringToFront();
					}else{
						layout_main_replace.bringToFront();
					}
					v.bringToFront();
					rl_navi_bottom.bringToFront();
				}
			}
		});
	}

	@Override
	protected void initData() {

	}

	private void initCameraView() {
		layout_main_replace = (LinearLayout) findViewById(R.id.layout_main_replace);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		sf_fragment = new RecordingFragment();
		transaction.add(R.id.layout_main_replace, sf_fragment);
		transaction.commit();
		
	}

	/**
	 * 自定义UI选项
	 */
	private void customUI() {
	    AMapNaviViewOptions viewOptions = mAMapNaviView.getViewOptions();
	    //主动隐藏导航光柱
	    viewOptions.setTrafficBarEnabled(false);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_continue_navi:
			mAMapNavi.resumeNavi();
			mAMapNaviView.recoverLockMode();
			ll_continue_navi.setVisibility(View.INVISIBLE);
			break;
		case R.id.iv_close:
			final AlertDialog  myDialog;
			myDialog = new AlertDialog.Builder(this).create();  
			myDialog.setCanceledOnTouchOutside(false);
			myDialog.show();  
			myDialog.getWindow().setContentView(R.layout.dialog_tips); 
			TextView tv_title = (TextView) myDialog.getWindow().findViewById(R.id.tv_title); 
			tv_title.setText("确定退出导航？");
			TextView comfirm=(TextView) myDialog.getWindow().findViewById(R.id.tv_confirm); 
			comfirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//退出
					myDialog.dismiss();
					finish();
				}
			});
			TextView cancle=(TextView) myDialog.getWindow().findViewById(R.id.tv_cancle);
			cancle.setOnClickListener(new OnClickListener() {
				@Override  
				public void onClick(View v) {  
					myDialog.dismiss();
				}  
			}); 
			break;

		default:
			break;
		}
		
	}


	@Override
	protected void onResume() {
		super.onResume();
		mAMapNaviView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mAMapNaviView.onPause();
		//        仅仅是停止你当前在说的这句话，一会到新的路口还是会再说的
		mTtsManager.stopSpeaking();
		//
		//        停止导航之后，会触及底层stop，然后就不会再有回调了，但是讯飞当前还是没有说完的半句话还是会说完
		//        mAMapNavi.stopNavi();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mAMapNaviView.onDestroy();
		mAMapNavi.stopNavi();
		//还要使用该对象实例，不可destroy
//		mAMapNavi.destroy();
		mTtsManager.destroy();
	}

	@Override
	public void onInitNaviFailure() {
		Toast.makeText(this, "init navi Failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onInitNaviSuccess() {
	}

	@Override
	public void onStartNavi(int type) {

	}

	@Override
	public void onTrafficStatusUpdate() {

	}

	@Override
	public void onLocationChange(AMapNaviLocation location) {

	}

	@Override
	public void onGetNavigationText(int type, String text) {

	}

	@Override
	public void onEndEmulatorNavi() {
	}

	@Override
	public void onArriveDestination() {
	}

	@Override
	public void onArriveDestination(NaviStaticInfo naviStaticInfo) {

	}

	@Override
	public void onArriveDestination(AMapNaviStaticInfo aMapNaviStaticInfo) {

	}

	@Override
	public void onCalculateRouteSuccess() {
	}

	@Override
	public void onCalculateRouteFailure(int errorInfo) {
	}

	@Override
	public void onReCalculateRouteForYaw() {

	}

	@Override
	public void onReCalculateRouteForTrafficJam() {

	}

	@Override
	public void onArrivedWayPoint(int wayID) {

	}

	@Override
	public void onGpsOpenStatus(boolean enabled) {
	}

	@Override
	public void onNaviSetting() {
	}

	@Override
	public void onNaviMapMode(int isLock) {
		//地图状态，0:车头朝上状态；1:非锁车状态,即车标可以任意显示在地图区域内。
		Log.i(Tag, "onNaviMapMode"+isLock);
		if(isLock == 1){
			mAMapNavi.pauseNavi();
			ll_continue_navi.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onNaviCancel() {
		Log.i("Navi", "onNaviCancel");
		sf_fragment.stopRecording();
		finish();
	}

	@Override
	public void onNaviTurnClick() {

	}

	@Override
	public void onNextRoadClick() {

	}

	@Override
	public void onScanViewButtonClick() {
	}

	@Deprecated
	@Override
	public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo naviinfo) {
		//TODO
		tv_distance.setText(naviinfo.getCurStepRetainDistance()+"");
		tv_target.setText(naviinfo.getNextRoadName());
		
		tv_rest_distance.setText((float)naviinfo.getPathRetainDistance()/1000+"公里");
		int time = naviinfo.getPathRetainTime();
		tv_rest_time.setText(time/3600+"小时"+(time-time/3600*3600)/60+"分钟");
		
	}

	@Override
	public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

	}

	@Override
	public void showCross(AMapNaviCross aMapNaviCross) {
	}

	@Override
	public void hideCross() {
	}

	@Override
	public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

	}

	@Override
	public void hideLaneInfo() {

	}

	@Override
	public void onCalculateMultipleRoutesSuccess(int[] ints) {

	}

	@Override
	public void notifyParallelRoad(int i) {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

	}

	@Override
	public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

	}

	@Override
	public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

	}

	@Override
	public void onLockMap(boolean isLock) {
	}

	@Override
	public void onNaviViewLoaded() {
	}

	@Override
	public boolean onNaviBackClick() {
		Log.i("Navi", "onNaviBackClick");
		return false;
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		sf_fragment.stopRecording();
		
	}

	
}
