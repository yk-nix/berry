package com.vroad.app.libui.base;

import androidx.lifecycle.Lifecycle;

import com.elvishew.xlog.XLog;

public interface LifecycleEventPrintable {
  boolean isPrintLifecycleEventEnabled();

  default String getName() {
    return getClass().getSimpleName();
  }

  default void printLifecycleEvent(Lifecycle.Event event) {
    if (isPrintLifecycleEventEnabled())
      XLog.d("--%s %s--", getName(), event);
  }

  default void printLifecycleEvent(String event) {
    if (isPrintLifecycleEventEnabled())
      XLog.d("--%s %s--", getName(), event);
  }
}
