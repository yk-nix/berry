package com.vroad.app.berry.data.datasource;

import com.vroad.app.berry.net.RetrofitService;
import com.vroad.app.libui.base.GenericParameterAware;

public class AbstractDataSource<T extends RetrofitService<?>> implements GenericParameterAware {
  protected T service;

  @SuppressWarnings("unchecked")
  protected AbstractDataSource() {
    try {
      Class<T> cls = (Class<T>) getGenericParameterClassType(0);
      this.service = cls.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
