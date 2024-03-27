package com.vroad.app.berry.ui.tasks;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elvishew.xlog.XLog;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.FragmentTasksBinding;
import com.vroad.app.berry.ui.common.OperationResult;
import com.vroad.app.berry.ui.task.TaskActivity;
import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.libui.base.BasicFragment;
import com.vroad.app.libui.utils.UtilsUI;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Setter;

public class TasksFragment extends BasicFragment<FragmentTasksBinding, TasksViewModel> {
  private final LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

  public TasksFragment() {
    super(false);
  }

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    LocalDateTime date = viewModel.getDate();
    if (date == null)
      date = today;
    UtilsUI.centerToolbarTitle(binding.toolbar);
    viewModel.getTasks().observe(getViewLifecycleOwner(), taskObserver);
    binding.taskRecycleView.setLayoutManager(new LinearLayoutManager(TasksFragment.this.getContext()));
    binding.taskRecycleView.addItemDecoration(new DividerItemDecoration(TasksFragment.this.requireContext(), DividerItemDecoration.VERTICAL));
    binding.smartRefreshLayout.setOnRefreshListener(onRefreshListener);
    binding.smartRefreshLayout.setOnLoadMoreListener(onLoadMoreListener);
    viewModel.getFreshResultState().observe(getViewLifecycleOwner(), refreshResultObserver);
    viewModel.getLoadMoreResultState().observe(getViewLifecycleOwner(), loadMoreResultObserver);
    initRadioButtons(date);
    binding.rtbDateSelectorToday.setOnCheckedChangeListener(onRadioButtonCheckedChangeListener);
    binding.rtbDateSelectorYesterday.setOnCheckedChangeListener(onRadioButtonCheckedChangeListener);
    binding.calendar.setOnClickListener(onCalendarClickedListener);
  }

  @Override
  public void release() {
    viewModel.getTasks().removeObserver(taskObserver);
    viewModel.getFreshResultState().removeObserver(refreshResultObserver);
    viewModel.getLoadMoreResultState().removeObserver(loadMoreResultObserver);
  }

  @Override
  public ViewModelStoreOwner getViewModelStoreOwner() {
    return getAbstractApplication().getViewModelStoreOwner();
  }

  private void initRadioButtons(@NonNull LocalDateTime date) {
    focusOutRadioButton(binding.rtbDateSelectorToday);
    focusOutRadioButton(binding.rtbDateSelectorYesterday);
    if (date.isEqual(today))
      focusOnRadioButton(binding.rtbDateSelectorToday);
    else if (date.isEqual(today.minusDays(1)))
      focusOnRadioButton(binding.rtbDateSelectorYesterday);
    //setDateTextViewValue(date);
    // ----------------  for test -----------------------
    setDateTextViewValue(LocalDateTime.of(2023,10, 26, 0, 0));
  }

  private void focusOnRadioButton(@NonNull CompoundButton button) {
    button.setChecked(true);
    button.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.FAKE_BOLD_TEXT_FLAG | button.getPaintFlags());
    button.setTextColor(getResources().getColor(R.color.green, null));
  }

  private void focusOutRadioButton(@NonNull CompoundButton button) {
    button.setChecked(false);
    button.setPaintFlags(~(Paint.UNDERLINE_TEXT_FLAG | Paint.FAKE_BOLD_TEXT_FLAG) & button.getPaintFlags());
    button.setTextColor(getResources().getColor(R.color.black, null));
  }

  private void setDateTextViewValue(@NonNull LocalDateTime date) {
    String fmt = getResources().getString(R.string.date_format);
    binding.tvDateSelectorValue.setText(String.format(fmt, date.getYear(), date.getMonth().getValue(), date.getDayOfMonth()));
    viewModel.setDate(date);
    viewModel.loadTasks(date);
  }

  private final Observer<List<Task>> taskObserver = tasks -> {
    XLog.i("%s", tasks);
    binding.taskRecycleView.setAdapter(new TaskViewAdapter(tasks));
    if (tasks.isEmpty() && !binding.noDataMessage.isShown()) {
      binding.noDataMessage.setVisibility(View.VISIBLE);
    }
  };

  private final Observer<OperationResult> refreshResultObserver = result -> {
    if (result != null && !result.isSuccess()) {
      Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
    }
    binding.smartRefreshLayout.finishRefresh();
  };

  private final Observer<OperationResult> loadMoreResultObserver = result -> {
    if (result != null && !result.isSuccess()) {
      Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
    }
    binding.smartRefreshLayout.finishLoadMore();
  };

  private final OnRefreshListener onRefreshListener = refreshLayout -> {
    if (binding.noDataMessage.isShown())
      binding.noDataMessage.setVisibility(View.GONE);
    viewModel.refreshTasks();
  };

  private final OnLoadMoreListener onLoadMoreListener = refreshLayout -> {
    if (binding.noDataMessage.isShown())
      binding.noDataMessage.setVisibility(View.GONE);
    viewModel.loadMoreTasks();
  };

  private final CompoundButton.OnCheckedChangeListener onRadioButtonCheckedChangeListener = (buttonView, isChecked) -> {
    if (buttonView.isChecked()) {
      LocalDateTime date = today;
      if (buttonView.getId() == R.id.rtb_date_selector_yesterday)
        date = today.minusDays(1);
      setDateTextViewValue(date);
      focusOnRadioButton(buttonView);
    } else {
      focusOutRadioButton(buttonView);
    }
  };

  private final View.OnClickListener onCalendarClickedListener = v -> {
    LocalDateTime lastDate = viewModel.getDate();
    Context context = getContext();
    if (context != null) {
      new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
        LocalDateTime date = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0, 0);
        if (date.isAfter(today)) {
          new AlertDialog.Builder(context).setMessage(R.string.date_selector_error).create().show();
          return;
        }
        initRadioButtons(date);
      }, lastDate.getYear(), lastDate.getMonth().getValue() - 1, lastDate.getDayOfMonth()).show();
    }
  };

  @Override
  public AbstractApplication getAbstractApplication() {
    return (AbstractApplication) requireActivity().getApplication();
  }

  /*-------------------------------------------------------------------------*/
  static class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView id;
    public TextView content;
    public TextView dispatcher;
    public View root;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);
      id = itemView.findViewById(R.id.id);
      content = itemView.findViewById(R.id.content);
      dispatcher = itemView.findViewById(R.id.dispatcher);
      root = itemView;
    }
  }

  class TaskViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    @Setter
    private List<Task> tasks;

    public TaskViewAdapter(List<Task> tasks) {
      this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(TasksFragment.this.getContext()).inflate(R.layout.layout_tasks_entry, parent, false);
      TaskViewHolder taskViewHolder = new TaskViewHolder(view);
      taskViewHolder.root.setOnClickListener((v) -> {
        Intent intent = new Intent(getContext(), TaskActivity.class);
        Bundle param = new Bundle();
        intent.putExtra(Task.TAG, tasks.get(taskViewHolder.getAdapterPosition()));
        startActivity(intent);
      });
      return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
      if (tasks == null)
        return;
      Task task = tasks.get(position);
      holder.id.setText(task.getId());
      holder.content.setText(task.getNote());
      holder.dispatcher.setText(task.getPublisher().getRealName());
    }

    @Override
    public int getItemCount() {
      List<Task> tasks = viewModel.getTasks().getValue();
      if (tasks == null)
        return 0;
      return viewModel.getTasks().getValue().size();
    }

    @Override
    public void onViewRecycled(@NonNull TaskViewHolder holder) {
      super.onViewRecycled(holder);
    }
  }
}