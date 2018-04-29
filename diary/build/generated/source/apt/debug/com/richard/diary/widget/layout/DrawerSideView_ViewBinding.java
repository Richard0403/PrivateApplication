// Generated code from Butter Knife. Do not modify!
package com.richard.diary.widget.layout;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.diary.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DrawerSideView_ViewBinding<T extends DrawerSideView> implements Unbinder {
  protected T target;

  private View view2131230918;

  private View view2131230917;

  private View view2131230915;

  private View view2131230914;

  private View view2131230911;

  private View view2131230913;

  @UiThread
  public DrawerSideView_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.iv_pic = Utils.findRequiredViewAsType(source, R.id.iv_pic, "field 'iv_pic'", ImageView.class);
    target.tv_name = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tv_name'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_person, "method 'Onclick'");
    view2131230918 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_msg, "method 'Onclick'");
    view2131230917 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_ground, "method 'Onclick'");
    view2131230915 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_focus, "method 'Onclick'");
    view2131230914 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_collect, "method 'Onclick'");
    view2131230911 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_exit, "method 'Onclick'");
    view2131230913 = view;
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

    target.iv_pic = null;
    target.tv_name = null;

    view2131230918.setOnClickListener(null);
    view2131230918 = null;
    view2131230917.setOnClickListener(null);
    view2131230917 = null;
    view2131230915.setOnClickListener(null);
    view2131230915 = null;
    view2131230914.setOnClickListener(null);
    view2131230914 = null;
    view2131230911.setOnClickListener(null);
    view2131230911 = null;
    view2131230913.setOnClickListener(null);
    view2131230913 = null;

    this.target = null;
  }
}
