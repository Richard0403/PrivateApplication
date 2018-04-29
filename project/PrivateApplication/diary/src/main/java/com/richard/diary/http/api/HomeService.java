package com.richard.diary.http.api;
import com.richard.diary.common.db.bean.DiaryBean;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.http.entity.user.UserEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * By Richard on 2017/12/26.
 */

public interface HomeService {
    @POST(ApiConfig.SING_IN)
    Observable<UserEntity> signIn(@Body RequestBody requestBody);
    @POST(ApiConfig.USER_DIARY_TAG)
    Observable<DiaryTagEntity> getUserTag(@Body RequestBody requestBody);
}
