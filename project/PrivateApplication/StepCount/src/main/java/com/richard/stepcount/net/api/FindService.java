package com.richard.stepcount.net.api;

import com.richard.stepcount.entity.BaseEntity;
import com.richard.stepcount.entity.find.ArticleListEntity;
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

public interface FindService {
    /**
     *  文章上传(article/uploadArticle)
     */
    String UPLOAD_ARTICLE = "article/uploadArticle";
    @POST(UPLOAD_ARTICLE)
    Observable<BaseEntity> uploadArticle(@Body RequestBody requestBody);
    /**
     *  获取文章列表(article/getArticles)
     */
    String GET_ARTICLES = "article/getArticles";
    @POST(GET_ARTICLES)
    Observable<ArticleListEntity> getArticles(@Body RequestBody requestBody);

}
