// Generated code from Butter Knife. Do not modify!
package com.richard.diary.view.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.diary.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GuideActivity_ViewBinding<T extends GuideActivity> implements Unbinder {
  protected T target;

  @UiThread
  public GuideActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'mViewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mViewPager = null;

    this.target = null;
  }
}
