package com.vroad.app.berry.ui.main;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.elvishew.xlog.XLog;
import com.vroad.app.berry.databinding.ActivityMainBinding;
import com.vroad.app.berry.ui.home.HomeActivity;
import com.vroad.app.libui.base.BasicActivity;
import com.vroad.app.libui.base.BasicViewModelFactory;
import com.vroad.app.libui.io.UriFile;

import java.util.stream.Collectors;

public class MainActivity extends BasicActivity<ActivityMainBinding, MainViewModel> {

  // Used to load the 'berry' library on application startup.
  static {
    System.loadLibrary("berry");
  }

  public MainActivity() {
    super(false, BasicViewModelFactory.class);
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
  public static ActivityResultLauncher<Uri> openDirectoryLauncher;
  public static ActivityResultLauncher<String[]> requestPermissionsLauncher;

  private UriFile lastCreatedTextDocument = null;

  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    // register notification channels
    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    nm.createNotificationChannel(new NotificationChannel(NORMAL_CHANNEL_ID, "普通通知", NotificationManager.IMPORTANCE_LOW));
    nm.createNotificationChannel(new NotificationChannel(IMPORTANT_CHANNEL_ID, "重要通知", NotificationManager.IMPORTANCE_HIGH));

    // initialize activity view
//    binding.sampleText.setText(stringFromJNI());

    if (!nm.areNotificationsEnabled()) {
      initNotificationSettings();
    }
    // initialize activity launchers
    initActivityLaunchers();

    // register observers
    registerObservers();
  }

  @Override
  public void release() {

  }

  public void registerObservers() {
    viewModel.getWorkInfo().observe(this, workInfos -> {
      if (workInfos == null || workInfos.isEmpty())
        return;
      XLog.i("--- work-info changed %s", workInfos.get(0).getState());
    });
  }

  private void initNotificationSettings() {
    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
    startActivity(intent);
  }

  private void initActivityLaunchers() {
    createTextDocumentLauncher = registerForActivityResult(
        new ActivityResultContracts.CreateDocument("text/plain"),
        uri -> lastCreatedTextDocument = new UriFile(getContentResolver(), uri)
    );
    openTextDocumentLauncher = registerForActivityResult(
        new ActivityResultContracts.OpenMultipleDocuments(),
        uris -> XLog.i(uris.stream().map(uri -> new UriFile(getContentResolver(), uri).getName()).collect(Collectors.toList()))
    );
    openDirectoryLauncher = registerForActivityResult(
        new ActivityResultContracts.OpenDocumentTree(),
        uri -> XLog.i("---- directory: %s", uri)
    );
    requestPermissionsLauncher = registerForActivityResult(
        new ActivityResultContracts.RequestMultiplePermissions(),
        permMap -> {
          if (!permMap.values().stream().allMatch((e) -> e)) {
            finish();
          }
        }
    );
  }

  public final Handler handler = new Handler(Looper.getMainLooper(), msg -> {
    XLog.i("message: %d  %s", msg.arg1, msg);
    binding.textView.setText(String.format("count=%s", msg.arg1));
    return false;
  });

  public void onClickStartService(View view) {
    try {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
          ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        requestPermissionsLauncher.launch(new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        });
        return;
      }
      XLog.i("-------- check ACCESS_FINE_LOCATION ---> OK");
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        requestPermissionsLauncher.launch(new String[]{Manifest.permission.READ_PHONE_STATE});
        return;
      }
      XLog.i("-------- check READ_PHONE_STATE ---> OK");
      SDKInitializer.initialize(getApplicationContext());
      SDKInitializer.setCoordType(CoordType.BD09LL);
      startActivity(new Intent(this, HomeActivity.class));
    } catch (Exception e) {
      XLog.e(e);
    }
  }

  public void onClickStopService(View view) {
    //AppUtils.exec(LoginRepository.getInstance(getApplicationContext())::logout, XLog::i);
  }
}