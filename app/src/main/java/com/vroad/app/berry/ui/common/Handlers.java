package com.vroad.app.berry.ui.common;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;

import com.vroad.app.berry.R;
import com.vroad.app.berry.ui.tasks.TasksViewModel;
import com.vroad.app.libui.base.AbstractViewModel;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Handlers {
  private final LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

  private <T extends AbstractViewModel> LocalDateTime getDate(T viewModel) {
    if (viewModel instanceof TasksViewModel) {
      return ((TasksViewModel) viewModel).getDate().getValue();
    }
    return today;
  }

  private <T extends AbstractViewModel> void setDate(T viewModel, LocalDateTime date) {
    if (viewModel instanceof TasksViewModel) {
      ((TasksViewModel) viewModel).setDate(date);
    }
  }

  public <T extends AbstractViewModel> void showDatePickerDialog(Context context, T viewModel) {
    LocalDateTime date = getDate(viewModel);
    new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
      LocalDateTime _date = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0, 0);
      if (_date.isAfter(today)) {
        new AlertDialog.Builder(context).setMessage(R.string.date_selector_error).create().show();
        return;
      }
      setDate(viewModel, _date);
    }, date.getYear(), date.getMonth().getValue() - 1, date.getDayOfMonth()).show();
  }
}
