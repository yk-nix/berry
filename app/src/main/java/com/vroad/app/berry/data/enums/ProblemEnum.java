package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProblemEnum implements ICodeEnum<Integer> {
  OTHER(0, "其他"),
  ELECTRICITY(1, "供电"),
  NETWORK(2, "网络"),
  END_DEVICE(3, "主机"),
  CAMERA(4, "摄像机"),
  READ_LIGHT_DETECTOR(5, "红灯检测器"),
  CONFIG(6, "配置"),
  PICTURE_QUALITY(7, "图像质量"),
  REOPEN(8, "其他");

  final int code;
  final String desc;
}
