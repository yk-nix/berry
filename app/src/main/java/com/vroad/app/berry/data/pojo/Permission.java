package com.vroad.app.berry.data.pojo;

import com.vroad.app.berry.data.enums.EnableStatusEnum;

import java.io.Serializable;

import lombok.Data;

@Data
public class Permission implements Serializable {
  private String id;
  private String pid;
  private String name;
  private EnableStatusEnum enable;
}
