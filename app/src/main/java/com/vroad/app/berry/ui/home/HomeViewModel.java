package com.vroad.app.berry.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vroad.app.basic.utils.AppUtils;
import com.vroad.app.berry.data.enums.ConditionType;
import com.vroad.app.berry.data.enums.OperatorEnum;
import com.vroad.app.berry.data.pojo.Condition;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.net.TaskService;

import java.util.List;

import lombok.Getter;
public class HomeViewModel extends ViewModel {
  @Getter
  private final MutableLiveData<List<Task>> tasks;
  private final TaskService taskService;

  public HomeViewModel() {
    tasks = new MutableLiveData<>();
    taskService = new TaskService();
  }

  @SuppressWarnings("unchecked")
  public void loadTasks() {
    if (tasks.isInitialized())
      return;
    AppUtils.exec(
        taskService::list,
        new DynamicQueryParameter(new Condition[]{
            new Condition<>(
                "CREATE_TIME",
                ConditionType.TIME_RANGE,
                OperatorEnum.BETWEEN,
                new String[]{"2023-02-28 00:00:00", "2024-02-28 23:59:59"}
            )
        }),
        result -> {
          if (result.OK())
            tasks.postValue(result.getData());
        }
    );
  }
}