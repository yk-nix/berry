package com.vroad.app.berry.net;

import android.content.Context;

import androidx.annotation.NonNull;

import com.elvishew.xlog.XLog;
import com.vroad.app.berry.data.pojo.LoggedInUser;
import com.vroad.app.berry.data.repository.LoginRepository;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;
import java.util.function.Function;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService<API> {
  public static Retrofit retrofit;
  protected API api = retrofit.create(getApiClassType());

  @SuppressWarnings("unchecked")
  private Class<API> getApiClassType() {
    Type type = getClass().getGenericSuperclass();
    assert type != null;
    return (Class<API>) ((ParameterizedType) type).getActualTypeArguments()[0];
  }

  public <T, R> Result<R> call(Function<T, Call<Result<R>>> func, T t) {
    try {
      return func.apply(t).execute().body();
    } catch (Exception e) {
      XLog.d(e);
      return null;
    }
  }

  public <R> Result<R> call(Callable<Call<Result<R>>> func) {
    try {
      return func.call().execute().body();
    } catch (Exception e) {
      XLog.d(e);
      return null;
    }
  }

  public static void init(Context appContext, String baseUrl) {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    client.addInterceptor(new Interceptor() {
      @NonNull
      @Override
      public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        LoggedInUser user = LoginRepository.getInstance(appContext).getUser();
        if (user != null && user.getToken() != null)
          builder.header("token", user.getToken());
        builder.method(original.method(), original.body());
        return chain.proceed(builder.build());
      }
    });
    OkHttpClient httpClient = client.build();
    retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build();
  }
}
