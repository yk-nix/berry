package com.vroad.app.berry.data.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Role implements Serializable {
  private String id;
  private String name;
  List<Permission> permissions;
}
