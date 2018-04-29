// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.main;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  private View view2131165324;

  private View view2131165326;

  private View view2131165322;

  private View view2131165325;

  @UiThread
  public MainActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.iv_home = Utils.findRequiredViewAsType(source, R.id.iv_home, "field 'iv_home'", ImageView.class);
    target.iv_rank = Utils.findRequiredViewAsType(source, R.id.iv_rank, "field 'iv_rank'", ImageView.class);
    target.iv_discover = Utils.findRequiredViewAsType(source, R.id.iv_discover, "field 'iv_discover'", ImageView.class);
    target.iv_person = Utils.findRequiredViewAsType(source, R.id.iv_person, "field 'iv_person'", ImageView.class);
    target.tv_home = Utils.findRequiredViewAsType(source, R.id.tv_home, "field 'tv_home'", TextView.class);
    target.tv_rank = Utils.findRequiredViewAsType(source, R.id.tv_rank, "field 'tv_rank'", TextView.class);
    target.tv_discover = Utils.findRequiredViewAsType(source, R.id.tv_discover, "field 'tv_discover'", TextView.class);
    target.tv_person = Utils.findRequiredViewAsType(source, R.id.tv_person, "field 'tv_person'", TextView.class);
    view = Utils.findRequiredView(source, R.id.rl_home, "method 'Onclick'");
    view2131165324 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_rank, "method 'Onclick'");
    view2131165326 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_discover, "method 'Onclick'");
    view2131165322 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_person, "method 'Onclick'");
    view2131165325 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });

    Context context = source.getContext();
    Resources res = context.getResources();
    Resources.Theme theme = context.getTheme();
    target.yellow = Utils.getColor(res, theme, R.color.color_title_yellow);
    target.grey = Utils.getColor(res, theme, R.color.txt_light_grey);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.iv_home = null;
    target.iv_rank = null;
    target.iv_discover = null;
    target.iv_person = null;
    target.tv_home = null;
    target.tv_rank = null;
    target.tv_discover = null;
    target.tv_person = null;

    view2131165324.setOnClickListener(null);
    view2131165324 = null;
    view2131165326.setOnClickListener(null);
    view2131165326 = null;
    view2131165322.setOnClickListener(null);
    view2131165322 = null;
    view2131165325.setOnClickListener(null);
    view2131165325 = null;

    this.target = null;
  }
}
