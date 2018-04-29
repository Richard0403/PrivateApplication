// Generated code from Butter Knife. Do not modify!
package com.richard.beautypic.view.home.novel;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.beautypic.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NovelDetailActivity_ViewBinding<T extends NovelDetailActivity> implements Unbinder {
  protected T target;

  private View view2131165272;

  @UiThread
  public NovelDetailActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv_title_left = Utils.findRequiredViewAsType(source, R.id.tv_title_left, "field 'tv_title_left'", TextView.class);
    target.tv_content = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tv_content'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_back, "method 'Onclick'");
    view2131165272 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_title_left = null;
    target.tv_content = null;

    view2131165272.setOnClickListener(null);
    view2131165272 = null;

    this.target = null;
  }
}
