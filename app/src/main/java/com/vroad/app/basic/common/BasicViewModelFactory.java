package com.vroad.app.basic.common;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

public class BasicViewModelFactory
    implements ViewModelProvider.Factory, GenericParameterAware {
  protected final Context appContext;

  protected BasicViewModelFactory(Context appContext) {
    this.appContext = appContext;
  }

  public static BasicViewModelFactory newInstance(Class<? extends BasicViewModelFactory> clz, Context appContext) {
    try {
      return clz.getDeclaredConstructor(Context.class).newInstance(appContext);
    } catch (Exception e) {
      return null;
    }
  }
}
