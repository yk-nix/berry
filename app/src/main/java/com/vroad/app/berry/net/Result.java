package com.vroad.app.berry.net;

import com.vroad.app.berry.data.enums.ResultStatusEnum;

import lombok.Data;

@Data
public class Result<T> {
  private ResultStatusEnum status;
  private String message;
  private T data;

  public boolean OK() {
    return status == ResultStatusEnum.OK;
  }
}