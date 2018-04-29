// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ConfirmDialog_ViewBinding<T extends ConfirmDialog> implements Unbinder {
  protected T target;

  @UiThread
  public ConfirmDialog_ViewBinding(T target, View source) {
    this.target = target;

    target.tv_content = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tv_content'", TextView.class);
    target.tv_cancle = Utils.findRequiredViewAsType(source, R.id.tv_cancle, "field 'tv_cancle'", TextView.class);
    target.tv_confirm = Utils.findRequiredViewAsType(source, R.id.tv_confirm, "field 'tv_confirm'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_content = null;
    target.tv_cancle = null;
    target.tv_confirm = null;

    this.target = null;
  }
}
