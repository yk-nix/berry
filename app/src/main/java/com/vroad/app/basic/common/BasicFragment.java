package com.vroad.app.basic.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import lombok.Getter;

public abstract class BasicFragment<T extends ViewBinding>
    extends Fragment
    implements LifecycleEventPrintable, ViewBindingAware, GenericParameterAware {
  @Getter(onMethod_ = {@Override})
  private final boolean printLifecycleEventEnabled;

  protected T binding;

  protected BasicFragment() {
    super();
    printLifecycleEventEnabled = false;
  }

  protected BasicFragment(boolean printLifecycleEventEnabled) {
    this.printLifecycleEventEnabled = printLifecycleEventEnabled;
  }

  protected abstract void init();
  protected void release() {}

  @Override
  @SuppressWarnings("unchecked")
  public Class<T> getViewBindingClassType() {
    return (Class<T>) getGenericParameterClassType(0);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    printLifecycleEvent(Lifecycle.Event.ON_CREATE);
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    printLifecycleEvent("ON_CREATE_VIEW");
    try {
      binding = getViewBinding(this::getLayoutInflater, container, false);
      init();
      return binding.getRoot();
    } catch (Exception e) {
      return null;
    }
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
  public void onDestroyView() {
    printLifecycleEvent("ON_DESTROY_VIEW");
    release();
    super.onDestroyView();
    binding = null;
  }
}
