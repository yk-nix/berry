package com.vroad.app.berry.ui.settings;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.vroad.app.libui.base.BasicFragment;
import com.vroad.app.berry.databinding.FragmentSettingsBinding;
import com.vroad.app.berry.ui.home.HomeActivity;

public class SettingsFragment extends BasicFragment<FragmentSettingsBinding, SettingsViewModel> {
  public SettingsFragment() {
    super(false);
  }

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    Activity activity = getActivity();
    if (activity != null) {
      binding.logout.setOnClickListener((v) -> ((HomeActivity) activity).logout(v));
    }
  }

  @Override
  public void release() {
  }
}