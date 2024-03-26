package com.vroad.app.libui.base;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

public abstract class AbstractViewModelFactory implements ViewModelProvider.Factory {
  public AbstractApplication getApplication(CreationExtras creationExtras) {
    return (AbstractApplication) creationExtras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
  }
}
