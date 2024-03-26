package com.vroad.app.berry.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.libui.base.BasicViewModel;
import com.vroad.app.libui.utils.AppUtils;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.ui.common.OperationResult;

import lombok.Getter;

public class HomeViewModel extends BasicViewModel {
  @Getter
  private final MutableLiveData<OperationResult> logoutResultState = new MutableLiveData<>();
  private final LoginRepository loginRepository;

  public HomeViewModel(AbstractApplication abstractApplication) {
    super(abstractApplication);
    loginRepository = LoginRepository.getInstance(abstractApplication);
  }

  public void logout() {
    AppUtils.exec(loginRepository::logout, result -> {
      if (result.OK())
        logoutResultState.postValue(new OperationResult(true));
      else
        logoutResultState.postValue(new OperationResult(false, result.getMessage()));
    });
  }

  public boolean isLoggedIn() {
    return loginRepository.isLoggedIn();
  }
}
