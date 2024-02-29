package com.vroad.app.basic.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface ViewBindingAware {
   <T extends ViewBinding> Class<T> getViewBindingClassType();
  @SuppressWarnings("unchecked")
  default <T extends ViewBinding> T getViewBinding(Callable<LayoutInflater> getLayoutInflater) throws Exception {
    Class<T> clz = getViewBindingClassType();
    Method inflate = clz.getMethod("inflate", LayoutInflater.class);
    return (T) inflate.invoke(null, getLayoutInflater.call());
  }

  @SuppressWarnings("unchecked")
  default <T extends ViewBinding> T getViewBinding(Callable<LayoutInflater> getLayoutInflater, ViewGroup container, boolean attachToParent) throws Exception {
    Class<T> clz = getViewBindingClassType();
    Method inflate = clz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
    return (T) inflate.invoke(null, getLayoutInflater.call(), container, attachToParent);
  }
}
