package com.vroad.app.berry.ui.home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.vroad.app.basic.common.BasicViewModelFactory;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.ui.login.LoginViewModel;

public class HomeViewModelFactory extends BasicViewModelFactory {
  protected HomeViewModelFactory(Context appContext) {
    super(appContext);
  }

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(HomeViewModel.class)) {
      return (T) new HomeViewModel(LoginRepository.getInstance(appContext));
    } else {
      throw new IllegalArgumentException("Unknown ViewModel class");
    }
  }
}
