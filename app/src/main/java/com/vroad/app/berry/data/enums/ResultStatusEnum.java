package com.vroad.app.berry.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultStatusEnum implements ICodeEnum<Integer> {
  OK(200, "成功"),
  ERROR(500, "失败"),
  WARNING(100, "警告"),
  INVALID_TOKEN(401, "无效的身份凭证"),
  USERNAME_NOT_FOUND(402, "用户名不存在"),
  BAD_CREDENTIALS(403, "密码错误"),
  PARAMETER_REQUIRED(302, "关键参数缺失"),
  OTHER_ERROR(303, "系统内部异常"),
  DATA_LOCK_IS_USED(10001, "数据锁被占用"),
  CHANGE_PASSWORD(20001, "请修改密码");

  final int code;
  final String desc;
}