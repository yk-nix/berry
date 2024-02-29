package com.vroad.app.basic.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import lombok.Getter;

public abstract class BasicActivity<T extends ViewBinding>
    extends AppCompatActivity
    implements LifecycleEventPrintable, ViewBindingAware, GenericParameterAware {
  @Getter(onMethod_ = {@Override})
  private final boolean printLifecycleEventEnabled;
  protected T binding;

  protected BasicActivity() {
    super();
    printLifecycleEventEnabled = false;
  }

  protected BasicActivity(boolean enablePrintLifecycleEvent) {
    super();
    printLifecycleEventEnabled = enablePrintLifecycleEvent;
  }

  protected abstract void init();
  protected void release() {}

  protected void afterCreate() {
    try {
      binding = getViewBinding(this::getLayoutInflater);
      setContentView(binding.getRoot());
      init();
    } catch (Exception ignored) {
    }
  }

  @SuppressWarnings("unchecked")
  public Class<T> getViewBindingClassType() {
    return (Class<T>) getGenericParameterClassType(0);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    printLifecycleEvent(Lifecycle.Event.ON_CREATE);
    super.onCreate(savedInstanceState);
    afterCreate();
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
  protected void onDestroy() {
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
