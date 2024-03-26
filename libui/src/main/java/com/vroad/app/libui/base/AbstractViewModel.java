package com.vroad.app.libui.base;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.lang.reflect.Constructor;

public abstract class AbstractViewModel extends ViewModel {
  public static <T extends ViewModel> T newInstance(CreationExtras extras, Class<T> cls) {
    try {
      Constructor<T> constructor = cls.getDeclaredConstructor(CreationExtras.class);
      return constructor.newInstance(extras);
    } catch (Exception e) {
      throw new IllegalArgumentException(String.format("%s has no such constructor with argument of %s", cls, CreationExtras.class));
    }
  }
}
