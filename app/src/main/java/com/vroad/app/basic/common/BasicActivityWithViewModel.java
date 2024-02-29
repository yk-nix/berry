package com.vroad.app.basic.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

public abstract class BasicActivityWithViewModel<T extends ViewBinding, V extends ViewModel>
    extends BasicActivity<T> {
  protected V viewModel;

  public BasicActivityWithViewModel() {
    super(false);
  }

  public BasicActivityWithViewModel(boolean enablePrintLifecycleEvent) {
    super(enablePrintLifecycleEvent);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void afterCreate() {
    try {
      binding = getViewBinding(this::getLayoutInflater);
      viewModel = new ViewModelProvider(this).get((Class<V>) getGenericParameterClassType(1));
      setContentView(binding.getRoot());
      init();
    } catch (Exception ignored) {
    }
  }
}
