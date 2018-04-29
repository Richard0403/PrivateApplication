// Generated code from Butter Knife. Do not modify!
package com.richard.diary.widget.layout;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.diary.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DrawerMainView_ViewBinding<T extends DrawerMainView> implements Unbinder {
  protected T target;

  private View view2131230853;

  private View view2131230849;

  @UiThread
  public DrawerMainView_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_header, "field 'iv_header' and method 'Onclick'");
    target.iv_header = Utils.castView(view, R.id.iv_header, "field 'iv_header'", ImageView.class);
    view2131230853 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_add, "field 'iv_add' and method 'Onclick'");
    target.iv_add = Utils.castView(view, R.id.iv_add, "field 'iv_add'", ImageView.class);
    view2131230849 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    target.clv_content = Utils.findRequiredViewAsType(source, R.id.clv_content, "field 'clv_content'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.iv_header = null;
    target.iv_add = null;
    target.clv_content = null;

    view2131230853.setOnClickListener(null);
    view2131230853 = null;
    view2131230849.setOnClickListener(null);
    view2131230849 = null;

    this.target = null;
  }
}
