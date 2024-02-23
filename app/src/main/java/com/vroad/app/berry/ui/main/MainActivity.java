package com.vroad.app.berry.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.BasicActivity;
import com.vroad.app.berry.data.repository.LoginRepository;
import com.vroad.app.berry.databinding.ActivityMainBinding;
import com.vroad.app.berry.net.UserService;
import com.vroad.app.berry.service.MyService;
import com.vroad.app.berry.ui.login.LoginActivity;

import java.util.concurrent.Executors;

public class MainActivity extends BasicActivity<ActivityMainBinding> {

  // Used to load the 'berry' library on application startup.
  static {
    System.loadLibrary("berry");
  }

  public MainActivity() {
    super(false);
  }

  /**
   * A native method that is implemented by the 'berry' native library,
   * which is packaged with this application.
   */
  public native String stringFromJNI();

  public static String NORMAL_CHANNEL_ID = "main_normal_notification";
  public static String IMPORTANT_CHANNEL_ID = "main_important_notification";

  private void init() {
    // register notification channels
    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    nm.createNotificationChannel(new NotificationChannel(NORMAL_CHANNEL_ID, "普通通知", NotificationManager.IMPORTANCE_LOW));
    nm.createNotificationChannel(new NotificationChannel(IMPORTANT_CHANNEL_ID, "重要通知", NotificationManager.IMPORTANCE_HIGH));

    // initialize activity view
    binding.sampleText.setText(stringFromJNI());

    if (!nm.areNotificationsEnabled()) {
      initNotificationSettings();
    }
  }

  private void initNotificationSettings() {
    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
    startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    init();
  }

  public final Handler handler = new Handler(Looper.getMainLooper(), msg -> {
    XLog.i("message: %d  %s", msg.arg1, msg);
    binding.textView.setText(String.format("count=%s", msg.arg1));
    return false;
  });

  public void onClickStartService(View view) {
    LoginRepository loginRepository = LoginRepository.getInstance();
    if (!loginRepository.isLoggedIn())
      startActivity(new Intent(this, LoginActivity.class));
    XLog.i("----- logged-in-user ------\n %s", loginRepository.getUser());
  }

  public void onClickStopService(View view) {

  }
}