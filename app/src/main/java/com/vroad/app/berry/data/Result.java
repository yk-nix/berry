package com.vroad.app.berry.data;

import lombok.Data;

@Data
public class Result<T> {
  private String status;
  private String message;
  private T data;
}