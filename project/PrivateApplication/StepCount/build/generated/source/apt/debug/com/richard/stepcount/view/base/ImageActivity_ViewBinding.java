// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageActivity_ViewBinding<T extends ImageActivity> implements Unbinder {
  protected T target;

  @UiThread
  public ImageActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.vpImg = Utils.findRequiredViewAsType(source, R.id.vp_img, "field 'vpImg'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.vpImg = null;

    this.target = null;
  }
}
