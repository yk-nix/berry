package com.vroad.app.berry;

import androidx.lifecycle.ViewModelStoreOwner;

import com.elvishew.xlog.XLog;
import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.berry.net.RetrofitService;
import com.vroad.app.berry.ui.common.AppViewModelStoreOwner;

import lombok.Getter;

public class Berry extends AbstractApplication {
  private final static String serverBaseUrl = "http://192.168.3.241";
  @Getter(onMethod_={@Override})
  private ViewModelStoreOwner viewModelStoreOwner;

  private void init() {
    XLog.init();
    RetrofitService.init(this, serverBaseUrl);
    viewModelStoreOwner = new AppViewModelStoreOwner();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    init();
  }
}
