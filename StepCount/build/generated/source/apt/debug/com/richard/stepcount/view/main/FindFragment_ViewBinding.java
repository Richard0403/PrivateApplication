// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import com.richard.stepcount.widget.LoadMoreRecyclerView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FindFragment_ViewBinding<T extends FindFragment> implements Unbinder {
  protected T target;

  private View view2131165321;

  @UiThread
  public FindFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv_complete = Utils.findRequiredViewAsType(source, R.id.tv_complete, "field 'tv_complete'", TextView.class);
    target.v_status = Utils.findRequiredView(source, R.id.v_status, "field 'v_status'");
    target.clv_content = Utils.findRequiredViewAsType(source, R.id.clv_content, "field 'clv_content'", LoadMoreRecyclerView.class);
    target.pfl_refresh = Utils.findRequiredViewAsType(source, R.id.pfl_refresh, "field 'pfl_refresh'", PtrFrameLayout.class);
    view = Utils.findRequiredView(source, R.id.rl_complete, "method 'Onclick'");
    view2131165321 = view;
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

    target.tv_complete = null;
    target.v_status = null;
    target.clv_content = null;
    target.pfl_refresh = null;

    view2131165321.setOnClickListener(null);
    view2131165321 = null;

    this.target = null;
  }
}
