package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OperatorEnum implements ICodeEnum<String> {
  BETWEEN("BETWEEN", "在...之间"),
  LIKE("LIKE", "包含"),
  EQ("=", "等于"),
  GT(">", "大于"),
  GE(">=", "大于或等于"),
  LT("<", "小于"),
  LE("<=", "小于或等于"),
  NE("!=", "不等于");

  final String code;
  final String desc;
}
