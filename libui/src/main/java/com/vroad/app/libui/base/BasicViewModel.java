package com.vroad.app.libui.base;

import androidx.lifecycle.ViewModel;

import java.lang.reflect.Constructor;

import lombok.Getter;

public class BasicViewModel
    extends AbstractViewModel
    implements AbstractApplicationAware {
  @Getter(onMethod_ = {@Override})
  private AbstractApplication abstractApplication;

  public BasicViewModel(AbstractApplication abstractApplication) {
    this.abstractApplication = abstractApplication;
  }

  public static <T extends ViewModel> T newInstance(AbstractApplication application, Class<T> cls) {
    try {
      Constructor<T> constructor = cls.getDeclaredConstructor(AbstractApplication.class);
      return constructor.newInstance(application);
    } catch (Exception e) {
      throw new IllegalArgumentException(String.format("%s has no such constructor with argument of %s", cls, AbstractApplication.class));
    }
  }
}
