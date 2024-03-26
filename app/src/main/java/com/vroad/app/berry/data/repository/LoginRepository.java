package com.vroad.app.berry.data.repository;

import android.app.Application;

import androidx.annotation.Nullable;

import com.vroad.app.libui.io.FileUtils;
import com.vroad.app.berry.data.datasource.LoginDataSource;
import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.net.Result;

import java.io.File;

import lombok.Getter;

public class LoginRepository {
  public static final String LOGGED_IN_USER = "loggedInUser";
  private static volatile LoginRepository instance;
  private final LoginDataSource dataSource;
  @Getter
  private LoggedInUser user;
  private final File userProfile;

  private LoginRepository(Application app, LoginDataSource dataSource) {
    this.dataSource = dataSource;
    userProfile = new File(app.getFilesDir(), LOGGED_IN_USER);
    if (userProfile.exists()) {
      user = FileUtils.readAs(userProfile);
    }
  }

  public static LoginRepository getInstance(Application app) {
    return getInstance(app, new LoginDataSource());
  }

  public static LoginRepository getInstance(Application app, LoginDataSource dataSource) {
    if (instance == null) {
      instance = new LoginRepository(app, dataSource);
    }
    return instance;
  }

  public boolean isLoggedIn() {
    return user != null;
  }

  private void setLoggedInUser(LoggedInUser user) {
    user.saveAs(userProfile);
    this.user = user;
  }

  @Nullable
  public Result<LoggedInUser> login(String username, String password) {
    Result<LoggedInUser> result = dataSource.login(username, password);
    if (result != null)
      setLoggedInUser(result.getData());
    return result;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Nullable
  public Result<String> logout() {
    Result<String> result = dataSource.logout();
    if (result != null && result.OK() && userProfile != null) {
      userProfile.delete();
      user = null;
    }
    return result;
  }
}