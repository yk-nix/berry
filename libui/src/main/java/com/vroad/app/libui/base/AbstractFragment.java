package com.vroad.app.libui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.viewbinding.ViewBinding;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractFragment
    extends Fragment
    implements AbstractComponent, AbstractActivityAware {
  private ViewBinding _binding;
  private ViewModel _viewModel;

  @Getter(onMethod_ = {@Override})
  @Setter
  private boolean printLifecycleEventEnabled;

  @Setter
  private Class<? extends ViewBinding> viewBindingClass;
  @Setter
  private Class<? extends AbstractViewModel> viewModelClass;
  @Setter
  private Class<? extends AbstractViewModelFactory> viewModelFactoryClass;
  @Setter
  private CreationExtras creationExtras;
  @Setter
  private Integer layout;

  @Getter(onMethod_ = {@Override})
  private AbstractActivity abstractActivity;

  protected AbstractFragment(boolean printLifecycleEventEnabled) {
    this(printLifecycleEventEnabled, null, null, null, null);
  }

  protected AbstractFragment(boolean printLifecycleEventEnabled,
                             @NonNull @LayoutRes Integer layout) {
    this(printLifecycleEventEnabled, layout, null, null, null);
  }

  protected AbstractFragment(boolean printLifecycleEventEnabled,
                             @NonNull @LayoutRes Integer layout,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass) {
    this(printLifecycleEventEnabled, layout, viewModelClass, null, null);
  }

  protected AbstractFragment(boolean printLifecycleEventEnabled,
                             @NonNull @LayoutRes Integer layout,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass,
                             @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    this(printLifecycleEventEnabled, null, null, null, null);
  }

  private AbstractFragment(boolean printLifecycleEventEnabled,
                           int layout,
                           Class<? extends AbstractViewModel> viewModelClass,
                           Class<? extends AbstractViewModelFactory> viewModelFactoryClass,
                           CreationExtras creationExtras) {
    this.printLifecycleEventEnabled = printLifecycleEventEnabled;
    this.layout = layout;
    this.viewModelClass = viewModelClass;
    this.viewModelFactoryClass = viewModelFactoryClass;
    this.creationExtras = creationExtras;
  }

  protected AbstractFragment(boolean printLifecycleEventEnabled,
                             @NonNull Class<? extends ViewBinding> viewBindingClass) {
    this(printLifecycleEventEnabled, viewBindingClass, null, null, null);
  }


  protected AbstractFragment(boolean printLifecycleEventEnabled,
                             @NonNull Class<? extends ViewBinding> viewBindingClass,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass) {
    this(printLifecycleEventEnabled, viewBindingClass, viewModelClass, null, null);
  }

  protected AbstractFragment(boolean printLifecycleEventEnabled,
                             @NonNull Class<? extends ViewBinding> viewBindingClass,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass,
                             @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    this(printLifecycleEventEnabled, viewBindingClass, viewModelClass, viewModelFactoryClass, null);
  }

  private AbstractFragment(boolean printLifecycleEventEnabled,
                           Class<? extends ViewBinding> viewBindingClass,
                           Class<? extends AbstractViewModel> viewModelClass,
                           Class<? extends AbstractViewModelFactory> viewModelFactoryClass,
                           CreationExtras creationExtras) {
    this.printLifecycleEventEnabled = printLifecycleEventEnabled;
    this.viewBindingClass = viewBindingClass;
    this.viewModelClass = viewModelClass;
    this.viewModelFactoryClass = viewModelFactoryClass;
    this.creationExtras = creationExtras;
  }


  @SuppressWarnings("unchecked")
  public <T extends ViewBinding> T getViewBinding() {
    assert _binding != null;
    return (T) _binding;
  }

  @SuppressWarnings("unchecked")
  public <T extends AbstractViewModel> T getViewModel() {
    assert _viewModel != null;
    return (T) _viewModel;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    printLifecycleEvent(Lifecycle.Event.ON_CREATE);
    super.onCreate(savedInstanceState);
  }

  protected void setContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
    if (viewBindingClass != null) {
      _binding = setContentView(inflater, viewBindingClass);
    } else {
      _binding = setContentView(layout, inflater, container);
    }
  }

  protected void createViewModel() {
    if (viewModelClass == null)
      return;
    if (viewModelFactoryClass == null) {
      _viewModel = createViewModel(abstractActivity, viewModelClass);
    } else {
      _viewModel = createViewModel(abstractActivity, viewModelClass, viewModelFactoryClass, creationExtras);
    }
  }

  @Override
  final public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
    printLifecycleEvent("ON_CREATE_VIEW");
    abstractActivity = (AbstractActivity) requireActivity();
    setContentView(inflater, container);
    createViewModel();
    init(savedInstanceState);
    return _binding.getRoot();
  }

  @Override
  public void onStart() {
    printLifecycleEvent(Lifecycle.Event.ON_START);
    super.onStart();
  }

  @Override
  public void onResume() {
    printLifecycleEvent(Lifecycle.Event.ON_RESUME);
    super.onResume();
  }

  @Override
  public void onPause() {
    printLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    super.onPause();
  }

  @Override
  public void onStop() {
    printLifecycleEvent(Lifecycle.Event.ON_STOP);
    super.onStop();
  }

  @Override
  public void onDestroy() {
    printLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    super.onDestroy();
  }

  @Override
  final public void onDestroyView() {
    printLifecycleEvent("ON_DESTROY_VIEW");
    release();
    super.onDestroyView();
  }
}
