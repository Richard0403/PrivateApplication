// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.home.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepTraceFragment_ViewBinding<T extends StepTraceFragment> implements Unbinder {
  protected T target;

  @UiThread
  public StepTraceFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.clv_step_hostory, "field 'recyclerView'", RecyclerView.class);
    target.refresh = Utils.findRequiredViewAsType(source, R.id.refresh, "field 'refresh'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.recyclerView = null;
    target.refresh = null;

    this.target = null;
  }
}
