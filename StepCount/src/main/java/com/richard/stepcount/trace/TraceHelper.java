package com.richard.stepcount.trace;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;

import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.entity.LocRequest;
import com.baidu.trace.api.entity.OnEntityListener;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.baidu.trace.model.TraceLocation;
import com.richard.stepcount.base.App;
import com.richard.stepcount.constants.AppKey;
import com.richard.stepcount.utils.MapUtil;
import com.richard.stepcount.utils.ToastUtil;

import java.util.Calendar;

/**
 * Created by xiaou on 2017/5/5.
 */

/**
 * 调用流程
 *TraceHelper.getInstance(this).initListener()
 .setTimeCycles(TraceConfig.DEFAULT_GATHER_INTERVAL, TraceConfig.DEFAULT_PACK_INTERVAL)
 .startTraceService();
 .setTraceStatusCallback();
 */


public class TraceHelper {
    private static TraceHelper instance;
    private Context mContext;

    public LBSTraceClient mClient = null;
    public Trace mTrace = null;

    private MapUtil mapUtil;

    private PowerManager.WakeLock mWakeLock;
    //是否在采集中
    public static boolean isTraceing = false;
    //轨迹服务监听器
    private OnTraceListener traceListener = null;
    //Entity监听器(用于接收实时定位回调)
    private OnEntityListener entityListener = null;

    //状态采集回掉给前端
    private TraceStatusCallback traceStatusCallback;

    TraceHelper(Context context){
        mContext = context;
        mClient = new LBSTraceClient(mContext);
//        mTrace = new Trace(AppKey.TRACE_SERVICE_ID, App.loginUser.getName()+"_"+App.loginUser.getId());
        mTrace = new Trace(AppKey.TRACE_SERVICE_ID, TraceUtil.getImei(mContext));
        mapUtil = MapUtil.getInstance();
    }

    public static TraceHelper getInstance(Context context){
        if(instance==null){
            instance = new TraceHelper(context);
        }
        return instance;
    }

    public TraceHelper setTimeCycles(int gatherInterval ,int packInterval){
        mClient.setInterval(gatherInterval, packInterval);
        return instance;
    }

    public TraceHelper startTraceService(){
        if(traceListener == null || entityListener == null){
            initListener();
        }
        mClient.startTrace(mTrace, traceListener);

        return instance;
    }

    public TraceHelper stopTraceService(){
        mClient.stopGather(traceListener);
        return instance;
    }


    /**
     * 状态采集回掉给前端
     * 只允许在MainActivity中使用
     * @param traceStatusCallback
     */
    public void setTraceStatusCallback(TraceStatusCallback traceStatusCallback){
        this.traceStatusCallback = traceStatusCallback;
    }



    public TraceHelper initListener() {

        entityListener = new OnEntityListener() {

            @Override
            public void onReceiveLocation(TraceLocation location) {

                System.out.println("TraceLocation : " + location);

                if (StatusCodes.SUCCESS != location.getStatus() || TraceUtil.isZeroPoint(location.getLatitude(),
                        location.getLongitude())) {
                    return;
                }
                LatLng currentLatLng = mapUtil.convertTraceLocation2Map(location);
                if (null == currentLatLng) {
                    return;
                }
                CurrentLocation.locTime = TraceUtil.toTimeStamp(location.getTime());
                CurrentLocation.latitude = currentLatLng.latitude;
                CurrentLocation.longitude = currentLatLng.longitude;
            }

        };

        traceListener = new OnTraceListener() {
            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    ToastUtil.showSingleToast( "轨迹服务启动成功");
                    mClient.startGather(traceListener);

                }
            }

            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    if (mWakeLock != null) {
                        if (mWakeLock.isHeld()){
                            mWakeLock.release();
                        }
                        mWakeLock = null;
                    }
                    isTraceing = false;
                    if(traceStatusCallback!=null){
                        traceStatusCallback.onStopTraceCallback();
                    }
                    ToastUtil.showSingleToast("轨迹服务已停止");
                }

            }

            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    getLock(mContext);
                    isTraceing = true;
                    if(traceStatusCallback != null){
                        traceStatusCallback.onStartGatherCallback();
                    }
                    ToastUtil.showSingleToast("轨迹开始采集");
                }
            }

            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    ToastUtil.showSingleToast("轨迹停止采集");
                    mClient.stopTrace(mTrace, traceListener);
                }
            }

            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
            }
        };

        return instance;

    }


    synchronized private PowerManager.WakeLock getLock(Context context) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld()){
                mWakeLock.release();
            }
            mWakeLock = null;
        }

        if (mWakeLock == null) {
            PowerManager mgr = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.setReferenceCounted(true);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            mWakeLock.acquire();
        }

        return (mWakeLock);
    }
}
