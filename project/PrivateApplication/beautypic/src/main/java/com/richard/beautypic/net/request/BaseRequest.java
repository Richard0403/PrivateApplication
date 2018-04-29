package com.richard.beautypic.net.request;

import android.content.Context;
import com.richard.beautypic.base.App;
import com.richard.beautypic.net.loading.ProgressSubscriber;
import com.richard.beautypic.net.loading.SubscriberOnNextListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
/**
 * by Richard on 2017/9/7
 * desc:
 */
public class BaseRequest {
    protected static <T> void toSubscribe(Observable<T> o, final BaseView<T> baseView){
        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.onFailure(e);
                    }

                    @Override
                    public void onNext(T t) {
                        baseView.onSuccess(t);
                    }
                });
    }

    protected static <T> void toLoadingSubscribe(Observable<T> o, final BaseView baseView, Context context){
        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<T>(new SubscriberOnNextListener<T>() {
                    @Override
                    public void onNext(T t) {
                        baseView.onSuccess(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.onFailure(e);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onCancle() {
                        baseView.onFailure(null);
                    }
                }, context));
    }
}
