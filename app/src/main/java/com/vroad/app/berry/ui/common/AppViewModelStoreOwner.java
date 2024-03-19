package com.vroad.app.berry.ui.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import lombok.Getter;

public class AppViewModelStoreOwner implements ViewModelStoreOwner, LifecycleEventObserver {
  @Getter(onMethod_={@Override, @NonNull})
  private ViewModelStore viewModelStore = new ViewModelStore();
  @Override
  public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
  }
}
