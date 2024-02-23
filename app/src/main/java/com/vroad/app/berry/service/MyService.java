package com.vroad.app.berry.service;

import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.BasicService;
import com.vroad.app.berry.ui.main.MainActivity;

import lombok.Getter;

public class MyService extends BasicService {
  private Thread thread;
  @Getter
  private int count = 0;

  public MyService() {
    super(true);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    try {
      startForeground(100,
          new NotificationCompat.Builder(this, MainActivity.NORMAL_CHANNEL_ID)
              .setOngoing(true)
              .build());
      startThread();
      super.onStartCommand(intent, flags, startId);
    } catch (Exception e) {
      e.printStackTrace();
      stopSelf();
    }
    return START_NOT_STICKY;
  }

  @Override
  public void onDestroy() {
    stopThread();
    super.onDestroy();
  }

  @Override
  public IBinder onBind(Intent intent) {
    super.onBind(intent);
    Messenger messenger = new Messenger(new Handler(Looper.getMainLooper(), msg -> {
      XLog.i("msg: %s", msg.obj);
      return true;
    }));
    return messenger.getBinder();
  }

  @Override
  public boolean onUnbind(Intent intent) {
    stopThread();
    return super.onUnbind(intent);
  }

  private void startThread() {
    if (thread == null) {
      thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (!Thread.interrupted()) {
            SystemClock.sleep(2000);
            XLog.i("--count=%s", count++);
          }
        }
      });
    }
    thread.start();
  }

  private void startThread(Messenger messenger) {
    if (thread == null) {
      thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (!Thread.interrupted()) {
            SystemClock.sleep(2000);
            XLog.i("--count=%s", count++);
          }
        }
      });
    }
    thread.start();
  }

  private void stopThread() {
    if (thread != null) {
      thread.interrupt();
      thread = null;
    }
    //stopForeground(STOP_FOREGROUND_REMOVE);
  }
}
