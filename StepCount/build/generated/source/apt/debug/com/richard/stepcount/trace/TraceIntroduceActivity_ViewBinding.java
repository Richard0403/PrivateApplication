// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.trace;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TraceIntroduceActivity_ViewBinding<T extends TraceIntroduceActivity> implements Unbinder {
  protected T target;

  private View view2131165395;

  private View view2131165320;

  @UiThread
  public TraceIntroduceActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.tv_title_left, "field 'tv_title'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_stop, "field 'tv_stop' and method 'Onclick'");
    target.tv_stop = Utils.castView(view, R.id.tv_stop, "field 'tv_stop'", TextView.class);
    view2131165395 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_back, "method 'Onclick'");
    view2131165320 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_title = null;
    target.tv_stop = null;

    view2131165395.setOnClickListener(null);
    view2131165395 = null;
    view2131165320.setOnClickListener(null);
    view2131165320 = null;

    this.target = null;
  }
}
