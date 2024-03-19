package com.vroad.app.basic.common;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

public abstract class BasicActivityWithViewModelFactory<T extends ViewBinding, V extends ViewModel, F extends BasicViewModelFactory>
    extends BasicActivityWithViewModel<T, V> {
  public BasicActivityWithViewModelFactory() {
    super(false);
  }

  public BasicActivityWithViewModelFactory(boolean enablePrintLifecycleEvent) {
    super(enablePrintLifecycleEvent);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void afterCreate() {
    try {
      ViewModelStoreOwner viewModelStoreOwner = ApplicationInfo.getViewModelStoreOwner();
      if (viewModelStoreOwner == null)
        viewModelStoreOwner = this;
      binding = getViewBinding(this::getLayoutInflater);
      BasicViewModelFactory factory = BasicViewModelFactory
          .newInstance((Class<F>) getGenericParameterClassType(2), getApplicationContext());
      if (factory == null)
        viewModel = new ViewModelProvider(viewModelStoreOwner).get((Class<V>) getGenericParameterClassType(1));
      else
        viewModel = new ViewModelProvider(viewModelStoreOwner, factory).get((Class<V>) getGenericParameterClassType(1));
      setContentView(binding.getRoot());
      init();
    } catch (Exception ignored) {
    }
  }
}
