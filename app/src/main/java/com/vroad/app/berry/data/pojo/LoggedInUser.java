package com.vroad.app.berry.data.pojo;

import com.vroad.app.libui.io.Persistable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoggedInUser implements Serializable, Persistable {
  private User userInfo;
  private String token;
}