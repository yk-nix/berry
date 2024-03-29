package com.vroad.app.berry.ui.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.FragmentTasksBinding;
import com.vroad.app.berry.databinding.LayoutTasksEntryBinding;
import com.vroad.app.berry.ui.common.BaseFragment;
import com.vroad.app.berry.ui.task.TaskActivity;
import com.vroad.app.libui.base.AbstractApplication;
import com.vroad.app.libui.utils.UtilsUI;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Setter;

public class TasksFragment extends BaseFragment<FragmentTasksBinding, TasksViewModel> {
  private final LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

  public TasksFragment() {
    super(false, R.layout.fragment_tasks);
  }

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    UtilsUI.centerToolbarTitle(binding.toolbar);
    binding.taskRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    binding.taskRecycleView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

    binding.smartRefreshLayout.setOnRefreshListener(refreshLayout ->
        viewModel.refreshTasks());
    binding.smartRefreshLayout.setOnLoadMoreListener(refreshLayout ->
        viewModel.loadMoreTasks());
    viewModel.getTasks().observe(this,
        (tasks) -> binding.taskRecycleView.setAdapter(new TaskViewAdapter(tasks)));
    viewModel.getLoadMoreResultState().observe(this,
        (result) -> binding.smartRefreshLayout.finishLoadMore());
    viewModel.getFreshResultState().observe(this,
        (result) -> binding.smartRefreshLayout.finishRefresh());
    viewModel.getDate().observe(this,
        (date) -> viewModel.loadTasks(date));

    //viewModel.setDate(LocalDateTime.now());
    viewModel.setDate(LocalDateTime.of(2023, 10, 26, 0, 0));
  }

  @Override
  public void release() {
  }

  @Override
  public ViewModelStoreOwner getViewModelStoreOwner() {
    return getAbstractApplication().getViewModelStoreOwner();
  }

  @Override
  public AbstractApplication getAbstractApplication() {
    return (AbstractApplication) requireActivity().getApplication();
  }

  class TaskViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Setter
    private List<Task> tasks;
    LayoutTasksEntryBinding viewBinding;

    public TaskViewAdapter(List<Task> tasks) {
      this.tasks = tasks;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(TasksFragment.this.getContext());
      viewBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_tasks_entry, parent, false);
      return new RecyclerView.ViewHolder(viewBinding.getRoot()) {
      };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      if (tasks == null || position >= tasks.size())
        return;
      Task task = tasks.get(position);
      if (task != null) {
        viewBinding.getRoot().setOnClickListener((v) -> {
          Intent intent = new Intent(getContext(), TaskActivity.class);
          Bundle param = new Bundle();
          intent.putExtra(Task.TAG, task);
          startActivity(intent);
        });
        viewBinding.setTask(task);
      }
    }

    @Override
    public int getItemCount() {
      List<Task> tasks = viewModel.getTasks().getValue();
      if (tasks == null)
        return 0;
      return viewModel.getTasks().getValue().size();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
      super.onViewRecycled(holder);
    }
  }
}