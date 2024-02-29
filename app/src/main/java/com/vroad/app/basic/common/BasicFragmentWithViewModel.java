package com.vroad.app.basic.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import lombok.Getter;

public abstract class BasicFragmentWithViewModel<T extends ViewBinding, V extends ViewModel> extends BasicFragment<T> {
  protected V viewModel;

  public BasicFragmentWithViewModel() {
    super(false);
  }

  public BasicFragmentWithViewModel(boolean enablePrintLifecycleEvent) {
    super(enablePrintLifecycleEvent);
  }

  @Override
  @SuppressWarnings("unchecked")
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    printLifecycleEvent("ON_CREATE_VIEW");
    try {
      binding = getViewBinding(this::getLayoutInflater, container, false);
      viewModel = new ViewModelProvider(this).get((Class<V>) getGenericParameterClassType(1));
      init();
      return binding.getRoot();
    } catch (Exception e) {
      return null;
    }
  }
}
