package com.vroad.app.basic.io;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.elvishew.xlog.XLog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;


@Getter
public class UriFile {
  private final ContentResolver resolver;
  private final Uri uri;
  private Map<String, Object> meta = null;

  public UriFile(ContentResolver resolver, Uri uri) {
    this.resolver = resolver;
    this.uri = uri;
    meta = getMetaInfo();
  }

  public String getText(String charsetName) {
    return getText(Charset.forName(charsetName));
  }

  public String getText(Charset cs) {
    try (InputStream in = resolver.openInputStream(uri)) {
      assert in != null;
      return new String(in.readAllBytes(), cs);
    } catch (Exception e) {
      return null;
    }
  }

  public String getText() {
    return getText(StandardCharsets.UTF_8);
  }

  private void _write(byte[] data, String mode) throws IOException {
    try (FileOutputStream out = new FileOutputStream(
        Objects.requireNonNull(resolver.openFileDescriptor(uri, mode)).getFileDescriptor())) {
      out.write(data);
    } catch (Exception ignored) {
      throw new IOException();
    }
  }

  private void append(byte[] data) throws IOException {
    _write(data, "wa");
  }

  public void append(String data, Charset cs) throws IOException {
    append(data.getBytes(cs));
  }

  public void append(String data, String charsetName) throws IOException {
    append(data.getBytes(charsetName));
  }

  public void append(String data) throws IOException {
    append(data.getBytes(StandardCharsets.UTF_8));
  }

  private void overWrite(byte[] data) throws IOException {
    _write(data, "wt");
  }

  public void write(String data, Charset cs) throws IOException {
    overWrite(data.getBytes(cs));
  }

  public void write(String data, String charsetName) throws IOException {
    overWrite(data.getBytes(charsetName));
  }

  public void write(String data) throws IOException {
    overWrite(data.getBytes(StandardCharsets.UTF_8));
  }

  public boolean exists() {
    try {
      ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
      assert pfd != null;
      pfd.close();
    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException ignored) {
    }
    return true;
  }

  private Map<String, Object> getMetaInfo() {
    try {
      Map<String, Object> meta = new HashMap<>();
      Cursor cursor = resolver.query(uri, null, null, null, null);
      assert cursor != null;
      cursor.moveToFirst();
      for (int i = 0; i < cursor.getColumnCount(); i++) {
        String k = cursor.getColumnName(i);
        Object v = null;
        switch (cursor.getType(i)) {
          case Cursor.FIELD_TYPE_INTEGER:
            v = cursor.getInt(i);
            break;
          case Cursor.FIELD_TYPE_STRING:
            v = cursor.getString(i);
            break;
          case Cursor.FIELD_TYPE_FLOAT:
            v = cursor.getFloat(i);
            break;
          case Cursor.FIELD_TYPE_BLOB:
            v = cursor.getBlob(i);
            break;
          default:
            break;
        }
        meta.put(k, v);
      }
      cursor.close();
      return meta;
    } catch (Exception e) {
      return null;
    }
  }

  public String getName() {
    if (meta != null)
      return (String) meta.get(MediaStore.MediaColumns.DISPLAY_NAME);
    return null;
  }
}
