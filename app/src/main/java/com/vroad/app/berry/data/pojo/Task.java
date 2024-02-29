package com.vroad.app.berry.data.pojo;

import com.google.gson.annotations.JsonAdapter;
import com.vroad.app.berry.data.adapter.LogTimestampSecondAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Task implements Serializable {
  public static final String TAG = "task";
  private String id;
  private String note;
  @JsonAdapter(LogTimestampSecondAdapter.class)
  private LocalDateTime createTime;
  private String creator;
  private List<User> handlers;
  private List<Problem> problems;
  private User publisher;
}
