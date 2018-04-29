package com.richard.stepcount.net.request;

/**
 * Created by 45820 on 2016/11/5.
 */

public interface BaseView<T> {

    void onSuccess(T t);

    void onFailure(Throwable e);
}
