// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.base;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageAdapter$AttentionImgVH_ViewBinding<T extends ImageAdapter.AttentionImgVH> implements Unbinder {
  protected T target;

  @UiThread
  public ImageAdapter$AttentionImgVH_ViewBinding(T target, View source) {
    this.target = target;

    target.iv_img = Utils.findRequiredViewAsType(source, R.id.iv_img, "field 'iv_img'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.iv_img = null;

    this.target = null;
  }
}
