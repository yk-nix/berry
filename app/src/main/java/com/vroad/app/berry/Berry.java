package com.vroad.app.berry;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.BasicApplication;
import com.vroad.app.berry.data.datasource.LoginDataSource;
import com.vroad.app.berry.data.repository.LoginRepository;

import lombok.Getter;

public class Berry extends BasicApplication {
  @SuppressLint("StaticFieldLeak")
  @Getter
  private static Context context;

  private void init() {
    XLog.init();
    context = getApplicationContext();
    LoginRepository.getInstance();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    init();
  }
}
