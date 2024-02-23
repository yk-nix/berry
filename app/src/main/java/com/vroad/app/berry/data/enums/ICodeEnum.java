package com.vroad.app.berry.data.enums;

import java.lang.reflect.Field;

/**
 * @author y.k.
 * @date 04/26 2023
 * @description Interface for enum of (code, description) type
 */
public interface ICodeEnum<T> {
  default Object getProperty(String name) throws IllegalAccessException {
    Field[] fields = this.getClass().getDeclaredFields();
    for (Field field : fields) {
      if (field.getName().equals(name)) {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        Object ret = field.get(this);
        field.setAccessible(access);
        return ret;
      }
    }
    return null;
  }

  /**
   * Return the code value of that enum element.
   *
   * @return T
   */
  @SuppressWarnings("unchecked")
  default T getCode() throws IllegalAccessException {
    return (T) getProperty("code");
  }

  /**
   * Return the description information corresponding with that enum element.
   *
   * @return string
   */
  default String getDesc() throws IllegalAccessException {
    return (String) getProperty("desc");
  }
}
