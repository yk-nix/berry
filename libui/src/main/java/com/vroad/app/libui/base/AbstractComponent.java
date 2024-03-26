package com.vroad.app.libui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;

public interface AbstractComponent extends LifecycleEventPrintable, AbstractApplicationAware {
  <T extends ViewBinding> T getViewBinding();

  <T extends AbstractViewModel> T getViewModel();

  void init(@Nullable Bundle savedInstanceState);

  void release();

  @SuppressWarnings("unchecked")
  @Nullable
  default <T extends ViewBinding> T setContentView(@NonNull AbstractActivity activity,
                                                   @NonNull Class<? extends ViewBinding> viewBindingClass) {
    try {
      Method inflate = viewBindingClass.getMethod("inflate", LayoutInflater.class);
      T binding = (T) inflate.invoke(null, activity.getLayoutInflater());
      assert binding != null;
      activity.setContentView(binding.getRoot());
      return binding;
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Nullable
  default <T extends ViewBinding> T setContentView(@NonNull LayoutInflater inflater,
                                                   @NonNull Class<? extends ViewBinding> viewBindingClass) {
    try {
      Method inflate = viewBindingClass.getMethod("inflate", LayoutInflater.class);
      return (T) inflate.invoke(null, inflater);
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Nullable
  default <T extends ViewBinding> T setContentView(@NonNull AbstractActivity activity,
                                                   @NonNull @LayoutRes Integer layout) {
    try {
      return (T) DataBindingUtil.setContentView(activity, layout);
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Nullable
  default <T extends ViewBinding> T setContentView(@NonNull @LayoutRes Integer layout,
                                                   @NonNull LayoutInflater inflater,
                                                   @Nullable ViewGroup container) {
    try {
      return (T) DataBindingUtil.inflate(inflater, layout, container, false);
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Nullable
  default <T extends AbstractViewModel> T createViewModel(@NonNull AbstractActivity activity,
                                                          @NonNull Class<? extends AbstractViewModel> viewModelClass) {
    try {
      AbstractApplication app = getAbstractApplication();
      ViewModelStoreOwner viewModelStoreOwner = app.getViewModelStoreOwner();
      return (T) new ViewModelProvider(viewModelStoreOwner).get(viewModelClass);
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Nullable
  default <T extends AbstractViewModel> T createViewModel(@NonNull AbstractActivity activity,
                                                          @NonNull Class<? extends AbstractViewModel> viewModelClass,
                                                          @NonNull Class<? extends AbstractViewModelFactory> viewModelFactoryClass,
                                                          @Nullable CreationExtras extras) {
    try {
      AbstractApplication app = getAbstractApplication();
      ViewModelStoreOwner viewModelStoreOwner = app.getViewModelStoreOwner();
      if (extras == null) {
        extras = activity.getDefaultViewModelCreationExtras();
        ((MutableCreationExtras) extras).set(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY, viewModelStoreOwner);
      }
      ((MutableCreationExtras) extras).set(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY, getAbstractApplication());
      return (T) new ViewModelProvider(viewModelStoreOwner.getViewModelStore(), viewModelFactoryClass.newInstance(), extras).get(viewModelClass);
    } catch (Exception e) {
      return null;
    }
  }
}
