package com.richard.diary.widget.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.richard.diary.R;
import com.richard.diary.common.cache.AppCache;
import com.richard.diary.common.utils.ImageLoader;
import com.richard.diary.common.utils.ToastUtil;
import com.richard.diary.http.HttpRequest;
import com.richard.diary.http.api.HomeService;
import com.richard.diary.http.entity.BaseEntity;
import com.richard.diary.http.entity.diary.DiaryTagEntity;
import com.richard.diary.http.entity.user.UserEntity;
import com.richard.diary.http.entity.user.UserInfo;
import com.richard.diary.interfaces.DrawerMainEventListener;
import com.richard.diary.view.home.DiaryTagAdapter;
import com.richard.diary.view.main.MainActivity;
import com.richard.diary.view.main.SignInActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * By Richard on 2018/4/25.
 */

public class DrawerMainView extends LinearLayout {
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.clv_content)
    RecyclerView clv_content;

    private DiaryTagAdapter tagAdapter;
    private List<DiaryTagEntity.DataBean> diaryTags = new ArrayList<>();

    private int pageNo = 0;
    private int pageSize = 20;


    private DrawerMainEventListener drawerMainEvent;

    public DrawerMainView(Context context) {
        this(context, null);
    }

    public DrawerMainView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerMainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_drawer_main, this);
        ButterKnife.bind(this);
        initAttrs(context, attrs);
        initViews();
        initData();
    }


    private void initAttrs(Context context, AttributeSet attrs) {
//        TypedArray ta;
//        if (attrs == null) {
//            ta = context.obtainStyledAttributes(R.styleable.ChallengeInfoCard);
//        } else {
//            ta = context.obtainStyledAttributes(attrs, R.styleable.ChallengeInfoCard);
//        }
//        isReadComplete = ta.getBoolean(R.styleable.ChallengeInfoCard_is_read_complete, false);
//        isGetReward = ta.getBoolean(R.styleable.ChallengeInfoCard_is_get_reward, false);
//        isChallengeStart = ta.getBoolean(R.styleable.ChallengeInfoCard_is_challenge_start, true);
//        ta.recycle();
    }


    private void initViews() {
        if(AppCache.getUserInfo()!=null){
            ImageLoader.getInstance().displayCricleImage(getContext(), AppCache.getUserInfo().getUser().getHeader(), iv_header);
        }
        tagAdapter = new DiaryTagAdapter(diaryTags);
        clv_content.setLayoutManager(new GridLayoutManager(getContext(),2));
        clv_content.setAdapter(tagAdapter);
        tagAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getUserTag();
            }
        }, clv_content);
    }

    private void initData() {
        getUserTag();
    }

    @OnClick({R.id.iv_header, R.id.iv_add})
    protected void Onclick(View view){
        if(drawerMainEvent == null){
            ToastUtil.showLongToast("drawerMainClick can't be null");
            return;
        }
        switch (view.getId()){
            case R.id.iv_header:
                drawerMainEvent.onDrawerClick();
                break;
            case R.id.iv_add:
                drawerMainEvent.onAddClick();
                break;
                default:
                    break;
        }
    }

    public void setEventListener(DrawerMainEventListener eventListener){
        this.drawerMainEvent = eventListener;
    }

    private void getUserTag() {
        HttpRequest httpRequest = new HttpRequest<DiaryTagEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put("pageNo", pageNo);
                map.put("pageSize", pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(DiaryTagEntity result) {
                super.onSuccess(result);
                if(pageNo == 1){
                    diaryTags.clear();
                }
                tagAdapter.loadMoreComplete();
                tagAdapter.setEnableLoadMore(result.getData().size()>=pageSize);
                diaryTags.addAll(result.getData());
                if(result.getData().size() >= pageSize){
                    pageNo++;
                }
                tagAdapter.notifyDataSetChanged();
            }
        };
        httpRequest.start(HomeService.class, "getUserTag");
    }
}
