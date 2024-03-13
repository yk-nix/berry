package com.vroad.app.berry.ui.dashboard;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.vroad.app.basic.common.BasicFragmentWithViewModel;
import com.vroad.app.berry.R;
import com.vroad.app.berry.databinding.FragmentDashboardBinding;
import com.vroad.app.libui.utils.UtilsUI;

public class DashboardFragment extends BasicFragmentWithViewModel<FragmentDashboardBinding, DashboardViewModel> {
  private Observer<String> textObserver;
  @Override
  protected void init() {
    UtilsUI.centerToolbarTitle(binding.toolbar);
    textObserver = binding.textDashboard::setText;
    viewModel.getText().observe(getViewLifecycleOwner(), textObserver);
  }

  @Override
  protected void release() {
    viewModel.getText().removeObserver(textObserver);
  }
}