package com.vroad.app.berry.ui.notifications;

import androidx.lifecycle.Observer;

import com.vroad.app.basic.common.BasicFragmentWithViewModel;
import com.vroad.app.berry.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends BasicFragmentWithViewModel<FragmentNotificationsBinding, NotificationsViewModel> {

  private Observer<String> textObserver;

  @Override
  protected void init() {
    textObserver = binding.textNotifications::setText;
    viewModel.getText().observe(getViewLifecycleOwner(), textObserver);
  }

  @Override
  protected void release() {
    viewModel.getText().removeObserver(textObserver);
  }

}