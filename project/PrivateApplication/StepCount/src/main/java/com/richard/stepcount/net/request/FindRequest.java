package com.richard.stepcount.net.request;

import android.content.Context;

import com.google.gson.Gson;
import com.richard.stepcount.constants.AppConstant;
import com.richard.stepcount.net.RetroManager;
import com.richard.stepcount.utils.DesUtil;
import com.richard.stepcount.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * by Richard on 2017/9/4
 * desc:
 */
public class FindRequest extends BaseRequest{

    public static void uploadArticle(String title, String content, Context context, BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("title",title);
        map.put("content",content);
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getFindService().uploadArticle(requestBody);
        toLoadingSubscribe(observable, baseView,context);
    }

    public static void getArticles(int pageNo, int pageSize,Context context, BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo",String.valueOf(pageNo));
        map.put("pageSize",String.valueOf(pageSize));
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getFindService().getArticles(requestBody);
        toLoadingSubscribe(observable, baseView,context);
    }
}
