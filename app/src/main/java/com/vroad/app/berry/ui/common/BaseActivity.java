package com.vroad.app.berry.ui.common;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.elvishew.xlog.XLog;
import com.vroad.app.libui.base.AbstractViewModel;
import com.vroad.app.libui.base.BasicActivity;
import com.vroad.app.libui.base.BasicFragment;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public abstract class BaseActivity<B extends ViewBinding, M extends AbstractViewModel>
    extends BasicActivity<B, M> {
  public BaseActivity(boolean enablePrintLifecycleEvent, @LayoutRes int layout) {
    super(enablePrintLifecycleEvent, layout);
  }

  private Method getMethod(Object obj, String methodName) {
    for (Method method : obj.getClass().getMethods()) {
      if (method.getName().equals(methodName))
        return method;
    }
    return null;
  }

  @CallSuper
  @Override
  public void init(@Nullable Bundle savedInstanceState) {
    super.init(savedInstanceState);
    try {
      Arrays.stream(binding.getClass().getMethods()).forEach(XLog::i);
      Objects.requireNonNull(getMethod(binding, "setViewModel")).invoke(binding, viewModel);
      Objects.requireNonNull(getMethod(binding, "setHandlers")).invoke(binding, new Handlers());
    } catch (Exception ignored) {
    }
  }
}
