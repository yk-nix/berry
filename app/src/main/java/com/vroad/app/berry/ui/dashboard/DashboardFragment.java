package com.vroad.app.berry.ui.dashboard;

import androidx.lifecycle.Observer;

import com.vroad.app.basic.common.BasicFragmentWithViewModel;
import com.vroad.app.berry.databinding.FragmentDashboardBinding;

public class DashboardFragment extends BasicFragmentWithViewModel<FragmentDashboardBinding, DashboardViewModel> {
  private Observer<String> textObserver;
  @Override
  protected void init() {
    textObserver = binding.textDashboard::setText;
    viewModel.getText().observe(getViewLifecycleOwner(), textObserver);
  }

  @Override
  protected void release() {
    viewModel.getText().removeObserver(textObserver);
  }
}