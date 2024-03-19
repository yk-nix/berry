package com.vroad.app.libui.utils;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class ReflectionUtil {
  @Nullable
  @SuppressWarnings("unchecked")
  public static <T> T getDeclaredField(Class<?> cls, Object obj, String fieldName) {
    try {
      T ret = null;
      Field field = cls.getDeclaredField(fieldName);
      try {
        ret = (T) field.get(obj);
      } catch (IllegalAccessException e) {
        field.setAccessible(true);
        ret = (T) field.get(obj);
        field.setAccessible(false);
      }
      return ret;
    } catch (Exception e) {
      return null;
    }
  }

  @Nullable
  public static <T> T getDeclaredField(String fullClassName, Object obj, String fieldName) {
    ;
    try {
      return getDeclaredField(Class.forName(fullClassName), obj, fieldName);
    } catch (Exception e) {
      return null;
    }
  }

  @Nullable
  public static <T> T getDeclaredField(Object obj, String fieldName) {
    return getDeclaredField(obj.getClass(), obj, fieldName);
  }

}
