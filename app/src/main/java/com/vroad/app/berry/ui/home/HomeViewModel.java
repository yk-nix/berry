package com.vroad.app.berry.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vroad.app.basic.utils.AppUtils;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.ui.common.OperationResult;

import lombok.Getter;

public class HomeViewModel extends ViewModel {
  @Getter
  private final MutableLiveData<OperationResult> logoutResultState = new MutableLiveData<>();
  private final LoginRepository loginRepository;

  public HomeViewModel(LoginRepository instance) {
    loginRepository = instance;
  }

  public void logout() {
    AppUtils.exec(loginRepository::logout, result -> {
      if (result.OK())
        logoutResultState.postValue(new OperationResult(true));
      else
        logoutResultState.postValue(new OperationResult(false, result.getMessage()));
    });
  }
}
