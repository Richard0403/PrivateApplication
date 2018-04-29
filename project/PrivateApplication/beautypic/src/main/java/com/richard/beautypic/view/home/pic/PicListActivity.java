package com.richard.beautypic.view.home.pic;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.GestureTransitions;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.tracker.SimpleTracker;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.beautypic.R;
import com.richard.beautypic.base.BaseActivity;
import com.richard.beautypic.entity.PictureEntity;
import com.richard.beautypic.net.request.BaseView;
import com.richard.beautypic.net.request.HomeRequest;
import com.richard.beautypic.utils.ImageLoader;
import com.richard.beautypic.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class PicListActivity extends BaseActivity {

    @BindView(R.id.tv_title_left)
    TextView tv_title_left;
    @BindView(R.id.clv_content)
    RecyclerView clv_content;

    @BindView(R.id.transition_pager)
    ViewPager transition_pager;
    @BindView(R.id.transition_full_background)
    View background;


    private PaintingsPagerAdapter pagerAdapter;
    private ViewsTransitionAnimator<Integer> animator;



    private int pageNo = 0,pageSize = 40;
    private List<PictureEntity.DataBean.PicturesBean> pictures = new ArrayList<>();
    private PicAdapter picAdapter;
    private GridLayoutManager layoutManager;



    @Override
    protected int getLayout() {
        return R.layout.activity_pic_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initList();
    }
    @Override
    protected void initData() {
        queryPictures();
    }

    private void queryPictures() {
        HomeRequest.queryPictures(pageNo, pageSize, new BaseView<PictureEntity>() {
            @Override
            public void onSuccess(PictureEntity result) {
                if(result.getCode() == 0){
                    List<PictureEntity.DataBean.PicturesBean> temp = result.getData().getPictures();
                    int pageCount = result.getData().getPageCount();
                    if(temp.size()>0){
                        if(pageNo == 0){
                            pictures.clear();
                        }
                        pictures.addAll(temp);
                        picAdapter.notifyDataSetChanged();
                        pagerAdapter.notifyDataSetChanged();
//                        pageNo++;
                        pageNo = RandomUtil.getRandom(0,pageCount);
                    }
                }
                picAdapter.loadMoreComplete();

            }

            @Override
            public void onFailure(Throwable e) {
                picAdapter.loadMoreFail();
            }

        });

    }


    private void initList() {
        tv_title_left.setText("美图");
        layoutManager = new GridLayoutManager(this,4);
        clv_content.setLayoutManager(layoutManager);
        picAdapter = new PicAdapter(R.layout.item_picture_list, pictures);
        clv_content.setAdapter(picAdapter);
        picAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                queryPictures();
            }
        }, clv_content);
        picAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                animator.enter(position, true);
            }
        });



        //listener --imageView的setting(SettingsMenu.java)
        pagerAdapter = new PaintingsPagerAdapter(transition_pager, pictures, null);
        transition_pager.setAdapter(pagerAdapter);
        transition_pager.setPageMargin(20);

        // Initializing images animator
        final SimpleTracker listTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                int first = layoutManager.findFirstVisibleItemPosition();
                int last = layoutManager.findLastVisibleItemPosition();
                if (position < first || position > last) {
                    return null;
                } else {
                    View itemView = clv_content.getChildAt(position - first);
                    return picAdapter.getImage(itemView);
                }
            }
        };

        final SimpleTracker pagerTracker = new SimpleTracker() {
            @Override
            public View getViewAt(int position) {
                RecyclePagerAdapter.ViewHolder holder = pagerAdapter.getViewHolder(position);
                return holder == null ? null : PaintingsPagerAdapter.getImage(holder);
            }
        };

        animator = GestureTransitions.from(clv_content, listTracker).into(transition_pager, pagerTracker);

        // Setting up background animation during image animation
        animator.addPositionUpdateListener(new ViewPositionAnimator.PositionUpdateListener() {
            @Override
            public void onPositionUpdate(float position, boolean isLeaving) {
                background.setVisibility(position == 0f ? View.INVISIBLE : View.VISIBLE);
                background.getBackground().setAlpha((int) (255 * position));
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.onBackPressed();
        }
    }

    private class PicAdapter extends BaseQuickAdapter<PictureEntity.DataBean.PicturesBean,BaseViewHolder> {
        private List<PictureEntity.DataBean.PicturesBean> data;

        public PicAdapter(int layoutResId, List<PictureEntity.DataBean.PicturesBean> data) {
            super(layoutResId, data);
            this.data = data;
        }

        private ImageView getImage(View itemView) {
            ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
            return imageView == null ? null:imageView;
        }

        @Override
        protected void convert(BaseViewHolder helper, PictureEntity.DataBean.PicturesBean item) {
            ImageLoader.getInstance().displaySmallImage(item.getUrl(), (ImageView) helper.getView(R.id.iv_pic));
        }
    }
}
