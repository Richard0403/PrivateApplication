package com.richard.stepcount.net.request;

import android.content.Context;

import com.google.gson.Gson;
import com.richard.stepcount.constants.AppConstant;
import com.richard.stepcount.entity.BaseEntity;
import com.richard.stepcount.entity.home.UploadFileEntity;
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
public class HomeRequest extends BaseRequest{

    public static void login(String qqOpenId, String icon, String name, Context context,final BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("qqOpenId",qqOpenId);
        map.put("header",icon);
        map.put("name",name);
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getHomePageService().login(requestBody);
        toLoadingSubscribe(observable, baseView,context);
    }

    public static void uploadStep(int steps, BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        String stepString;
        try {
            stepString = DesUtil.encode(String.valueOf(steps));
        } catch (Exception e) {
            LogUtil.setLog("uploadStep", "step encode failed");
            return;
        }
        map.put("stepCount",stepString);
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getHomePageService().commitStep(requestBody);
        toSubscribe(observable, baseView);
    }
    //上传图片
    public static void uploadImage(List<String> files,Context context, BaseView baseView) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (String path : files) {
            File file = new File(path);
            RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_IMG), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        Observable observable = RetroManager.getInstance().getHomePageService().uploadImage(parts);
        toLoadingSubscribe(observable, baseView,context);
    }
}
