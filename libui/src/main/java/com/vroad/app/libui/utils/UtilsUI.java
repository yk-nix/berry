package com.vroad.app.libui.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;

import org.w3c.dom.Text;


public class UtilsUI {
  @UiThread
  public static void setVisibility(View view, boolean visible) {
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  @UiThread
  public static void setVisibility(View root, int childViewId, boolean visible) {
    setVisibility(root.findViewById(childViewId), visible);
  }

  @UiThread
  public static void setIcon(View view, Drawable icon) {
    ImageView iconView = (ImageView) view;
    if (icon != null) {
      iconView.setImageDrawable(icon);
      iconView.setVisibility(View.VISIBLE);
    }
  }

  @UiThread
  public static void setIcon(View root, int childViewId, Drawable icon) {
    setIcon(root.findViewById(childViewId), icon);
  }

  @UiThread
  public static void setText(View view, String text) {
    TextView textView = (TextView) view;
    textView.setText(text);
    textView.setVisibility(View.VISIBLE);
  }

  @UiThread
  public static void setText(View root, int childViewId, String text) {
    setText(root.findViewById(childViewId), text);
  }

  @UiThread
  public static void setTextSize(View view, float size) {
    ((TextView) view).setTextSize(size);
  }

  @UiThread
  public static void setTextSize(View root, int childViewId, float size) {
    setTextSize(root.findViewById(childViewId), size);
  }

  @UiThread
  public static void setSize(View view, int size) {
    setSize(view, size, true, size, true);
  }

  @UiThread
  public static void setSize(View root, int childViewId, int size) {
    setSize(root.findViewById(childViewId), size);
  }

  @UiThread
  public static void setSize(View view, int height, boolean setHeight, int width, boolean setWidth) {
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
    if (setHeight)
      params.height = height;
    if (setWidth)
      params.width = width;
    view.setLayoutParams(params);
  }

  @UiThread
  public static void setSize(View root, int childViewId, int height, boolean setHeight, int width, boolean setWidth) {
    setSize(root.findViewById(childViewId), height, setHeight, width, setWidth);
  }

  @UiThread
  public static void setColor(View view, int color) {
    setColor(view, color, false);
  }

  @UiThread
  public static void setColor(View root, int childViewId, int color) {
    setColor(root.findViewById(childViewId), color);
  }

  @UiThread
  public static void setColor(View view, int color, boolean background) {
    if (view instanceof TextView && !background)
      ((TextView) view).setTextColor(color);
    else
      view.setBackgroundColor(color);
  }

  @UiThread
  public static void setColor(View root, int childViewId, int color, boolean background) {
    setColor(root.findViewById(childViewId), color, background);
  }

  @UiThread
  public static void setBackgroundColor(@Nullable View view, @ColorInt int color) {
    if (view == null)
      return;
    view.setBackgroundColor(color);
  }

  @UiThread
  public static void setMargin(View view,
                               int top, boolean setTop,
                               int right, boolean setRight,
                               int bottom, boolean setBottom,
                               int left, boolean setLeft) {
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
    if (setTop)
      params.topMargin = top;
    if (setRight)
      params.rightMargin = right;
    if (setBottom)
      params.bottomMargin = bottom;
    if (setLeft)
      params.leftMargin = left;
    view.setLayoutParams(params);
  }

  @UiThread
  public static void setMargin(View root, int childViewId,
                               int top, boolean setTop,
                               int right, boolean setRight,
                               int bottom, boolean setBottom,
                               int left, boolean setLeft) {
    setMargin(root.findViewById(childViewId), top, setTop, right, setRight, bottom, setBottom, left, setLeft);
  }

  @UiThread
  @Nullable
  private static Toolbar getToolbar(@Nullable ActionBar actionBar) {
    Toolbar ret = null;
    if (actionBar != null) {
      Object obj = ReflectionUtil.getDeclaredField(WindowDecorActionBar.class, actionBar, "mDecorToolbar");
      if (obj == null) {
        obj = ReflectionUtil.getDeclaredField("androidx.appcompat.app.ToolbarActionBar", actionBar, "mDecorToolbar");
      }
      if (obj != null) {
        ret = (Toolbar) ReflectionUtil.getDeclaredField(ToolbarWidgetWrapper.class, obj, "mToolbar");
      }
    }
    return ret;
  }

  @UiThread
  @Nullable
  public static TextView getActionBarTitle(@Nullable ActionBar actionBar) {
    return getToolbarTitle(getToolbar(actionBar));
  }

  @UiThread
  @Nullable
  public static TextView getToolbarTitle(@Nullable Toolbar toolbar) {
    if (toolbar == null)
      return null;
    return ReflectionUtil.getDeclaredField(Toolbar.class, toolbar, "mTitleTextView");
  }

  @UiThread
  @Nullable
  public static ImageButton getToolbarNavigationButton(@Nullable Toolbar toolbar) {
    if (toolbar == null)
      return null;
    return ReflectionUtil.getDeclaredField(Toolbar.class, toolbar, "mNavButtonView");
  }

  @UiThread
  @Nullable
  public static ImageButton getActionBarNavigationButton(@Nullable ActionBar actionBar) {
    return getToolbarNavigationButton(getToolbar(actionBar));
  }

  @UiThread
  @Nullable
  public static TextView getToolbarSubtitle(@Nullable Toolbar toolbar) {
    if (toolbar == null)
      return null;
    return ReflectionUtil.getDeclaredField(Toolbar.class, "mSubtitleTextView");
  }

  @UiThread
  @Nullable
  public static TextView getActionBarSubtitle(@Nullable ActionBar actionBar) {
    return getToolbarSubtitle(getToolbar(actionBar));
  }

  @UiThread
  @Nullable
  public static ImageView getToolbarLogo(@Nullable Toolbar toolbar) {
    if (toolbar == null)
      return null;
    return ReflectionUtil.getDeclaredField(Toolbar.class, "mLogoView");
  }

  @UiThread
  @Nullable
  public static ImageView getActionBarLogo(@NonNull ActionBar actionBar) {
    return getToolbarLogo(getToolbar(actionBar));
  }


  @UiThread
  public static void centerToolbarChild(@Nullable View view) {
    if (view != null) {
      Toolbar.LayoutParams params = new Toolbar.LayoutParams(
          Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
      params.gravity = Gravity.CENTER;
      view.setLayoutParams(params);
    }
  }

  @UiThread
  public static void centerToolbarTitle(@Nullable Toolbar toolbar) {
    centerToolbarChild(getToolbarTitle(toolbar));
  }

  @UiThread
  public static void centerActionBarTitle(@Nullable ActionBar actionBar) {
    centerToolbarChild(getToolbarTitle(getToolbar(actionBar)));
  }

  @UiThread
  public static void setStatusBarColor(@Nullable Activity activity, @ColorInt int color) {
    if (activity != null) {
      activity.getWindow().setStatusBarColor(color);
    }
  }
}
