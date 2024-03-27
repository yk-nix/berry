package com.vroad.app.libui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

public abstract class BasicActivity<B extends ViewBinding, M extends AbstractViewModel>
    extends AbstractActivity
    implements GenericParameterAware {
  protected B binding;
  protected M viewModel;

  @SuppressWarnings("unchecked")
  public BasicActivity(boolean enablePrintLifecycleEvent) {
    super(enablePrintLifecycleEvent);
    setViewBindingClass((Class<B>) getGenericParameterClassType(0));
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
  }

  @SuppressWarnings("unchecked")
  public BasicActivity(boolean enablePrintLifecycleEvent,
                       Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    super(enablePrintLifecycleEvent);
    setViewBindingClass((Class<B>) getGenericParameterClassType(0));
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
    setViewModelFactoryClass(viewModelFactoryClass);
  }

  @SuppressWarnings("unchecked")
  public BasicActivity(boolean enablePrintLifecycleEvent,
                       @NonNull @LayoutRes Integer layout) {
    super(enablePrintLifecycleEvent, layout);
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
  }

  @SuppressWarnings("unchecked")
  public BasicActivity(boolean enablePrintLifecycleEvent,
                       @NonNull @LayoutRes Integer layout,
                       Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    super(enablePrintLifecycleEvent, layout);
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
    setViewModelFactoryClass(viewModelFactoryClass);
  }

  @Override
  @CallSuper
  public void init(@Nullable Bundle savedInstanceState) {
    binding = getViewBinding();
    viewModel = getViewModel();
  }

  @Override
  public ViewModelStoreOwner getViewModelStoreOwner() {
    return this;
  }

  @Override
  public AbstractApplication getAbstractApplication() {
    return (AbstractApplication) getApplication();
  }
}
