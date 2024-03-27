package com.vroad.app.berry.data.datasource;

import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.pojo.User;
import com.vroad.app.berry.net.Result;
import com.vroad.app.berry.net.UserService;

public class LoginDataSource extends AbstractDataSource<UserService> {

  public Result<LoggedInUser> login(String username, String password) {
    return service.login(new User(username, password));
  }

  public Result<String> logout() {
    return service.logout();
  }
}