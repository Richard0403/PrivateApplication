package com.richard.diary.http;

import com.richard.diary.common.cache.ActivityCollector;
import com.richard.diary.common.db.AppConst;
import com.richard.diary.common.utils.LogUtil;
import com.richard.diary.common.utils.StringUtils;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.loading.ProgressSubscriber;
import com.richard.diary.http.loading.SubscriberOnNextListener;

import java.io.File;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 发起网络请求的类
 *
 * @param <T>
 */
public abstract class HttpRequest<T extends BaseEntity> {

    private static final int ERROR = 0x02;

    private final String TAG = getClass().getSimpleName();

    private List<Observable> lastObservables = new ArrayList<>();

    public HttpRequest() {
    }

    public String createJson(){
        return "{}";
    }



    /**
     * 获取正常返回，子类重写此方法
     *
     * @param t
     */
    protected void onSuccess(T t) {

    }

    /**
     * 默认弹出 Toast ，子类要处理请求失败，重写此方法
     *
     * @param code
     * @param msg
     */
    protected void onFail(int code, String msg, BaseEntity entity) {
        CodeFilter.filter(code, msg);
        ToastUtil.showSingleToast(msg);
    }



    /**
     * 发起一般的网络请求
     * @param clazz
     * @param methodName
     */
    public synchronized void start(Class<?> clazz, String methodName) {
        start(clazz, methodName, false);
    }
    public synchronized void start(Class<?> clazz, String methodName, boolean isShowDialog) {

        String json = createJson();

        if (StringUtils.isEmpty(json)){
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConst.MEDIA_TYPE_FORMAT_JSON), json);

        Observable observable = null;

        try {
            Method method = clazz.getMethod(methodName, RequestBody.class);
            Object service = RetroManager.getInstance().createService(clazz, methodName);
            observable = (Observable) method.invoke(service, requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (observable == null) {
            throw new IllegalArgumentException("observable can not be null");
        }
        if(isShowDialog){
            subscriber(observable, progressSubscriber);
        }else{
            subscriber(observable, subscriber);
        }
    }

    /**
     * 上传图片
     */
    public synchronized void startFile(Class<?> clazz, String methodName, String[] fileName, String[] filePath, boolean isShowDialog) {

        String json = createJson();
        if (StringUtils.isEmpty(json)){
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i=0;i<fileName.length;i++) {
            File file = new File(filePath[i]);
            RequestBody requestBody = RequestBody.create(MediaType.parse(AppConst.MEDIA_TYPE_FORMAT_IMG), file);
            builder.addFormDataPart(fileName[i], file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        Observable observable = null;
        try {
            Method method = clazz.getMethod(methodName, MultipartBody.class);
            Object service = RetroManager.getInstance().createService(clazz, methodName);
            observable = (Observable) method.invoke(service, multipartBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (observable == null) {
            throw new IllegalArgumentException("observable can not be null");
        }
        if(isShowDialog){
            subscriber(observable, progressSubscriber);
        }else{
            subscriber(observable, subscriber);
        }
    }

    /**
     * 上传多图片
     */
    public synchronized void startMultiFile(Class<?> clazz, String methodName, String fileName, List<String> filePath, boolean isShowDialog) {

        String json = createJson();
        if (StringUtils.isEmpty(json)){
            return;
        }

        List<MultipartBody.Part> parts = new ArrayList<>(filePath.size());
        for (String path : filePath) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse(AppConst.MEDIA_TYPE_FORMAT_IMG), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(fileName, file.getName(), requestBody);
            parts.add(part);
        }
        Observable observable = null;
        try {
            Method method = clazz.getMethod(methodName, List.class);
            Object service = RetroManager.getInstance().createService(clazz, methodName);
            observable = (Observable) method.invoke(service, parts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (observable == null) {
            throw new IllegalArgumentException("observable can not be null");
        }
        if(isShowDialog){
            subscriber(observable, progressSubscriber);
        }else{
            subscriber(observable, subscriber);
        }
    }

    private Subscriber<T> subscriber = new Subscriber<T>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            LogUtil.e(TAG, "error=========>" + e.getMessage());
            if(e instanceof HttpException || e instanceof ConnectException){
                ToastUtil.showSingleToast("网络错误，请检查网络");
            }else{
                onFail(ERROR, e.getMessage(), null);
            }
        }

        @Override
        public void onNext(T t) {
//            MyLog.i(TAG, "message:=========>" + JsonUtils.toJson(t));
            handleResult(t);
        }
    };

    private ProgressSubscriber progressSubscriber = new ProgressSubscriber(new SubscriberOnNextListener<T>() {
        @Override
        public void onNext(T t) {
            handleResult(t);
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.e(TAG, "error=========>" + e.getMessage());
            if(e instanceof HttpException || e instanceof ConnectException){
                ToastUtil.showSingleToast("网络错误，请检查网络");
            }else{
                onFail(ERROR, e.getMessage(), null);
            }
        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onCancle() {

        }
    }, ActivityCollector.getTopActivity(), false);


    private synchronized static <T> void  subscriber(Observable<T> o, Subscriber<? super T> subscriber) {
        o.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void handleResult(T t) {
        if (t.getCode() == 0) {
            onSuccess((T) t);
        } else {
            onFail(t.getCode(), t.getMsg(), t);
        }
    }

    /**
     * 要取消本次请求，调用此方法
     */
    public void cancel() {
        subscriber.unsubscribe();
    }


}
