package com.vroad.app.basic.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public interface exSerializable extends Serializable {
  /**
   * @param out where to output this object
   */
  default void saveAs(OutputStream out) {
    try (ObjectOutputStream _out = new ObjectOutputStream(out)) {
      _out.writeObject(this);
    } catch (Exception ignored) {
    }
  }

  /**
   * @param file where to output this object
   */
  default void saveAs(File file) {
    try {
      saveAs(new FileOutputStream(file));
    } catch (Exception ignored) {
    }
  }
}