// Generated code from Butter Knife. Do not modify!
package com.richard.diary.view.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.diary.R;
import com.richard.diary.widget.layout.DrawerMainView;
import com.richard.diary.widget.layout.DrawerSideView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MainActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.dm_view = Utils.findRequiredViewAsType(source, R.id.dm_view, "field 'dm_view'", DrawerMainView.class);
    target.ds_view = Utils.findRequiredViewAsType(source, R.id.ds_view, "field 'ds_view'", DrawerSideView.class);
    target.draw_layout = Utils.findRequiredViewAsType(source, R.id.draw_layout, "field 'draw_layout'", DrawerLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.dm_view = null;
    target.ds_view = null;
    target.draw_layout = null;

    this.target = null;
  }
}
