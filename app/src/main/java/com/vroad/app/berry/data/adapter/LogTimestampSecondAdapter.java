package com.vroad.app.berry.data.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.vroad.app.libui.date.DateTimeFormatters;

import java.io.IOException;
import java.time.LocalDateTime;

public class LogTimestampSecondAdapter extends TypeAdapter<LocalDateTime> {
  @Override
  public void write(JsonWriter out, LocalDateTime value) throws IOException {
    out.value(DateTimeFormatters.log_timestamp_sec.format(value));
  }

  @Override
  public LocalDateTime read(JsonReader in) throws IOException {
    return LocalDateTime.parse(in.nextString(), DateTimeFormatters.log_timestamp_sec);
  }
}
