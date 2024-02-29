package com.vroad.app.berry.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicService;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpConnectionService extends BasicService {
  private Thread thread;
  private final String host = "192.168.3.241";
  private final int port = 2345;
  private Messenger messenger;
  private Socket socket;

  public TcpConnectionService() {
    super(true);
  }

  @Override
  protected IBinder getBinder(Intent intent) {
    messenger = intent.getParcelableExtra(INTENT_TAG_MESSENGER, Messenger.class);
    if (messenger != null)
      return messenger.getBinder();
    return new Binder();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    messenger = intent.getParcelableExtra(INTENT_TAG_MESSENGER, Messenger.class);
    startThread();
    return START_NOT_STICKY;
  }

  @Override
  public void onDestroy() {
    stopThread();
    super.onDestroy();
  }

  @Override
  public boolean onUnbind(Intent intent) {
    stopThread();
    return super.onUnbind(intent);
  }

  private void startThread() {
    thread = new Thread(() -> {
      try {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));
        byte[] buf = new byte[1024 * 2];
        int bytes = 0;
        XLog.i("---connection connected");
        while ((bytes = socket.getInputStream().read(buf)) >= 0) {
          Message message = Message.obtain();
          message.obj = new String(buf, 0, bytes, StandardCharsets.UTF_8);
          messenger.send(message);
        }
        XLog.i("---connection closed");
      } catch (IOException | RemoteException e) {
        if (socket != null) {
          try {
            socket.close();
            Message message = Message.obtain();
            message.what = 1;
            messenger.send(message);
          } catch (IOException | RemoteException ex) {
            throw new RuntimeException(ex);
          }
          socket = null;
        }
      }
      stopSelf();
    });
    thread.start();
  }

  private void stopThread() {
    if (thread != null) {
      thread.interrupt();
      thread = null;
    }
  }
}
