package com.vroad.app.berry.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.BasicActivity;
import com.vroad.app.basic.io.UriFile;
import com.vroad.app.berry.databinding.ActivityMainBinding;

import java.util.stream.Collectors;

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
  public static ActivityResultLauncher<String> createTextDocumentLauncher;
  public static ActivityResultLauncher<String[]> openTextDocumentLauncher;
  private UriFile lastCreatedTextDocument = null;

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

    // initialize activity launchers
    initActivityLaunchers();
  }

  private void initNotificationSettings() {
    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
    startActivity(intent);
  }

  private void initActivityLaunchers() {
    createTextDocumentLauncher = registerForActivityResult(
        new ActivityResultContracts.CreateDocument("text/plain"),
        uri -> {
          lastCreatedTextDocument = new UriFile(getContentResolver(), uri);
        }
    );
    openTextDocumentLauncher = registerForActivityResult(
        new ActivityResultContracts.OpenMultipleDocuments(),
        uris -> {
          XLog.i(uris.stream().map(uri -> new UriFile(getContentResolver(), uri).getName()).collect(Collectors.toList()));
        }
    );
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
    try {
      openTextDocumentLauncher.launch(new String[] {"*/*"});

    } catch (Exception e) {
      XLog.i(e);
    }
  }

  public void onClickStopService(View view) {
    XLog.i("%s", lastCreatedTextDocument.getText());
  }
}