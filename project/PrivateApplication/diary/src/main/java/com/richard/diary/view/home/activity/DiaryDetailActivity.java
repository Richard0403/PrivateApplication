package com.richard.diary.view.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.db.AppConstant;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryDetailEntity;
import com.richard.diary.view.base.BaseActivity;
import com.richard.diary.widget.richedit.RichTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class DiaryDetailActivity extends BaseActivity {
    @BindView(R.id.rtv_content)
    RichTextView rtv_content;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private long diaryId;

    @Override
    protected int getLayout() {
        return R.layout.activity_diary_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        diaryId = getIntent().getLongExtra(AppConstant.Extra.EXTRA_DIARY_ID, -1);
    }

    @Override
    protected void initData() {
        getDiaryDetail();
    }

    private void getDiaryDetail() {
        HttpRequest httpRequest = new HttpRequest<DiaryDetailEntity>() {
            @Override
            public String createJson() {
                Map map = new HashMap();
                map.put("id", diaryId);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(DiaryDetailEntity result) {
                super.onSuccess(result);
                tv_title.setText(result.getData().getTitle());
                rtv_content.setHtmlText(result.getData().getContent());
            }
        };
        httpRequest.start(HomeService.class, "queryDiaryDetail", true);
    }

    public static void start(Context context, long diaryId){
        Intent intent = new Intent(context, DiaryDetailActivity.class);
        intent.putExtra(AppConstant.Extra.EXTRA_DIARY_ID, diaryId);
        context.startActivity(intent);
    }
}
