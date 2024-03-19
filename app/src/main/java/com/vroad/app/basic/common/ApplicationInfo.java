package com.vroad.app.basic.common;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStoreOwner;

import lombok.Getter;
import lombok.Setter;

@SuppressLint("StaticFieldLeak")
public class ApplicationInfo {
  @Getter
  private static Context context;
  @Getter
  @Setter
  @Nullable
  private static ViewModelStoreOwner viewModelStoreOwner;

  public static void setContext(Context applicationContext) {
    context = applicationContext;
  }
}
