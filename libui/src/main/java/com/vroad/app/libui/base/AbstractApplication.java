package com.vroad.app.libui.base;

import android.app.Application;

import androidx.lifecycle.ViewModelStoreOwner;

public abstract class AbstractApplication extends Application {
  public static String TAG = "AbstractApplication";
  public abstract ViewModelStoreOwner getViewModelStoreOwner();
}
