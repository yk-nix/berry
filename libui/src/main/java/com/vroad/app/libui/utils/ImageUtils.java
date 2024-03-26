package com.vroad.app.libui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ImageUtils {

  @Nullable
  public static Bitmap decodeBitmapFromResource(Context appContext, @DrawableRes int drawableId, @ColorInt int tint) {
    Drawable drawable = ContextCompat.getDrawable(appContext, drawableId);
    if (drawable != null) {
      Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.setTint(tint);
      drawable.draw(canvas);
      return bitmap;
    }
    return null;
  }

}
