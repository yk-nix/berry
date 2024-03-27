package com.vroad.app.berry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.elvishew.xlog.XLog;
import com.vroad.app.libui.utils.UtilsUI;

public class BindingAdapters {
  public static enum ShapeType {
    rectangle,
    oval,
    line,
    ring
  }

  @BindingAdapter(value = {"shapeBackgroundType", "shapeBackgroundColor"}, requireAll = true)
  public static void setShapeBackground(View view,
                                        ShapeType shapeBackgroundType,
                                        @ColorInt int shapeBackgroundColor) {
    XLog.i("-----------shape: %s  %x---------", shapeBackgroundType, shapeBackgroundColor);
    Context context = view.getContext();
    view.setBackground(UtilsUI.getShapeDrawable(
        shapeBackgroundType.ordinal(),
        shapeBackgroundColor,
        1,
        Color.BLACK,
        1.0F,
        0.0F,
        8.0F
    ));
  }

}
