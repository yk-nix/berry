package com.vroad.app.basic.common;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

public abstract class BasicActivityWithViewModel<T extends ViewBinding, V extends ViewModel>
    extends BasicActivity<T> {
  protected V viewModel;

  public BasicActivityWithViewModel() {
    super(false);
  }

  public BasicActivityWithViewModel(boolean enablePrintLifecycleEvent) {
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
      viewModel = new ViewModelProvider(viewModelStoreOwner).get((Class<V>) getGenericParameterClassType(1));
      setContentView(binding.getRoot());
      init();
    } catch (Exception ignored) {
    }
  }
}
