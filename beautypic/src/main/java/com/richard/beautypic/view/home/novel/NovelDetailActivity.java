package com.richard.beautypic.view.home.novel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.richard.beautypic.R;
import com.richard.beautypic.base.BaseActivity;
import com.richard.beautypic.entity.NovelDetailEntity;
import com.richard.beautypic.net.request.BaseView;
import com.richard.beautypic.net.request.HomeRequest;
import com.richard.beautypic.utils.ToastUtil;

public class NovelDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title_left)
    TextView tv_title_left;

    @BindView(R.id.tv_content)
    TextView tv_content;


    private String novelId;

    @Override
    protected int getLayout() {
        return R.layout.activity_novel_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        novelId = getIntent().getStringExtra("id");
        getNovelDetail();
    }

    private void getNovelDetail(){
        HomeRequest.queryNovelDetail(novelId, this,new BaseView<NovelDetailEntity>() {
            @Override
            public void onSuccess(NovelDetailEntity result) {

                if(result.getCode() == 0){
                    tv_title_left.setText(Html.fromHtml(result.getData().getTitle()));
                    tv_content.setText(Html.fromHtml(result.getData().getContent()));
                }else {
                    ToastUtil.showSingleToast(result.getMsg());
                }

            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    @OnClick(R.id.rl_back)
    protected void Onclick(){
        finish();
    }
}
