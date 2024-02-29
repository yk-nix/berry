package com.vroad.app.berry.net;

import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.data.pojo.Result;
import com.vroad.app.berry.data.pojo.Task;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class TaskService extends RetrofitService<TaskService.Api> {
  public interface Api {
    @POST("/api/v3.0.0/tasks/list")
    Call<Result<List<Task>>> list(@Body DynamicQueryParameter param);
  }

  public Result<List<Task>> list(DynamicQueryParameter param) {
    return call(api::list, param);
  }
}
