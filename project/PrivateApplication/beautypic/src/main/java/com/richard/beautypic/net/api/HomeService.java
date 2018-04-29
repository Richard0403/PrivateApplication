package com.richard.beautypic.net.api;

import com.richard.beautypic.entity.*;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface HomeService {
    /**
     *  登录(users/login")
     */
    String USER_LOGIN = "users/login";
    @POST(USER_LOGIN)
    Observable<UserEntity> login(@Body RequestBody requestBody);

    /**
     * 查询图片(pic/getOldPic)
     */
    String QUERY_PIC = "pic/getOldPic";
    @POST(QUERY_PIC)
    Observable<PictureEntity> getOldPic(@Body RequestBody requestBody);

    /**
     * 获取版本(version/getVersion)
     */
    String QUERY_VERSION = "version/getVersion";
    @POST(QUERY_VERSION)
    Observable<VersionEntity> getVersion(@Body RequestBody requestBody);

    /**
     * 查询小说(pic/getNovel)
     */
    String QUERY_NOVEL = "novel/getNovel";
    @POST(QUERY_NOVEL)
    Observable<NovelListEntity> getNovel(@Body RequestBody requestBody);

    /**
     * 查询小说详情(pic/getNovelDetail)
     */
    String QUERY_NOVEL_DETAIL = "novel/getNovelDetail";
    @POST(QUERY_NOVEL_DETAIL)
    Observable<NovelDetailEntity> getNovelDetail(@Body RequestBody requestBody);

}
