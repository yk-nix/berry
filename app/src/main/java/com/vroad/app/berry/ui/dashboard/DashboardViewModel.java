package com.vroad.app.berry.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vroad.app.basic.utils.AppUtils;
import com.vroad.app.berry.data.pojo.Device;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.net.DeviceService;

import java.util.List;

import lombok.Getter;

public class DashboardViewModel extends ViewModel {
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