package com.vroad.app.libui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public abstract class BasicFragment<B extends ViewBinding, M extends AbstractViewModel>
    extends AbstractFragment
    implements GenericParameterAware {
  protected B binding;
  protected M viewModel;

  @SuppressWarnings("unchecked")
  public BasicFragment(boolean enablePrintLifecycleEvent) {
    super(false);
    setViewBindingClass((Class<B>) getGenericParameterClassType(0));
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
  }

  @SuppressWarnings("unchecked")
  public BasicFragment(boolean enablePrintLifecycleEvent,
                       @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    super(false);
    setViewBindingClass((Class<B>) getGenericParameterClassType(0));
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
    setViewModelFactoryClass(viewModelFactoryClass);
  }

  @SuppressWarnings("unchecked")
  public BasicFragment(boolean enablePrintLifecycleEvent,
                       @NonNull @LayoutRes Integer layout) {
    super(false, layout);
    setViewBindingClass((Class<B>) getGenericParameterClassType(0));
    setViewModelClass((Class<M>) getGenericParameterClassType(1));
  }

  @SuppressWarnings("unchecked")
  public BasicFragment(boolean enablePrintLifecycleEvent,
                       @NonNull @LayoutRes Integer layout,
                       @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    super(false, layout);
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
  public AbstractApplication getAbstractApplication() {
    return (AbstractApplication) getAbstractActivity().getApplication();
  }
}
