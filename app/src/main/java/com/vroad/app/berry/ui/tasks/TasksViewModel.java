package com.vroad.app.berry.ui.tasks;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.vroad.app.libui.base.AbstractViewModel;
import com.vroad.app.libui.date.DateTimeFormatters;
import com.vroad.app.libui.utils.AppUtils;
import com.vroad.app.berry.data.enums.ConditionType;
import com.vroad.app.berry.data.enums.OperatorEnum;
import com.vroad.app.berry.data.pojo.Condition;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.net.TaskService;
import com.vroad.app.berry.ui.common.OperationResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class TasksViewModel extends AbstractViewModel {
  @Getter
  private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
  @Getter
  private final MutableLiveData<OperationResult> freshResultState = new MutableLiveData<>();
  @Getter
  private final MutableLiveData<OperationResult> loadMoreResultState = new MutableLiveData<>();
  private final TaskService taskService = new TaskService();
  @Getter
  @Setter
  private LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);


  @SuppressWarnings("unchecked")
  public void loadTasks(@NonNull LocalDateTime date) {
    AppUtils.exec(
        taskService::list,
        new DynamicQueryParameter(new Condition[]{
            new Condition<>(
                "CREATE_TIME",
                ConditionType.TIME_RANGE,
                OperatorEnum.BETWEEN,
                new String[]{
                    date.format(DateTimeFormatters.log_timestamp_sec),
                    date.plusDays(1).format(DateTimeFormatters.log_timestamp_sec)
                })
        }),
        result -> {
          if (result != null && result.OK()) {
            List<Task> taskList = result.getData();
            if (taskList != null) {
              tasks.postValue(taskList);
            }
          }
        }
    );
  }

  public void refreshTasks() {
    AppUtils.exec(() -> {
      SystemClock.sleep(2000);
      List<Task> taskList = tasks.getValue();
      if (taskList != null && !taskList.isEmpty()) {
        taskList.add(0, tasks.getValue().get(0));
      }
      tasks.postValue(taskList);
      freshResultState.postValue(new OperationResult(true));
    });
  }

  public void loadMoreTasks() {
    AppUtils.exec(() -> {
      SystemClock.sleep(2000);
      List<Task> taskList = tasks.getValue();
      if (taskList != null && !taskList.isEmpty()) {
        taskList.add(tasks.getValue().get(0));
      }
      tasks.postValue(taskList);
      loadMoreResultState.postValue(new OperationResult(true));
    });
  }
}