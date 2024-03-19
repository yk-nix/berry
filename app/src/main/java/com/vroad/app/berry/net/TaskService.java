package com.vroad.app.berry.net;

import androidx.annotation.Nullable;

import com.elvishew.xlog.XLog;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.data.pojo.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class TaskService extends RetrofitService<TaskService.Api> {
  public interface Api {
    @POST("/api/v3.0.0/tasks/list")
    Call<Result<List<Task>>> list(@Body DynamicQueryParameter param);
  }

  @Nullable
  public Result<List<Task>> list(DynamicQueryParameter param) {
    return call(api::list, param);
  }
}
