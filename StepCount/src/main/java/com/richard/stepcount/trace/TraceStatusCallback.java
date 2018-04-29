package com.richard.stepcount.trace;

/**
 * Created by xiaou on 2017/5/5.
 */

public interface TraceStatusCallback {
    //停止服务
    void onStopTraceCallback();
    //开始采集
    void onStartGatherCallback();
}
