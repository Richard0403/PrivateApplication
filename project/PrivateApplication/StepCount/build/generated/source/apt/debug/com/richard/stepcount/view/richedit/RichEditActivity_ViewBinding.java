// Generated code from Butter Knife. Do not modify!
package com.richard.stepcount.view.richedit;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.richard.stepcount.R;
import com.richard.stepcount.view.richedit.view.RichTextEditor;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RichEditActivity_ViewBinding<T extends RichEditActivity> implements Unbinder {
  protected T target;

  private View view2131165273;

  private View view2131165321;

  private View view2131165320;

  @UiThread
  public RichEditActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.tv_title_left = Utils.findRequiredViewAsType(source, R.id.tv_title_left, "field 'tv_title_left'", TextView.class);
    target.tv_right = Utils.findRequiredViewAsType(source, R.id.tv_complete, "field 'tv_right'", TextView.class);
    target.et_new_title = Utils.findRequiredViewAsType(source, R.id.et_new_title, "field 'et_new_title'", EditText.class);
    target.et_new_content = Utils.findRequiredViewAsType(source, R.id.et_new_content, "field 'et_new_content'", RichTextEditor.class);
    target.sv_content = Utils.findRequiredViewAsType(source, R.id.sv_content, "field 'sv_content'", ScrollView.class);
    target.rl_select_pic = Utils.findRequiredViewAsType(source, R.id.rl_select_pic, "field 'rl_select_pic'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_pic, "method 'Onclick'");
    view2131165273 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_complete, "method 'Onclick'");
    view2131165321 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.Onclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rl_back, "method 'Onclick'");
    view2131165320 = view;
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

    target.tv_title_left = null;
    target.tv_right = null;
    target.et_new_title = null;
    target.et_new_content = null;
    target.sv_content = null;
    target.rl_select_pic = null;

    view2131165273.setOnClickListener(null);
    view2131165273 = null;
    view2131165321.setOnClickListener(null);
    view2131165321 = null;
    view2131165320.setOnClickListener(null);
    view2131165320 = null;

    this.target = null;
  }
}
