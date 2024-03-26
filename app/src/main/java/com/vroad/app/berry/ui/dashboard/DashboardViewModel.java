package com.vroad.app.berry.ui.dashboard;

import androidx.lifecycle.MutableLiveData;

import com.vroad.app.libui.base.AbstractViewModel;
import com.vroad.app.libui.utils.AppUtils;
import com.vroad.app.berry.data.pojo.Device;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.net.DeviceService;

import java.util.List;

import lombok.Getter;

public class DashboardViewModel extends AbstractViewModel {
  @Getter
  private final MutableLiveData<List<Device>> devices = new MutableLiveData<>();
  private final DeviceService deviceService = new DeviceService();

  public void loadDevices() {
    AppUtils.exec(
        deviceService::list,
        new DynamicQueryParameter(),
        result -> {
          if (result != null && result.OK()) {
            List<Device> deviceList = result.getData();
            if (deviceList != null) {
              devices.postValue(deviceList);
            }
          }
        }
    );
  }
}