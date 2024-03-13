package com.vroad.app.libui.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.UUID;

public class UtilsUI {
  private static final String toolbarTitle = UUID.randomUUID().toString();
  public static void setVisibility(View view, boolean visible) {
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
  }

  public static void setVisibility(View root, int childViewId, boolean visible) {
    setVisibility(root.findViewById(childViewId), visible);
  }

  public static void setIcon(View view, Drawable icon) {
    ImageView iconView = (ImageView) view;
    if (icon != null) {
      iconView.setImageDrawable(icon);
      iconView.setVisibility(View.VISIBLE);
    }
  }

  public static void setIcon(View root, int childViewId, Drawable icon) {
    setIcon(root.findViewById(childViewId), icon);
  }

  public static void setText(View view, String text) {
    TextView textView = (TextView) view;
    textView.setText(text);
    textView.setVisibility(View.VISIBLE);
  }

  public static void setText(View root, int childViewId, String text) {
    setText(root.findViewById(childViewId), text);
  }

  public static void setTextSize(View view, float size) {
    ((TextView)view).setTextSize(size);
  }

  public static void setTextSize(View root, int childViewId, float size) {
    setTextSize(root.findViewById(childViewId), size);
  }

  public static void setSize(View view, int size) {
    setSize(view, size, true, size, true);
  }

  public static void setSize(View root, int childViewId, int size) {
    setSize(root.findViewById(childViewId), size);
  }

  public static void setSize(View view, int height, boolean setHeight, int width, boolean setWidth) {
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
    if (setHeight)
      params.height = height;
    if (setWidth)
      params.width = width;
    view.setLayoutParams(params);
  }

  public static void setSize(View root, int childViewId, int height, boolean setHeight, int width, boolean setWidth) {
    setSize(root.findViewById(childViewId), height, setHeight, width, setWidth);
  }

  public static void setColor(View view, int color) {
    setColor(view, color, false);
  }

  public static void setColor(View root, int childViewId, int color) {
    setColor(root.findViewById(childViewId), color);
  }

  public static void setColor(View view, int color, boolean background) {
    if (view instanceof TextView && !background)
      ((TextView) view).setTextColor(color);
    else
      view.setBackgroundColor(color);
  }

  public static void setColor(View root, int childViewId, int color, boolean background) {
    setColor(root.findViewById(childViewId), color, background);
  }

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

  public static void setMargin(View root, int childViewId,
                               int top, boolean setTop,
                               int right, boolean setRight,
                               int bottom, boolean setBottom,
                               int left, boolean setLeft) {
    setMargin(root.findViewById(childViewId), top, setTop, right, setRight, bottom, setBottom, left, setLeft);
  }

  public static void centerToolbarTitle(Toolbar toolbar) {
    final CharSequence originalTitle = toolbar.getTitle();
    toolbar.setTitle(toolbarTitle);
    for (int i = 0; i < toolbar.getChildCount(); i++) {
      View view = toolbar.getChildAt(i);
      if (view instanceof TextView) {
        TextView textView = (TextView) view;
        if (toolbarTitle.contentEquals(textView.getText())) {
          Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
          params.gravity = Gravity.CENTER;
          textView.setLayoutParams(params);
        }
      }
    }
    toolbar.setTitle(originalTitle);
  }

  public static void setStatusBarColor(Activity activity, @ColorInt int color) {
    if (activity == null)
      return;
    activity.getWindow().setStatusBarColor(color);
  }
}
