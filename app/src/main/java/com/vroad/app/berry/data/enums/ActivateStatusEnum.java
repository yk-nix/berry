package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActivateStatusEnum implements ICodeEnum<Integer> {
  ACTIVATED(0, "已开通"),
  INACTIVE(1, "未开通"),
  ACTIVATING(2, "开通中"),
  DEPRECATED(3, "已拆除"),
  UNKNOWN(4, "未知");

  final Integer code;
  final String desc;
}
