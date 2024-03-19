package com.vroad.app.berry;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicApplication;
import com.vroad.app.berry.net.RetrofitService;
import com.vroad.app.berry.ui.common.AppViewModelStoreOwner;
import com.vroad.app.basic.common.ApplicationInfo;

public class Berry extends BasicApplication {
  private final static String serverBaseUrl = "http://192.168.3.241";

  private void init() {
    XLog.init();
    SDKInitializer.initialize(getApplicationContext());
    SDKInitializer.setCoordType(CoordType.BD09LL);
    RetrofitService.init(getApplicationContext(), serverBaseUrl);
    ApplicationInfo.setContext(getApplicationContext());
    ApplicationInfo.setViewModelStoreOwner(new AppViewModelStoreOwner());
  }

  @Override
  public void onCreate() {
    super.onCreate();
    init();
  }
}
