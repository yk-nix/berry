package com.vroad.app.berry.ui.dashboard;

import androidx.lifecycle.Observer;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicFragmentWithViewModel;
import com.vroad.app.berry.data.pojo.Device;
import com.vroad.app.berry.databinding.FragmentDashboardBinding;
import com.vroad.app.libui.utils.UtilsUI;

import java.util.List;

public class DashboardFragment extends BasicFragmentWithViewModel<FragmentDashboardBinding, DashboardViewModel> {

  @Override
  protected void init() {
    UtilsUI.centerToolbarTitle(binding.toolbar);
    viewModel.getDevices().observe(getViewLifecycleOwner(), deviceObserver);
    viewModel.loadDevices();
  }

  @Override
  protected void release() {
    super.release();
    viewModel.getDevices().removeObserver(deviceObserver);
  }

  private final Observer<List<Device>> deviceObserver = devices -> {
    XLog.i("----devices: \n%s", devices);
  };
}