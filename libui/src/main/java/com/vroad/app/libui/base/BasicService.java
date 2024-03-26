package com.vroad.app.libui.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import lombok.Getter;

public abstract class BasicService
    extends Service implements LifecycleEventPrintable {
  @Getter(onMethod_ = {@Override})
  private final boolean printLifecycleEventEnabled;
  public static final String INTENT_TAG_MESSENGER = "messenger";

  protected BasicService() {
    super();
    printLifecycleEventEnabled = false;
  }

  protected abstract IBinder getBinder(Intent intent);

  protected BasicService(boolean enablePrintLifecycleEvent) {
    super();
    printLifecycleEventEnabled = enablePrintLifecycleEvent;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    printLifecycleEvent("ON_BIND");
    return getBinder(intent);
  }

  @Override
  public boolean onUnbind(Intent intent) {
    printLifecycleEvent("ON_UNBIND");
    return super.onUnbind(intent);
  }

  @Override
  public void onRebind(Intent intent) {
    printLifecycleEvent("ON_REBIND");
    super.onRebind(intent);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    printLifecycleEvent("ON_START_COMMAND");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onCreate() {
    printLifecycleEvent(Lifecycle.Event.ON_CREATE);
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    printLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    super.onDestroy();
  }
}
