package com.vroad.app.berry.ui.login;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.pojo.Result;
import com.vroad.app.berry.data.repository.LoginRepository;

import lombok.Getter;

public class LoginViewModel extends ViewModel {
  @Getter
  private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
  @Getter
  private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
  private final LoginRepository loginRepository;

  LoginViewModel(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  public void login(String username, String password) {
    // can be launched in a separate
    new Thread(() -> {
      Result<LoggedInUser> result = loginRepository.login(username, password);
      if (result.OK())
        loginResult.postValue(new LoginResult(true, null));
      else {
        loginResult.postValue(new LoginResult(false, result.getMessage()));
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
    return password != null && password.trim().length() > 5;
  }
}