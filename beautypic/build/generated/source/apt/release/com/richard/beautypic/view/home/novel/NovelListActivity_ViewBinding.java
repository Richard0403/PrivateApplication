// Generated code from Butter Knife. Do not modify!
package com.richard.beautypic.view.home.novel;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.beautypic.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NovelListActivity_ViewBinding<T extends NovelListActivity> implements Unbinder {
  protected T target;

  @UiThread
  public NovelListActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.tv_title_left = Utils.findRequiredViewAsType(source, R.id.tv_title_left, "field 'tv_title_left'", TextView.class);
    target.clv_content = Utils.findRequiredViewAsType(source, R.id.clv_content, "field 'clv_content'", RecyclerView.class);
    target.sp_refresh = Utils.findRequiredViewAsType(source, R.id.sp_refresh, "field 'sp_refresh'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_title_left = null;
    target.clv_content = null;
    target.sp_refresh = null;

    this.target = null;
  }
}
