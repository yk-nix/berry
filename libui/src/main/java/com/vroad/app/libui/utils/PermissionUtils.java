package com.vroad.app.libui.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {
  public static void checkAndRequestPermission(Activity activity, String permission) {
    if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)
      ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
  }
}
