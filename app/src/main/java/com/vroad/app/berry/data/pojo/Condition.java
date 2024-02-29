package com.vroad.app.berry.data.pojo;

import com.vroad.app.berry.data.enums.ConditionType;
import com.vroad.app.berry.data.enums.OperatorEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Condition<T> extends Pagination {
  String name;
  T[] values;
  T value;
  ConditionType type;
  OperatorEnum operator;

  public Condition(String name, ConditionType type, OperatorEnum operator, T value) {
    init(name, type, operator);
    this.value = value;
  }

  public Condition(String name, ConditionType type, OperatorEnum operator, T[] values) {
    init(name, type, operator);
    this.values = values;
  }

  private void init(String name, ConditionType type, OperatorEnum operator) {
    this.name = name;
    this.type = type;
    this.operator = operator;
  }
}
