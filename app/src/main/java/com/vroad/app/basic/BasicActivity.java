package com.vroad.app.basic;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.Getter;

public abstract class BasicActivity<T extends ViewBinding>
    extends AppCompatActivity implements LifecycleEventPrintable {
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

  @SuppressWarnings("unchecked")
  private Class<T> getBindingClassType() {
    Type type = getClass().getGenericSuperclass();
    assert type != null;
    return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
  }

  @SuppressWarnings("unchecked")
  private T getBinding() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Class<T> clz = getBindingClassType();
    Method inflate = clz.getMethod("inflate", LayoutInflater.class);
    return (T) inflate.invoke(null, getLayoutInflater());
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    printLifecycleEvent(Lifecycle.Event.ON_CREATE);
    super.onCreate(savedInstanceState);
    try {
      binding = getBinding();
      setContentView(binding.getRoot());
    } catch (Exception ignored) {
      /* */
    }
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
    super.onDestroy();
  }

  @Override
  protected void onRestart() {
    printLifecycleEvent("ON_RESTART");
    super.onRestart();
  }
}
