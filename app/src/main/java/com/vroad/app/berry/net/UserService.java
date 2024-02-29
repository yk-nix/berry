package com.vroad.app.berry.net;

import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.pojo.Result;
import com.vroad.app.berry.data.pojo.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class UserService extends RetrofitService<UserService.Api> {
  public interface Api {
    @POST("/api/v3.0.0/users/login")
    Call<Result<LoggedInUser>> login(@Body User user);
    @POST("/api/v3.0.0/users/logout")
    Call<Result<String>> logout();
  }
  public Result<LoggedInUser> login(User user) {
    return call(api::login, user);
  }

  public Result<String> logout() {
    return call(api::logout);
  }
}
