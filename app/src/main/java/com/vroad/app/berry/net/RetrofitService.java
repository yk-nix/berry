package com.vroad.app.berry.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
  public static Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("http://192.168.3.241")
      .addConverterFactory(GsonConverterFactory.create())
      .build();
}
