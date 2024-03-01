package com.vroad.app.berry.ui.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.elvishew.xlog.XLog;
import com.vroad.app.basic.common.BasicActivity;
import com.vroad.app.basic.io.UriFile;
import com.vroad.app.berry.databinding.ActivityMainBinding;
import com.vroad.app.berry.service.TcpConnectionService;

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
  public static ActivityResultLauncher<Uri> openDirectoryLauncher;
  public static ActivityResultLauncher<String[]> requestPermissionsLauncher;

  private UriFile lastCreatedTextDocument = null;

  @Override
  protected void init() {
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
        ret -> XLog.i("--- permission request: %s", ret)
    );
  }

  public final Handler handler = new Handler(Looper.getMainLooper(), msg -> {
    XLog.i("message: %d  %s", msg.arg1, msg);
    binding.textView.setText(String.format("count=%s", msg.arg1));
    return false;
  });

  public void onClickStartService(View view) {
    try {
      XLog.i("-----------------------");
      
//      LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//          ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        requestPermissionsLauncher.launch(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
//        return;
//      }
//      locationManager.getAllProviders().forEach(provider -> {
//        XLog.i("--- %s(%s): %s",
//            provider,
//            locationManager.isProviderEnabled(provider) ? "ON" : "OFF",
//            locationManager.getProviderProperties(provider));
//      });
//      String provider = LocationManager.FUSED_PROVIDER;
//      XLog.i("--- location is %s, last-known: %s",
//          locationManager.isLocationEnabled(), locationManager.getLastKnownLocation(provider));
//      locationManager.getCurrentLocation(
//          provider,
//          null,
//          getMainExecutor(),
//          location -> XLog.i("------ %s %s", provider, location)
//      );

//      FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
//      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//          ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//        requestPermissionsLauncher.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
//      }
//      LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//      if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//      }
//      LocationRequest locationRequest = new LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, 5000).build();
//      client.requestLocationUpdates(locationRequest, getMainExecutor(), location -> {
//        XLog.i("-- %s", location);
//      });

//      CurrentLocationRequest currentLocationRequest = new CurrentLocationRequest.Builder().setPriority(PRIORITY_HIGH_ACCURACY).build();
//      client.getCurrentLocation(currentLocationRequest, null)
//          .addOnSuccessListener(location -> XLog.i("--current location: %s", location));
//      client.getLastLocation().addOnSuccessListener(location -> XLog.i("--last location: %s" , location));

//      startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//      locationManager.requestLocationUpdates(
//          provider,
//          0,
//          0L,
//          location -> XLog.i("-- %s location: %s", provider, location)
//      );
    } catch (Exception e) {
      XLog.i(e);
    }
  }

  public void onClickStopService(View view) {
    //AppUtils.exec(LoginRepository.getInstance(getApplicationContext())::logout, XLog::i);
    stopService(new Intent(this, TcpConnectionService.class));
  }
}