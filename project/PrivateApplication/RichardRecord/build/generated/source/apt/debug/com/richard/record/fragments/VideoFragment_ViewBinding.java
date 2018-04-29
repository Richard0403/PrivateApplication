// Generated code from Butter Knife. Do not modify!
package com.richard.record.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.richard.record.R;
import com.richard.record.widget.MaxListView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoFragment_ViewBinding<T extends VideoFragment> implements Unbinder {
  protected T target;

  @UiThread
  public VideoFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.rl_no_record = Utils.findRequiredViewAsType(source, R.id.rl_no_record, "field 'rl_no_record'", RelativeLayout.class);
    target.lv_vedio_short = Utils.findRequiredViewAsType(source, R.id.lv_vedio_short, "field 'lv_vedio_short'", MaxListView.class);
    target.lv_vedio_long = Utils.findRequiredViewAsType(source, R.id.lv_vedio_long, "field 'lv_vedio_long'", MaxListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rl_no_record = null;
    target.lv_vedio_short = null;
    target.lv_vedio_long = null;

    this.target = null;
  }
}
