package com.richard.diary.http.api;
import com.richard.diary.common.db.bean.DiaryBean;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryDetailEntity;
import com.richard.diary.http.entity.diary.DiaryListEntity;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.http.entity.diary.UploadEntity;
import com.richard.diary.http.entity.user.UserEntity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * By Richard on 2017/12/26.
 */

public interface HomeService {
    @POST(ApiConfig.SING_IN)
    Observable<UserEntity> signIn(@Body RequestBody requestBody);
    @POST(ApiConfig.USER_DIARY_TAG)
    Observable<DiaryTagEntity> getUserTag(@Body RequestBody requestBody);
    @POST(ApiConfig.QUERY_DIARY_BY_TAG)
    Observable<DiaryListEntity> queryDiaryByTag(@Body RequestBody requestBody);
    @POST(ApiConfig.DIARY_PRAISE)
    Observable<BaseEntity> praiseDiary(@Body RequestBody requestBody);
    @Multipart
    @POST(ApiConfig.UPLOAD_IMAGE)
    Observable<UploadEntity> uploadImage(@Part List<MultipartBody.Part> files);
    @POST(ApiConfig.SAVE_TAG)
    Observable<BaseEntity> addDiaryTag(@Body RequestBody requestBody);
    @POST(ApiConfig.SAVE_DIARY)
    Observable<BaseEntity> addDiary(@Body RequestBody requestBody);
    @POST(ApiConfig.QUERY_DIARY_DETAIL)
    Observable<DiaryDetailEntity> queryDiaryDetail(@Body RequestBody requestBody);


}
