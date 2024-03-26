package com.vroad.app.berry.ui.login;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.libui.base.BasicViewModel;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.net.Result;
import com.vroad.app.berry.ui.common.OperationResult;

import lombok.Getter;

public class LoginViewModel extends BasicViewModel {
  @Getter
  private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
  @Getter
  private MutableLiveData<OperationResult> loginResult = new MutableLiveData<>();
  private final LoginRepository loginRepository;

  public LoginViewModel(AbstractApplication abstractApplication) {
    super(abstractApplication);
    loginRepository = LoginRepository.getInstance(abstractApplication);
  }

  public void login(String username, String password) {
    // can be launched in a separate
    new Thread(() -> {
      Result<LoggedInUser> result = loginRepository.login(username, password);
      if (result == null) {
        loginResult.postValue(new OperationResult(false, "error"));
      } else if (result.OK())
        loginResult.postValue(new OperationResult(true, null));
      else {
        loginResult.postValue(new OperationResult(false, result.getMessage()));
      }
    }).start();
  }

  public void loginDataChanged(String username, String password) {
    if (!isUserNameValid(username)) {
      loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
    } else if (!isPasswordValid(password)) {
      loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
    } else {
      loginFormState.setValue(new LoginFormState(true));
    }
  }

  // A placeholder username validation check
  private boolean isUserNameValid(String username) {
    if (username == null) {
      return false;
    }
    if (username.contains("@")) {
      return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    } else {
      return !username.trim().isEmpty();
    }
  }

  // A placeholder password validation check
  private boolean isPasswordValid(String password) {
    return password != null && !password.trim().isEmpty();
  }
}