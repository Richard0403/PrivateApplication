// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.home.fragment;

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
import com.richard.stepcount.widget.InstrumentView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepCountFragment_ViewBinding<T extends StepCountFragment> implements Unbinder {
  protected T target;

  private View view2131165399;

  @UiThread
  public StepCountFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv_step_count = Utils.findRequiredViewAsType(source, R.id.tv_step_count, "field 'tv_step_count'", TextView.class);
    target.itv_panel_counter = Utils.findRequiredViewAsType(source, R.id.itv_panel_counter, "field 'itv_panel_counter'", InstrumentView.class);
    view = Utils.findRequiredView(source, R.id.tv_trace_status, "field 'tv_trace_status' and method 'Onclick'");
    target.tv_trace_status = Utils.castView(view, R.id.tv_trace_status, "field 'tv_trace_status'", TextView.class);
    view2131165399 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    target.tv_count_tip = Utils.findRequiredViewAsType(source, R.id.tv_count_tip, "field 'tv_count_tip'", TextView.class);

    Context context = source.getContext();
    Resources res = context.getResources();
    Resources.Theme theme = context.getTheme();
    target.txt_light_grey = Utils.getColor(res, theme, R.color.txt_light_grey);
    target.accent = Utils.getColor(res, theme, R.color.accent);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_step_count = null;
    target.itv_panel_counter = null;
    target.tv_trace_status = null;
    target.tv_count_tip = null;

    view2131165399.setOnClickListener(null);
    view2131165399 = null;

    this.target = null;
  }
}
