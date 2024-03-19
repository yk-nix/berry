package com.vroad.app.basic.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

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
      ViewModelStoreOwner viewModelStoreOwner = ApplicationInfo.getViewModelStoreOwner();
      if (viewModelStoreOwner == null)
        viewModelStoreOwner = this;
      binding = getViewBinding(this::getLayoutInflater, container, false);
      viewModel = new ViewModelProvider(viewModelStoreOwner).get((Class<V>) getGenericParameterClassType(1));
      init();
      return binding.getRoot();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
