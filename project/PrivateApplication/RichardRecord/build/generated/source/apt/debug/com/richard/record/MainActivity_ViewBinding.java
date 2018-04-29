// Generated code from Butter Knife. Do not modify!
package com.richard.record;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  private View view2131165214;

  private View view2131165217;

  private View view2131165215;

  private View view2131165213;

  private View view2131165265;

  @UiThread
  public MainActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_main_home, "field 'btn_main_home' and method 'OnTabclick'");
    target.btn_main_home = Utils.castView(view, R.id.btn_main_home, "field 'btn_main_home'", RelativeLayout.class);
    view2131165214 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnTabclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_main_video, "field 'btn_main_video' and method 'OnTabclick'");
    target.btn_main_video = Utils.castView(view, R.id.btn_main_video, "field 'btn_main_video'", RelativeLayout.class);
    view2131165217 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnTabclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_main_person, "field 'btn_main_person' and method 'OnTabclick'");
    target.btn_main_person = Utils.castView(view, R.id.btn_main_person, "field 'btn_main_person'", RelativeLayout.class);
    view2131165215 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnTabclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_main_community, "field 'btn_main_community' and method 'OnTabclick'");
    target.btn_main_community = Utils.castView(view, R.id.btn_main_community, "field 'btn_main_community'", RelativeLayout.class);
    view2131165213 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnTabclick(p0);
      }
    });
    target.tvMainHome = Utils.findRequiredViewAsType(source, R.id.tv_main_home, "field 'tvMainHome'", TextView.class);
    target.tv_main_video = Utils.findRequiredViewAsType(source, R.id.tv_main_video, "field 'tv_main_video'", TextView.class);
    target.tv_main_person = Utils.findRequiredViewAsType(source, R.id.tv_main_person, "field 'tv_main_person'", TextView.class);
    target.tv_main_community = Utils.findRequiredViewAsType(source, R.id.tv_main_community, "field 'tv_main_community'", TextView.class);
    target.iv_main_home = Utils.findRequiredViewAsType(source, R.id.iv_main_home, "field 'iv_main_home'", ImageView.class);
    target.iv_main_video = Utils.findRequiredViewAsType(source, R.id.iv_main_video, "field 'iv_main_video'", ImageView.class);
    target.iv_main_person = Utils.findRequiredViewAsType(source, R.id.iv_main_person, "field 'iv_main_person'", ImageView.class);
    target.iv_main_community = Utils.findRequiredViewAsType(source, R.id.iv_main_community, "field 'iv_main_community'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.iv_main_recorder_big, "field 'iv_main_recorder_big' and method 'OnTabclick'");
    target.iv_main_recorder_big = Utils.castView(view, R.id.iv_main_recorder_big, "field 'iv_main_recorder_big'", ImageView.class);
    view2131165265 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnTabclick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btn_main_home = null;
    target.btn_main_video = null;
    target.btn_main_person = null;
    target.btn_main_community = null;
    target.tvMainHome = null;
    target.tv_main_video = null;
    target.tv_main_person = null;
    target.tv_main_community = null;
    target.iv_main_home = null;
    target.iv_main_video = null;
    target.iv_main_person = null;
    target.iv_main_community = null;
    target.iv_main_recorder_big = null;

    view2131165214.setOnClickListener(null);
    view2131165214 = null;
    view2131165217.setOnClickListener(null);
    view2131165217 = null;
    view2131165215.setOnClickListener(null);
    view2131165215 = null;
    view2131165213.setOnClickListener(null);
    view2131165213 = null;
    view2131165265.setOnClickListener(null);
    view2131165265 = null;

    this.target = null;
  }
}
