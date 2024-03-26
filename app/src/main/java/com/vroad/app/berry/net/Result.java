package com.vroad.app.berry.net;

import com.vroad.app.berry.data.enums.ResultStatusEnum;

import lombok.Data;

@Data
public class Result<T> {
  private ResultStatusEnum status;
  private String message;
  private T data;

  public Result() {}

  public Result(ResultStatusEnum status, String message) {
    this.status = status;
    this.message = message;
  }

  public boolean OK() {
    return status == ResultStatusEnum.OK;
  }
}