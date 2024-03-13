package com.vroad.app.berry.ui.common;

import android.annotation.SuppressLint;
import android.content.Context;

import lombok.Getter;

@SuppressLint("StaticFieldLeak")
public class ApplicationInfo {
  @Getter
  private static Context context;

  public static void setContext(Context applicationContext) {
    context = applicationContext;
  }
}
