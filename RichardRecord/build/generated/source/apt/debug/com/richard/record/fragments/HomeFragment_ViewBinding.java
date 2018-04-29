// Generated code from Butter Knife. Do not modify!
package com.richard.record.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.amap.api.maps.TextureMapView;
import com.richard.record.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding<T extends HomeFragment> implements Unbinder {
  protected T target;

  private View view2131165268;

  @UiThread
  public HomeFragment_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.gd_map = Utils.findRequiredViewAsType(source, R.id.gd_map, "field 'gd_map'", TextureMapView.class);
    view = Utils.findRequiredView(source, R.id.iv_navigation, "field 'iv_navigation' and method 'onclick'");
    target.iv_navigation = Utils.castView(view, R.id.iv_navigation, "field 'iv_navigation'", ImageView.class);
    view2131165268 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onclick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.gd_map = null;
    target.iv_navigation = null;

    view2131165268.setOnClickListener(null);
    view2131165268 = null;

    this.target = null;
  }
}
