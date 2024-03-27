package com.vroad.app.berry.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

import lombok.Getter;

public class JsonAdapters {
  abstract static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    protected abstract DateTimeFormatter getFormatter();

    protected DateTimeFormatter getFormatter(String pattern) {
      return new DateTimeFormatterBuilder().appendPattern(pattern).toFormatter();
    }

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
      out.value(Objects.requireNonNull(getFormatter()).format(value));
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
      return LocalDateTime.parse(in.nextString(), getFormatter());
    }
  }

  public static class LogTimestampSecondAdapter extends LocalDateTimeAdapter {
    @Getter(onMethod_ = {@Override})
    private final DateTimeFormatter formatter = getFormatter("yyyy-MM-dd HH:mm:ss");
  }

  public static class LogTimestampMillisecondAdapter extends LocalDateTimeAdapter {
    @Getter(onMethod_ = {@Override})
    private final DateTimeFormatter formatter = getFormatter("yyyy-MM-dd HH:mm:ss.SSS");
  }
}
