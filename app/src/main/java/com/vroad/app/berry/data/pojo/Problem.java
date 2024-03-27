package com.vroad.app.berry.data.pojo;

import com.google.gson.annotations.JsonAdapter;
import com.vroad.app.berry.adapter.JsonAdapters;
import com.vroad.app.berry.data.enums.ProblemEnum;
import com.vroad.app.berry.data.enums.ProblemStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Problem implements Serializable {
  private String id;
  private String deviceId;
  private String detectRuleId;
  @JsonAdapter(JsonAdapters.LogTimestampSecondAdapter.class)
  private LocalDateTime detectTime;
  private ProblemStatusEnum status;
  private ProblemEnum problem;
  @JsonAdapter(JsonAdapters.LogTimestampSecondAdapter.class)
  private LocalDateTime closeTime;
  @JsonAdapter(JsonAdapters.LogTimestampSecondAdapter.class)
  private LocalDateTime predictCloseTime;
  @JsonAdapter(JsonAdapters.LogTimestampSecondAdapter.class)
  private LocalDateTime feedbackTime;
  private String detectRule;
  private String note;
  private String feedbackerId;
  private String publisherNote;
  private String deviceIp;
  private String devicePosition;
}
