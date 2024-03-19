package com.vroad.app.libui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.StyleableRes;

public interface ViewInitializer {

  void setAttribute(TypedArray typedArray, @StyleableRes int attrId);

  default void init(Context context, AttributeSet set, @StyleableRes int[] attrs) {
    try {
      TypedArray typedArray = context.obtainStyledAttributes(set, attrs);
      for (int i = 0; i < typedArray.getIndexCount(); i++) {
        setAttribute(typedArray, typedArray.getIndex(i));
      }
      typedArray.recycle();
    } catch (Exception ignored) {
    }
  }
}
