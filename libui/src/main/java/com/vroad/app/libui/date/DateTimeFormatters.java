package com.vroad.app.libui.date;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatters {
  final public static DateTimeFormatter name_timestamp_day = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd").toFormatter();
  final public static DateTimeFormatter name_timestamp_sec = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").toFormatter();
  final public static DateTimeFormatter name_timestamp_min = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmm").toFormatter();
  final public static DateTimeFormatter name_timestamp_hour = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHH").toFormatter();
  final public static DateTimeFormatter name_timestamp_msec = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
  final public static DateTimeFormatter log_timestamp_day = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
  final public static DateTimeFormatter log_timestamp_msec = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss.SSS").toFormatter();
  final public static DateTimeFormatter log_timestamp_sec = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
}
