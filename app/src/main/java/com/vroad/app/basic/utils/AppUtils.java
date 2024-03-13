package com.vroad.app.basic.utils;

import com.elvishew.xlog.XLog;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class AppUtils {

  public static void exec(Runnable action) {
    (new Thread(() -> {
      try {
        action.run();
      } catch (Exception e) {
        XLog.e(e);
      }
    })).start();
  }


  public static <R> void exec(Callable<R> action) {
    (new Thread(() -> {
      try {
        action.call();
      } catch (Exception e) {
        XLog.e(e);
      }
    })).start();
  }

  public static <R> void exec(Callable<R> action, Consumer<R> after) {
    (new Thread(() -> {
      try {
        after.accept(action.call());
      } catch (Exception e) {
        XLog.e(e);
      }
    })).start();
  }

  public static <T, R> void exec(Function<T, R> action, T t, Consumer<R> after) {
    (new Thread(() -> {
      try {
        after.accept(action.apply(t));
      } catch (Exception e) {
        XLog.d(e);
      }
    })).start();
  }

  public static <T, U, R> void exec(BiFunction<T, U, R> action, T t, U u, Consumer<R> after) {
    (new Thread(() -> {
      try {
        after.accept(action.apply(t, u));
      } catch (Exception e) {
        XLog.d(e);
      }
    })).start();
  }
}
