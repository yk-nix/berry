package com.vroad.app.libui.io;

import androidx.annotation.Nullable;

import com.elvishew.xlog.XLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface Persistable extends Serializable {
  /**
   * @param out where to output this object
   */
  default void toOutputStream(OutputStream out) {
    try (ObjectOutputStream _out = new ObjectOutputStream(out)) {
      _out.writeObject(this);
    } catch (Exception e) {
      XLog.e(e);
    }
  }

  /**
   * @param file where to output this object
   */
  default void toFile(File file) {
    try {
      toOutputStream(new FileOutputStream(file));
    } catch (Exception e) {
      XLog.e(e);
    }
  }

  @Nullable
  default byte[] toBytes() {
    ByteArrayOutputStream cache = new ByteArrayOutputStream();
    try (ObjectOutputStream out = new ObjectOutputStream(cache)) {
      out.writeObject(this);
      out.flush();
      return cache.toByteArray();
    } catch (Exception e) {
      XLog.e(e);
      return null;
    }
  }
}
