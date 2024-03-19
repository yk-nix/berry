package com.vroad.app.berry.net;

import androidx.annotation.Nullable;

import com.elvishew.xlog.XLog;
import com.vroad.app.berry.data.pojo.Device;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class DeviceService extends RetrofitService<DeviceService.Api> {
  public interface Api {
    @POST("/api/v3.0.0/devices/list")
    Call<Result<List<Device>>> list(@Body DynamicQueryParameter param);
  }

  @Nullable
  public Result<List<Device>> list(DynamicQueryParameter param) {
    return call(api::list, param);
  }
}
