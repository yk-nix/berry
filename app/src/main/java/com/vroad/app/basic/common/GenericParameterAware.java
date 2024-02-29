package com.vroad.app.basic.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface GenericParameterAware {
  default Class<?> getGenericParameterClassType(int index) {
    Type type = getClass().getGenericSuperclass();
    assert type != null;
    return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[index];
  }
}
