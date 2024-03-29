package com.vroad.app.berry.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.elvishew.xlog.XLog;
import com.vroad.app.libui.utils.UtilsUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindingAdapters {
  public static enum ShapeType {
    rectangle,
    oval,
    line,
    ring
  }

  @BindingAdapter(value = {"shape_background_type", "shape_background_color"}, requireAll = false)
  public static void setShapeBackground(View view,
                                        ShapeType shapeBackgroundType,
                                        @ColorInt Integer shapeBackgroundColor) {
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

  private static void tryAddSateColor(Integer color, Integer state, boolean value, List<Integer> states, List<Integer> values, List<Integer> colors) {
    if (color != null) {
      states.add(state);
      values.add(value ? 0 : 1);
      colors.add(color);
    }
  }

  @BindingAdapter(value = { "pressed_state_background_color",
                            "hovered_state_background_color",
                            "checked_stated_background_color",
                            "selected_state_background_color",
                            "normal_state_background_color"}, requireAll = false)
  public static void setBackgroundColor(View view,
                                        @ColorInt Integer pressedStateColor,
                                        @ColorInt Integer hoveredStateColor,
                                        @ColorInt Integer checkedStateColor,
                                        @ColorInt Integer selectedStateColor,
                                        @ColorInt Integer normalSateColor) {
    Drawable bg = view.getBackground();
    List<Integer> stateList = new ArrayList<>();
    List<Integer> valueList = new ArrayList<>();
    List<Integer> colorList = new ArrayList<>();
    tryAddSateColor(normalSateColor, android.R.attr.state_pressed, false, stateList, valueList, colorList);
    tryAddSateColor(pressedStateColor, android.R.attr.state_pressed, true, stateList, valueList, colorList);
    tryAddSateColor(hoveredStateColor, android.R.attr.state_hovered, true, stateList, valueList, colorList);
    tryAddSateColor(checkedStateColor, android.R.attr.state_checked, true, stateList, valueList, colorList);
    tryAddSateColor(selectedStateColor, android.R.attr.state_selected, true, stateList, valueList, colorList);
    int count = stateList.size();
    if (count == 0)
      return;
    int[][] states = new int [count][2];
    int[] colors = new int [count];
    for (int i = 0; i < count; i++) {
      states[i][0] = stateList.get(i);
      states[i][1] = valueList.get(i);
      colors[i] = colorList.get(i);
    }
    ColorStateList colorStateList = new ColorStateList(states, colors);
    bg.setTintList(colorStateList);
  }

  @BindingAdapter("textStyle")
  public static void setTextStyle(View view, int tf) {
    if (view instanceof TextView) {
      ((TextView)view).setTypeface(null, tf);
    }
  }
}
