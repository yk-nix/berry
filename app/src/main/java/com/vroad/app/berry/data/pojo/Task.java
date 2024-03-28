package com.vroad.app.berry.data.pojo;

import com.google.gson.annotations.JsonAdapter;
import com.vroad.app.berry.adapter.JsonAdapters;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class Task implements Serializable {
  public static final String TAG = "TASK";
  private String id;
  private String note;
  @JsonAdapter(JsonAdapters.LogTimestampSecondAdapter.class)
  private LocalDateTime createTime;
  private String creator;
  private List<User> handlers;
  private List<Problem> problems;
  private User publisher;

  public String getHandlerNames(String separator) {
    if (handlers == null || handlers.isEmpty())
      return "";
    return handlers.stream().filter(Objects::nonNull).map(User::getRealName).collect(Collectors.joining(separator));
  }

  public String getContent() {
    return note;
  }
}
