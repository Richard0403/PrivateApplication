// Generated code from Butter Knife. Do not modify!
package com.richard.beautypic.view.home.pic;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.beautypic.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PicListActivity_ViewBinding<T extends PicListActivity> implements Unbinder {
  protected T target;

  @UiThread
  public PicListActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.tv_title_left = Utils.findRequiredViewAsType(source, R.id.tv_title_left, "field 'tv_title_left'", TextView.class);
    target.clv_content = Utils.findRequiredViewAsType(source, R.id.clv_content, "field 'clv_content'", RecyclerView.class);
    target.transition_pager = Utils.findRequiredViewAsType(source, R.id.transition_pager, "field 'transition_pager'", ViewPager.class);
    target.background = Utils.findRequiredView(source, R.id.transition_full_background, "field 'background'");
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_title_left = null;
    target.clv_content = null;
    target.transition_pager = null;
    target.background = null;

    this.target = null;
  }
}
