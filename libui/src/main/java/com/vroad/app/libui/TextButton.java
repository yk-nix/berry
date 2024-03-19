package com.vroad.app.libui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

public class TextButton extends AppCompatTextView implements ViewInitializer {
  private final StateListDrawable stateListDrawable = new StateListDrawable();
  private int radius = 0;
  private int borderWidth = 1;
  private @ColorInt int borderColor = Color.GRAY;
  private final List<GradientDrawable> backgroundList = new ArrayList<>();

  public TextButton(@NonNull Context context) {
    this(context, null);
  }

  public TextButton(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    registerBackground(new int[]{android.R.attr.state_pressed}, new GradientDrawable());
    registerBackground(new int[]{}, new GradientDrawable());
    init(context, attrs, R.styleable.TextButton);
    backgroundList.forEach((e) -> {
      e.setStroke(borderWidth, borderColor);
      e.setCornerRadius(radius);
    });
    setBackground(stateListDrawable);
  }

  private void registerBackground(int[] stateSet, GradientDrawable background) {
    background.setState(stateSet);
    if (stateSet.length == 0)
      background.setColor(Color.TRANSPARENT);
    else
      background.setColor(Color.GREEN);
    backgroundList.add(background);
    stateListDrawable.addState(stateSet, background);
  }

  @Override
  public void setAttribute(TypedArray typedArray, int attrId) {
    if (attrId == R.styleable.TextButton_radius) {
      radius = (int) typedArray.getDimension(attrId, 0);
    } else if (attrId == R.styleable.TextButton_background_color) {
      backgroundList.get(0).setColor(typedArray.getColor(attrId, Color.TRANSPARENT));
    } else if (attrId == R.styleable.TextButton_pressed_color) {
      backgroundList.get(1).setColor(typedArray.getColor(attrId, Color.GREEN));
    } else if (attrId == R.styleable.TextButton_border_width) {
      borderWidth = (int) typedArray.getDimension(attrId, 1);
    } else if (attrId == R.styleable.TextButton_border_color) {
      borderColor = (int) typedArray.getColor(attrId, Color.GRAY);
    }
  }
}
