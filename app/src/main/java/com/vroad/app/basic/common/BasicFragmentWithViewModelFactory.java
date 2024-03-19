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

public abstract class BasicFragmentWithViewModelFactory<T extends ViewBinding, V extends ViewModel, F extends BasicViewModelFactory>
    extends BasicFragmentWithViewModel<T, V> {
  public BasicFragmentWithViewModelFactory() {
    super(false);
  }

  public BasicFragmentWithViewModelFactory(boolean enablePrintLifecycleEvent) {
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
      BasicViewModelFactory factory = BasicViewModelFactory
          .newInstance((Class<F>) getGenericParameterClassType(2), requireActivity().getApplicationContext());
      if (factory == null)
        viewModel = new ViewModelProvider(viewModelStoreOwner).get((Class<V>) getGenericParameterClassType(1));
      else
        viewModel = new ViewModelProvider(viewModelStoreOwner, factory).get((Class<V>) getGenericParameterClassType(1));
      init();
      return binding.getRoot();
    } catch (Exception e) {
      return null;
    }
  }
}
