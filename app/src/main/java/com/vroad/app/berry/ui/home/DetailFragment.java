package com.vroad.app.berry.ui.home;

import android.os.Bundle;

import com.vroad.app.basic.common.BasicFragment;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.FragmentDetailBinding;

public class DetailFragment extends BasicFragment<FragmentDetailBinding> {
  public DetailFragment() {
    super(true);
  }

  @Override
  protected void init() {
    Bundle params = getArguments();
    if (params != null) {
      Task task = params.getSerializable(Task.TAG, Task.class);
      if (task != null) {
        binding.testText.setText(String.format("%s : %s", task.getId(), task.getNote()));
      }
    }
  }
}
