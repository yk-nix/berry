package com.vroad.app.berry.data.datasource;

import com.vroad.app.berry.data.Result;
import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.pojo.User;
import com.vroad.app.berry.net.UserService;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
  public Result<LoggedInUser> login(String username, String password) {
    try {
      return UserService.login(username, password);
    } catch (Exception e) {
      return null;
    }
  }

  public void logout() {
    try {
      UserService.logout();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}