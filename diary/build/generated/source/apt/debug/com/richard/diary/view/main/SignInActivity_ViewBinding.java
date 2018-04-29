// Generated code from Butter Knife. Do not modify!
package com.richard.diary.view.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.diary.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignInActivity_ViewBinding<T extends SignInActivity> implements Unbinder {
  protected T target;

  private View view2131230854;

  @UiThread
  public SignInActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_login, "field 'iv_login' and method 'Onclick'");
    target.iv_login = Utils.castView(view, R.id.iv_login, "field 'iv_login'", ImageView.class);
    view2131230854 = view;
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

    target.iv_login = null;

    view2131230854.setOnClickListener(null);
    view2131230854 = null;

    this.target = null;
  }
}
