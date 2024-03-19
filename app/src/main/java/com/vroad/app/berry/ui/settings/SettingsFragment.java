package com.vroad.app.berry.ui.settings;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.vroad.app.basic.common.BasicFragmentWithViewModel;
import com.vroad.app.berry.databinding.FragmentSettingsBinding;
import com.vroad.app.berry.ui.home.HomeActivity;

public class SettingsFragment extends BasicFragmentWithViewModel<FragmentSettingsBinding, SettingsViewModel> {


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void init() {
    Activity activity = getActivity();
    if (activity != null) {
      binding.logout.setOnClickListener((v) -> ((HomeActivity) activity).logout(v));
    }
  }

  @Override
  protected void release() {
    super.release();
  }
}