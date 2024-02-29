package com.vroad.app.berry.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.vroad.app.basic.common.BasicViewModelFactory;
import com.vroad.app.berry.data.repository.LoginRepository;

public class LoginViewModelFactory extends BasicViewModelFactory {
  public LoginViewModelFactory(Context appContext) {
    super(appContext);
  }

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(LoginViewModel.class)) {
      return (T) new LoginViewModel(LoginRepository.getInstance(appContext));
    } else {
      throw new IllegalArgumentException("Unknown ViewModel class");
    }
  }
}