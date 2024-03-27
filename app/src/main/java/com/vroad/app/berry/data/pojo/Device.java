package com.vroad.app.berry.data.pojo;

import com.google.gson.annotations.JsonAdapter;
import com.vroad.app.berry.adapter.JsonAdapters;
import com.vroad.app.berry.data.enums.ActivateStatusEnum;
import com.vroad.app.berry.data.enums.DeviceStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Device implements Serializable {
  public static String TAG = "DEVICE";
  private String deviceId;
  private String epDeviceId;
  private String deviceName;
  private String deviceType;
  private String ip;
  private String area;
  private String position;
  private String laneType;
  private Integer laneNo;
  private String laneName;
  private String direction;
  private String projectName;
  private String violations;
  private String camera;
  private String description;
  private String manufacturer;
  private Float latitude;
  private Float longitude;
  private DeviceStatusEnum status;
  private ActivateStatusEnum activateStatus;
  private String department;
  @JsonAdapter(JsonAdapters.LogTimestampSecondAdapter.class)
  private LocalDateTime lastCheck;
  private String problemPhoto1;
  private String problemPhoto2;
  private String problemPhoto3;
  private String photo1;
  private String photo2;
  private String photo3;
  private Integer photoNum;
  private Integer problemPhotoNum;
}
