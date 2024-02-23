package com.vroad.app.berry.net;

import com.elvishew.xlog.XLog;
import com.vroad.app.berry.data.Result;
import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.pojo.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class UserService extends RetrofitService {
  public interface Api {
    @POST("/api/v3.0.0/users/login")
    Call<Result<LoggedInUser>> login(@Body User user);
    @POST("/api/v3.0.0/users/logout")
    Call<Result<String>> logout();
  }

  public static Result<LoggedInUser> login(String name, String password) throws IOException {
    Api api = retrofit.create(Api.class);
    User user = new User();
    user.setName(name);
    user.setPassword(password);
    Response<Result<LoggedInUser>> resp = api.login(user).execute();
    return resp.body();
  }

  public static void logout() throws IOException {
    Api api = retrofit.create(Api.class);
    Response<Result<String>> resp = api.logout().execute();
    XLog.i("---- Logout: %s", resp.body());
  }
}
