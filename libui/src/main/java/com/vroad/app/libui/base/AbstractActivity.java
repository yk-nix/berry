package com.vroad.app.libui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.viewbinding.ViewBinding;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public abstract class AbstractActivity
    extends AppCompatActivity
    implements AbstractComponent {
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
  private @LayoutRes Integer layout;

  protected AbstractActivity(boolean printLifecycleEventEnabled,
                             @NonNull @LayoutRes Integer layout) {
    this(printLifecycleEventEnabled, layout, null, null, null);
  }

  protected AbstractActivity(boolean printLifecycleEventEnabled,
                             @NonNull @LayoutRes Integer layout,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass) {
    this(printLifecycleEventEnabled, layout, viewModelClass, null, null);
  }

  protected AbstractActivity(boolean printLifecycleEventEnabled,
                             @NonNull @LayoutRes Integer layout,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass,
                             @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    this(printLifecycleEventEnabled, layout, viewModelClass, viewModelFactoryClass, null);
  }

  private AbstractActivity(boolean printLifecycleEventEnabled,
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

  protected AbstractActivity(boolean printLifecycleEventEnabled) {
    this(printLifecycleEventEnabled, null, null, null, null);
  }

  protected AbstractActivity(boolean printLifecycleEventEnabled,
                             @NonNull Class<? extends ViewBinding> viewBindingClass) {
    this(printLifecycleEventEnabled, viewBindingClass, null, null, null);
  }


  protected AbstractActivity(boolean printLifecycleEventEnabled,
                             @NonNull Class<? extends ViewBinding> viewBindingClass,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass) {
    this(printLifecycleEventEnabled, viewBindingClass, viewModelClass, null, null);
  }

  protected AbstractActivity(boolean printLifecycleEventEnabled,
                             @NonNull Class<? extends ViewBinding> viewBindingClass,
                             @NonNull Class<? extends AbstractViewModel> viewModelClass,
                             @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass) {
    this(printLifecycleEventEnabled, viewBindingClass, viewModelClass, viewModelFactoryClass, null);
  }

  private AbstractActivity(boolean printLifecycleEventEnabled,
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

  private void setContentView() {
    if (viewBindingClass != null) {
      _binding = setContentView(this, viewBindingClass);
    } else {
      _binding = setContentView(this, layout);
    }
  }

  private void createViewModel() {
    if (viewModelClass == null) {
      return;
    }
    if (viewModelFactoryClass == null) {
      _viewModel = createViewModel(viewModelClass);
    } else {
      if (creationExtras == null)
        creationExtras = getDefaultViewModelCreationExtras();
      _viewModel = createViewModel(viewModelClass, viewModelFactoryClass, creationExtras);
    }
  }

  @Override
  final protected void onCreate(@Nullable Bundle savedInstanceState) {
    printLifecycleEvent(Lifecycle.Event.ON_CREATE);
    super.onCreate(savedInstanceState);
    setContentView();
    createViewModel();
    init(savedInstanceState);
  }

  @Override
  protected void onStart() {
    printLifecycleEvent(Lifecycle.Event.ON_START);
    super.onStart();
  }

  @Override
  protected void onResume() {
    printLifecycleEvent(Lifecycle.Event.ON_RESUME);
    super.onResume();
  }

  @Override
  protected void onPause() {
    printLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    super.onPause();
  }

  @Override
  protected void onStop() {
    printLifecycleEvent(Lifecycle.Event.ON_STOP);
    super.onStop();
  }

  @Override
  final protected void onDestroy() {
    printLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    release();
    super.onDestroy();
  }

  @Override
  protected void onRestart() {
    printLifecycleEvent("ON_RESTART");
    super.onRestart();
  }
}
