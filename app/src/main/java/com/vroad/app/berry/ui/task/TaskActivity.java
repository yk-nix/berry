package com.vroad.app.berry.ui.task;

import androidx.appcompat.app.ActionBar;

import com.vroad.app.basic.common.BasicActivityWithViewModel;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.ActivityTaskBinding;
import com.vroad.app.berry.ui.tasks.TasksViewModel;

public class TaskActivity extends BasicActivityWithViewModel<ActivityTaskBinding, TasksViewModel> {

  @Override
  protected void init() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_backward_24dp);
    }
    Task task = getIntent().getSerializableExtra(Task.TAG, Task.class);
    if (task != null) {
      binding.testText.setText(String.format("%s : %s", task.getId(), task.getNote()));
    }
  }
}