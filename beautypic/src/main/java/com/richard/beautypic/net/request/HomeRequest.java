package com.richard.beautypic.net.request;

import android.content.Context;
import com.google.gson.Gson;
import com.richard.beautypic.constant.AppConstant;
import com.richard.beautypic.entity.BaseEntity;
import com.richard.beautypic.entity.PictureEntity;
import com.richard.beautypic.net.RetroManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import java.util.HashMap;
import java.util.Map;

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

    public static void queryPictures(int pageNo, int pageSize,final BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo",pageNo+"");
        map.put("pageSize",pageSize+"");
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getHomePageService().getOldPic(requestBody);
        toSubscribe(observable, baseView);
    }

    public static void getVersion(final BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getHomePageService().getVersion(requestBody);
        toSubscribe(observable, baseView);
    }


    public static void queryNovels(int pageNo, int pageSize, BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo",pageNo+"");
        map.put("pageSize",pageSize+"");
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getHomePageService().getNovel(requestBody);
        toSubscribe(observable, baseView);
    }

    public static void queryNovelDetail(String id , Context context, final BaseView baseView) {
        Map<String, String> map = new HashMap<>();
        map.put("id",id);
        String strs = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConstant.MEDIATYPE_FORMAT_JSON), strs);
        Observable observable = RetroManager.getInstance().getHomePageService().getNovelDetail(requestBody);
        toLoadingSubscribe(observable, baseView, context);
    }
}
