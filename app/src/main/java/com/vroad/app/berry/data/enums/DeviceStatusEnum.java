package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeviceStatusEnum implements ICodeEnum<Integer> {
  UNKNOWN(0, "未知"),
  NORMAL(1, "正常"),
  ERROR(2, "故障中"),
  NEED_CHECK(3, "需要巡检"),
  OFFLINE(4, "未在线"),
  SUSPEND(5, "挂起"),
  NOT_USED(9, "未使用");

  final int code;
  final String desc;
}
