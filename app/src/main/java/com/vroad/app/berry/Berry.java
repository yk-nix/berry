package com.vroad.app.berry;

import android.annotation.SuppressLint;
import android.content.Context;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicApplication;
import com.vroad.app.berry.net.RetrofitService;
import com.vroad.app.berry.ui.common.ApplicationInfo;

import lombok.Getter;

public class Berry extends BasicApplication {
  private final static String serverBaseUrl = "http://192.168.3.241";

  private void init() {
    XLog.init();
    RetrofitService.init(getApplicationContext(), serverBaseUrl);
    ApplicationInfo.setContext(getApplicationContext());
  }

  @Override
  public void onCreate() {
    super.onCreate();
    init();
  }
}
