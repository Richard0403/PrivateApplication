package com.richard.diary.widget.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richard.diary.R;
import com.richard.diary.common.cache.AppCache;
import com.richard.diary.common.utils.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * By Richard on 2018/4/25.
 */

public class DrawerSideView extends LinearLayout {
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.tv_name)
    TextView tv_name;




    public DrawerSideView(Context context) {
        this(context, null);
    }

    public DrawerSideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerSideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_drawer_side, this);
        ButterKnife.bind(this);

        initAttrs(context, attrs);
        initViews();
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
            ImageLoader.getInstance().displayCricleImage(getContext(), AppCache.getUserInfo().getUser().getHeader(), iv_pic);
        }
    }

    @OnClick({R.id.rl_person, R.id.rl_msg, R.id.rl_ground, R.id.rl_focus, R.id.rl_collect, R.id.rl_exit})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.rl_person:
                break;
            case R.id.rl_msg:
                break;
            case R.id.rl_ground:
                break;
            case R.id.rl_focus:
                break;
            case R.id.rl_collect:
                break;
            case R.id.rl_exit:
                break;
                default:
                    break;
        }
    }
}
