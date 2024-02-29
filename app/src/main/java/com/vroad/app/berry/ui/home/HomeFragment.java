package com.vroad.app.berry.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vroad.app.basic.common.BasicFragmentWithViewModel;
import com.vroad.app.berry.R;
import com.vroad.app.berry.data.pojo.Task;
import com.vroad.app.berry.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends BasicFragmentWithViewModel<FragmentHomeBinding, HomeViewModel> {
  @Override
  protected void init() {
    viewModel.getTasks().observe(getViewLifecycleOwner(), taskObserver);
    viewModel.loadTasks();
  }

  @Override
  protected void release() {
    viewModel.getTasks().removeObserver(taskObserver);
  }

  private final Observer<List<Task>> taskObserver = tasks -> {
    binding.taskRecycleView.setAdapter(new TaskViewAdapter(tasks));
    binding.taskRecycleView.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext()));
    binding.taskRecycleView.addItemDecoration(new DividerItemDecoration(HomeFragment.this.requireContext(), DividerItemDecoration.VERTICAL));
  };

  static class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView id;
    public TextView content;

    public ImageButton button;

    public TaskViewHolder(@NonNull View itemView) {
      super(itemView);
      id = itemView.findViewById(R.id.id);
      content = itemView.findViewById(R.id.content);
      button = itemView.findViewById(R.id.imageButton);
    }
  }

  class TaskViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private final List<Task> tasks;

    public TaskViewAdapter(List<Task> tasks) {
      this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(HomeFragment.this.getContext()).inflate(R.layout.task_entry_layout, parent, false);
      TaskViewHolder taskViewHolder = new TaskViewHolder(view);
      taskViewHolder.button.setOnClickListener(v -> {
        Bundle params = new Bundle();
        params.putSerializable(Task.TAG, tasks.get(taskViewHolder.getAdapterPosition()));
        Navigation.findNavController(HomeFragment.this.requireActivity(), R.id.nav_host_fragment_activity_home)
            .navigate(R.id.action_navigation_home_to_detailFragment, params);
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