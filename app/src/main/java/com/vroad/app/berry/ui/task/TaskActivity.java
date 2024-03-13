package com.vroad.app.berry.ui.task;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicActivityWithViewModel;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.ActivityTaskBinding;
import com.vroad.app.berry.ui.tasks.TasksViewModel;
import com.vroad.app.libui.utils.UtilsUI;

public class TaskActivity extends BasicActivityWithViewModel<ActivityTaskBinding, TasksViewModel> {

  @Override
  protected void init() {
    ActionBar actionBar =  getSupportActionBar();
    if (actionBar != null) {
     actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_backward_24dp);
    }

//    setSupportActionBar(binding.toolbar);
//    Bundle params = getArguments();
//    if (params != null) {
//      Task task = params.getSerializable(Task.TAG, Task.class);
//      if (task != null) {
//        binding.testText.setText(String.format("%s : %s", task.getId(), task.getNote()));
//      }
//    }
  }
}