package com.vroad.app.basic.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtils {
  @SuppressWarnings("unchecked")
  public static <T extends Serializable> T readAs(InputStream in) {
    try (ObjectInputStream _in = new ObjectInputStream(in)) {
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

  public static <T extends Serializable> T readAs(byte[] data) {
    try {
      return readAs(new ByteArrayInputStream(data));
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

  public static boolean deleteDir(File dir) {
    boolean ret = true;
    try {
      if (dir.isDirectory()) {
        for (File e : Objects.requireNonNull(dir.listFiles())) {
          if (!deleteDir(e))
            ret = false;
        }
      }
      if (!dir.delete())
        ret = false;
      return ret;
    } catch (Exception e) {
      return false;
    }
  }

  public static String getText(File file, Charset cs) {
    try (FileInputStream in = new FileInputStream(file)) {
      return new String(in.readAllBytes(), cs);
    } catch (Exception e) {
      return null;
    }
  }

  public static String getText(File file, String charsetName) {
    return getText(file, Charset.forName(charsetName));
  }

  public static String getText(File file) {
    return getText(file, StandardCharsets.UTF_8);
  }

  public static void write(File file, byte[] data, boolean append) {
    try (FileOutputStream out = new FileOutputStream(file, append)) {
      out.write(data);
    } catch (Exception ignored) {
    }
  }

  public static void write(File file, String data, Charset cs) {
    write(file, data.getBytes(cs), false);
  }

  public static void write(File file, String data, String charsetName) {
    write(file, data.getBytes(Charset.forName(charsetName)), false);
  }

  public static void write(File file, String data) {
    write(file, data, StandardCharsets.UTF_8);
  }

  public static void append(File file, String data, Charset cs) {
    write(file, data.getBytes(cs), true);
  }

  public static void append(File file, String data, String charsetName) {
    write(file, data.getBytes(Charset.forName(charsetName)), true);
  }

  public static void append(File file, String data) {
    write(file, data.getBytes(StandardCharsets.UTF_8), true);
  }
}
