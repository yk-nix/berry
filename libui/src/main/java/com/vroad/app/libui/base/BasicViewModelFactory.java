package com.vroad.app.libui.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;

public class BasicViewModelFactory extends AbstractViewModelFactory {
  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> viewModelClass, @NonNull CreationExtras extras) {
    if (BasicViewModel.class.isAssignableFrom(viewModelClass)) {
      return BasicViewModel.newInstance(getApplication(extras), viewModelClass);
    }
    return AbstractViewModel.newInstance(extras, viewModelClass);
  }
}
