package com.vroad.app.berry.data.pojo;

import com.vroad.app.libui.io.exSerializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoggedInUser implements exSerializable {
  private User userInfo;
  private String token;
}