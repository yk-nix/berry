package com.vroad.app.berry.ui.tasks;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import com.vroad.app.berry.BR;
import com.vroad.app.berry.data.enums.ConditionType;
import com.vroad.app.berry.data.enums.OperatorEnum;
import com.vroad.app.berry.data.pojo.Condition;
import com.vroad.app.berry.data.pojo.DynamicQueryParameter;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.net.Result;
import com.vroad.app.berry.net.TaskService;
import com.vroad.app.berry.ui.common.OperationResult;
import com.vroad.app.libui.base.AbstractViewModel;
import com.vroad.app.libui.utils.AppUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.Getter;

public class TasksViewModel extends AbstractViewModel {
  private final LocalDateTime now = LocalDateTime.now();
  @Getter
  private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
  @Getter
  private final MutableLiveData<OperationResult> freshResultState = new MutableLiveData<>();
  @Getter
  private final MutableLiveData<OperationResult> loadMoreResultState = new MutableLiveData<>();
  private final TaskService taskService = new TaskService();
  @Bindable
  @Getter(onMethod_ = {@Bindable})
  private boolean loading = false;
  @Bindable
  @Getter(onMethod_ = {@Bindable})
  private MutableLiveData<LocalDateTime> date = new MutableLiveData<>();
  @Bindable
  @Getter(onMethod_ = {@Bindable})
  private boolean today = false;
  @Bindable
  @Getter(onMethod_ = {@Bindable})
  private boolean yesterday = false;

  public void setLoading(boolean loading) {
    this.loading = loading;
    notifyPropertyChanged(BR.loading);
  }

  public void setDate(LocalDateTime date) {
    this.date.setValue(date);
    checkDate(date);
    notifyPropertyChanged(BR.date);
  }

  public void setToday(boolean today) {
    this.today = today;
    notifyPropertyChanged(BR.today);
  }

  public void setYesterday(boolean yesterday) {
    this.yesterday = yesterday;
    notifyPropertyChanged(BR.yesterday);
  }

  private boolean isSameDay(LocalDateTime d1, LocalDateTime d2) {
    return (d1.truncatedTo(ChronoUnit.DAYS).isEqual(d2.truncatedTo(ChronoUnit.DAYS)));
  }

  private void checkDate(LocalDateTime date) {
    setToday(false);
    setYesterday(false);
    if (isSameDay(date, now))
      setToday(true);
    else if (isSameDay(date, now.minusDays(1)))
      setYesterday(true);
  }

  private <T, R> void execLoading(Function<T, R> action, T parameter, Consumer<R> after) {
    setLoading(true);
    AppUtils.exec(action, parameter, (result) -> {
      after.accept(result);
      setLoading(false);
    });
  }

  @SuppressWarnings("unchecked")
  private DynamicQueryParameter getQueryCondition(LocalDateTime date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Condition<String> condition = new Condition<>("CREATE_TIME", ConditionType.TIME_RANGE, OperatorEnum.BETWEEN,
        new String[]{date.format(formatter), date.plusDays(1).format(formatter)});
    Condition<?>[] conditions = new Condition[]{condition};
    return new DynamicQueryParameter((Condition<String>[]) conditions);
  }

  private void handleResponse(Result<List<Task>> result) {
    if (result == null || !result.OK())
      return;
    List<Task> tasks = result.getData();
    if (tasks != null)
      this.tasks.postValue(tasks);
  }

  public void loadTasks(@NonNull LocalDateTime date) {
    execLoading(taskService::list, getQueryCondition(date), this::handleResponse);
  }

  public void refreshTasks() {
    LocalDateTime date = this.date.getValue();
    if (date != null) {
      execLoading(taskService::list, getQueryCondition(date), result -> {
        handleResponse(result);
        freshResultState.postValue(new OperationResult(true));
      });
    }
  }

  public void loadMoreTasks() {
    LocalDateTime date = this.date.getValue();
    if (date != null) {
      execLoading(taskService::list, getQueryCondition(date), result -> {
        handleResponse(result);
        loadMoreResultState.postValue(new OperationResult(true));
      });
    }
  }

}