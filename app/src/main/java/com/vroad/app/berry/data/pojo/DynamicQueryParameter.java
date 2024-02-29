package com.vroad.app.berry.data.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DynamicQueryParameter extends Pagination {
  private Condition<String>[] conditions;

  public DynamicQueryParameter(Condition<String>[] conditions) {
    super();
    this.conditions = conditions;
  }
}
