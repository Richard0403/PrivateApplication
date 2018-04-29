package com.richard.stepcount.net.api;

import com.richard.stepcount.entity.BaseEntity;
import com.richard.stepcount.entity.home.UploadFileEntity;
import com.richard.stepcount.entity.home.UserEntity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface HomeService {
    /**
     *  登录("auth/register")
     */
    String USER_LOGIN = "auth/register";
    @POST(USER_LOGIN)
    Observable<UserEntity> login(@Body RequestBody requestBody);
    /**
     *  提交步数("step/commitStep")
     */
    String COMMIT_STEP = "step/commitStep";
    @POST(COMMIT_STEP)
    Observable<BaseEntity> commitStep(@Body RequestBody requestBody);
    /**
     *  上传图片("upload/uploadImage")
     */
    String UPLOAD_IMAGE = "upload/uploadImage";
    @Multipart
    @POST(UPLOAD_IMAGE)
    Observable<UploadFileEntity> uploadImage(@Part List<MultipartBody.Part> files);

}
