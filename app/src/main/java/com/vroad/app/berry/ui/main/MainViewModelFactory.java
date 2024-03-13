package com.vroad.app.berry.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicViewModelFactory;

public class MainViewModelFactory extends BasicViewModelFactory {
  protected MainViewModelFactory(Context appContext) {
    super(appContext);
  }

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
    if (modelClass.isAssignableFrom(MainViewModel.class)) {
      return (T) new MainViewModel(appContext);
    } else {
      throw new IllegalArgumentException("Unknown ViewModel class");
    }
  }
}
