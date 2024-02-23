package com.vroad.app.basic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class FileUtils {
  @SuppressWarnings("unchecked")
  public static <T extends Serializable> T readAs(InputStream in) {
    try (ObjectInputStream _in = new ObjectInputStream(in)){
      return (T) _in.readObject();
    } catch (Exception e) {
      return null;
    }
  }

  public static <T extends Serializable> T readAs(File file) {
    try {
      return readAs(new FileInputStream(file));
    } catch (Exception e) {
      return null;
    }
  }

  public static <T extends Serializable> void saveTo(T o, OutputStream out) {
    try (ObjectOutputStream _out = new ObjectOutputStream(out)) {
      _out.writeObject(o);
    } catch (Exception ignored) {
    }
  }

  public static <T extends Serializable> void saveTo(T o, File file) {
    try {
      saveTo(o, new FileOutputStream(file));
    } catch (Exception ignored) {
    }
  }
}
