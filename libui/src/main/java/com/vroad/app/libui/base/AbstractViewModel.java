package com.vroad.app.libui.base;


import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.lang.reflect.Constructor;

public abstract class AbstractViewModel extends ViewModel implements Observable {
  public static <T extends ViewModel> T newInstance(CreationExtras extras, Class<T> cls) {
    try {
      Constructor<T> constructor = cls.getDeclaredConstructor(CreationExtras.class);
      return constructor.newInstance(extras);
    } catch (Exception e) {
      throw new IllegalArgumentException(String.format("%s has no such constructor with argument of %s", cls, CreationExtras.class));
    }
  }

  private transient PropertyChangeRegistry mCallbacks;

  protected AbstractViewModel() {
  }

  public void addOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback callback) {
    synchronized(this) {
      if (this.mCallbacks == null) {
        this.mCallbacks = new PropertyChangeRegistry();
      }
    }

    this.mCallbacks.add(callback);
  }

  public void removeOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback callback) {
    synchronized(this) {
      if (this.mCallbacks == null) {
        return;
      }
    }

    this.mCallbacks.remove(callback);
  }

  public void notifyChange() {
    synchronized(this) {
      if (this.mCallbacks == null) {
        return;
      }
    }

    this.mCallbacks.notifyCallbacks((Observable) this, 0, (Void) null);
  }

  public void notifyPropertyChanged(int fieldId) {
    synchronized(this) {
      if (this.mCallbacks == null) {
        return;
      }
    }

    this.mCallbacks.notifyCallbacks((Observable) this, fieldId, (Void) null);
  }
}
