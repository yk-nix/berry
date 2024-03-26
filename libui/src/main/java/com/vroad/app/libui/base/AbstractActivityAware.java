package com.vroad.app.libui.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public interface AbstractActivityAware {
  AbstractActivity getAbstractActivity();

  default String getStringResource(@StringRes int id) {
    return getAbstractActivity().getResources().getString(id);
  }

  default float getDimenResource(@DimenRes int id) {
    return getAbstractActivity().getResources().getDimension(id);
  }

  default Drawable getDrawableResource(@DrawableRes int id) {
    return ContextCompat.getDrawable(getAbstractActivity().getApplicationContext(), id);
  }

  @ColorInt
  default int getColorResource(@ColorRes int id) {
    return ContextCompat.getColor(getAbstractActivity().getApplicationContext(), id);
  }

  default Bitmap getBitmapFromDrawableResource(@DrawableRes int id, @ColorRes int tintId) {
    Drawable drawable = getDrawableResource(id);
    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.setTint(getColorResource(tintId));
    drawable.draw(canvas);
    return bitmap;
  }
}
