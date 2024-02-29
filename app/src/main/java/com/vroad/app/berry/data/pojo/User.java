package com.vroad.app.berry.data.pojo;

import com.vroad.app.berry.data.enums.EnableStatusEnum;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class User implements Serializable {
  private String id;
  private String name;
  private String password;
  private String realName;
  private EnableStatusEnum status;
  private String phone;
  private String email;
  private String description;
  List<Role> roles;

  public User() {
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }
}
