package com.vroad.app.berry.ui.task;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.ActivityTaskBinding;
import com.vroad.app.libui.base.BasicActivity;

public class TaskActivity extends BasicActivity<ActivityTaskBinding, TaskViewModel> {
  public TaskActivity() {
    super(false, R.layout.activity_task);
  }

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_backward_16dp);
    }
    Task task = (Task) getIntent().getSerializableExtra(Task.TAG);
    if (task != null) {
      binding.setTask(task);
    }
  }

  @Override
  public void release() {
  }
}