package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnableStatusEnum implements ICodeEnum<Integer> {
  DISABLED(0, "禁用"),
  ENABLED(1, "启用");

  final Integer code;
  final String desc;
}

