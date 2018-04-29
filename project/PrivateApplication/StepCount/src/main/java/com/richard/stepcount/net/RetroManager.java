package com.richard.stepcount.net;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.richard.stepcount.base.App;
import com.richard.stepcount.constants.AppConstant;
import com.richard.stepcount.db.Config;
import com.richard.stepcount.entity.BaseEntity;
import com.richard.stepcount.net.api.FindService;
import com.richard.stepcount.net.api.HomeService;
import com.richard.stepcount.utils.LogUtil;
import com.richard.stepcount.utils.ToastUtil;
import com.richard.stepcount.view.home.activity.LoginActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 45820 on 2016/11/5.
 */

public class RetroManager {
    private static final String TAG = "RetroManager";
    private static volatile RetroManager instance = null;

    private Retrofit retrofit;
    private Gson gson;
    private OkHttpClient client;
    private static final int DEFAULT_READ_TIMEOUT = 60 * 1000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 60 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 60 * 1000;

    private RetroManager() {
        initGson();
        initOkhttpClient();
        initRetrofit();
    }

    public static RetroManager getInstance() {
        if (instance == null) {
            synchronized (RetroManager.class) {
                if (instance == null) {
                    instance = new RetroManager();
                }
            }
        }
        return instance;
    }

    private void initRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    private void initOkhttpClient() {
        if (client == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    try {
                        BaseEntity baseEntity = new Gson().fromJson(message, BaseEntity.class);
                        if(baseEntity.getCode()==401){
                            onExit("授权失败，请重新登录");
                        }else if (baseEntity.getCode() == 402){
                            onExit("您的账号在其他设备登录，请重新登录");
                        }
                    }catch (Exception e){

                    }
                    LogUtil.setLog("aaa", "message====" + message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(new RequestInterceptor())
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new NetworkInterceptor())
                    .build();
        }
    }

    public class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            String userIdHeader = "";
            String token = "";
            if(App.getLoginUser() != null){
                 token = App.getLoginUser().getToken();
            }

            //TODO 添加头部
//            String channel = "";
//            if (App.getUserEntity() != null) {
//                header = "bearerHeader " + App.getUserEntity().getToken();
//                userIdHeader = App.getUserEntity().getId()+"";
//                channel = App.getUserEntity().getChannel();
//            }
            Request request = null;

            try {
                request = original.newBuilder()
                        .addHeader("authorization", token)
                        .addHeader("userId", userIdHeader)
//                        .addHeader("sign", DesUtils.encode(encodeContent))
//                        .addHeader("version", mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),0).versionName)
    //                    .addHeader("channel", channel)
                        .method(original.method(), original.body())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            LogUtil.setLog("RequestInterceptor",String.format("发送请求 %s on %s%n%s",
//                    request.url(), request.body(), request.toString()));
            LogUtil.setLog("RequestInterceptor",String.format("发送请求 %s ",
                    request.body().toString() ));
            return chain.proceed(request);
        }
    }

    public class NetworkInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if(response.code() == 401){
               //TODO 401处理
                onExit("授权失败，请重新登录");
                return null;
            }
            return response;
        }
    }
    /**
     * 获取缓存
     */
//    Interceptor baseInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            if (!NetworkUtil.IsNetWorkAvailable(context)) {
//                /**
//                 * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
//                 */
//                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
//                CacheControl tempCacheControl = new CacheControl.Builder()
//                        .onlyIfCached()
//                        .maxStale(maxStale, TimeUnit.SECONDS)
//                        .build();
//                request = request.newBuilder()
//                        .cacheControl(tempCacheControl)
//                        .build();
//                LogUtil.setLog(TAG, "intercept:no network ");
//            }
//            return chain.proceed(request);
//        }
//    };
//    //只有 网络拦截器环节 才会写入缓存写入缓存,在有网络的时候 设置缓存时间
//    Interceptor rewriteCacheControlInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            Response originalResponse = chain.proceed(request);
//            int maxAge = 1 * 60; // 在线缓存在1分钟内可读取 单位:秒
//            return originalResponse.newBuilder()
//                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .build();
//        }
//    };

    private void initGson() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

    private void onExit(String tip){
        Config.getConfig(App.getInstance()).saveUserInfo(null);
        Intent intent = new Intent(App.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        App.getInstance().startActivity(intent);
        ToastUtil.showSingleToast(tip);
    }

    public HomeService getHomePageService(){
        return retrofit.create(HomeService.class);
    }
    public FindService getFindService(){
        return retrofit.create(FindService.class);
    }
}
