package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProblemStatusEnum implements ICodeEnum<Integer> {
  UNPUBLISHED(0, "未发布"),
  CLOSED(1, "已处理"),
  SUSPEND(2, "挂起"),
  ON_GOING(3, "处理中"),
  PUBLISHED(4, "已发布");

  final int code;
  final String desc;
}
