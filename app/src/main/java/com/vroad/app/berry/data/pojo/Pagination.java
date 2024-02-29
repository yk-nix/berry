package com.vroad.app.berry.data.pojo;

import lombok.Data;

@Data
public class Pagination {
  private boolean paging = false;
  private int pageNum = 1;
  private int pageSize = 10;
}
