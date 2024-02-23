package com.vroad.app.berry.data.repository;

import android.content.Context;

import com.vroad.app.basic.io.FileUtils;
import com.vroad.app.berry.Berry;
import com.vroad.app.berry.data.Result;
import com.vroad.app.berry.data.datasource.LoginDataSource;
import com.vroad.app.berry.data.pojo.LoggedInUser;

import java.io.File;

import lombok.Getter;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {
  public static String LOGGED_IN_USER = "loggedInUser";
  private static volatile LoginRepository instance;
  private final LoginDataSource dataSource;
  @Getter
  private LoggedInUser user = null;
  private final File userProfile = new File(Berry.getContext().getFilesDir(), LOGGED_IN_USER);

  private LoginRepository(LoginDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public static LoginRepository getInstance() {
    return getInstance(new LoginDataSource());
  }

  public static LoginRepository getInstance(LoginDataSource dataSource) {
    if (instance == null) {
      instance = new LoginRepository(dataSource);
    }
    return instance;
  }

  public boolean isLoggedIn() {
    Context context = Berry.getContext();
    if (!userProfile.exists())
      return false;
    user = FileUtils.readAs(userProfile);
    return user != null;
  }

  @SuppressWarnings("all")
  public void logout() {
    if (userProfile.exists())
      userProfile.delete();
    user = null;
    dataSource.logout();
  }

  @SuppressWarnings("all")
  private void setLoggedInUser(LoggedInUser user) {
    if (userProfile.exists())
      userProfile.delete();
    FileUtils.saveTo(user, userProfile);
    this.user = user;
  }

  public Result<LoggedInUser> login(String username, String password) {
    Result<LoggedInUser> result = dataSource.login(username, password);
    if (result != null)
      setLoggedInUser(result.getData());
    return result;
  }
}