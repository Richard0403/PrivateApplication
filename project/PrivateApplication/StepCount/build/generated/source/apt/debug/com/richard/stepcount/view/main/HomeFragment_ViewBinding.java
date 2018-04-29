// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.main;

import android.content.Context;
import android.content.res.Resources;
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

public class HomeFragment_ViewBinding<T extends HomeFragment> implements Unbinder {
  protected T target;

  private View view2131165329;

  private View view2131165330;

  @UiThread
  public HomeFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv_step = Utils.findRequiredViewAsType(source, R.id.tv_step, "field 'tv_step'", TextView.class);
    target.tv_step_count = Utils.findRequiredViewAsType(source, R.id.tv_step_count, "field 'tv_step_count'", TextView.class);
    target.tv_step_trace = Utils.findRequiredViewAsType(source, R.id.tv_step_trace, "field 'tv_step_trace'", TextView.class);
    target.v_step_count = Utils.findRequiredView(source, R.id.v_step_count, "field 'v_step_count'");
    target.v_step_trace = Utils.findRequiredView(source, R.id.v_step_trace, "field 'v_step_trace'");
    view = Utils.findRequiredView(source, R.id.rl_step_count, "method 'Onclick'");
    view2131165329 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_step_trace, "method 'Onclick'");
    view2131165330 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });

    Context context = source.getContext();
    Resources res = context.getResources();
    Resources.Theme theme = context.getTheme();
    target.blue = Utils.getColor(res, theme, R.color.txt_blue);
    target.yellow = Utils.getColor(res, theme, R.color.accent);
    target.grey = Utils.getColor(res, theme, R.color.txt_middle_grey);
    target.transparent = Utils.getColor(res, theme, R.color.transparent);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_step = null;
    target.tv_step_count = null;
    target.tv_step_trace = null;
    target.v_step_count = null;
    target.v_step_trace = null;

    view2131165329.setOnClickListener(null);
    view2131165329 = null;
    view2131165330.setOnClickListener(null);
    view2131165330 = null;

    this.target = null;
  }
}
