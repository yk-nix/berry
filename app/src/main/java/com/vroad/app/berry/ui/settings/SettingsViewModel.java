package com.vroad.app.berry.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vroad.app.libui.base.AbstractViewModel;

public class SettingsViewModel extends AbstractViewModel {

  private final MutableLiveData<String> mText;

  public SettingsViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is notifications fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}