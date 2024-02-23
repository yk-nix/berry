package com.vroad.app.basic.io;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public record UriFile(ContentResolver resolver, Uri uri) {

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
}
