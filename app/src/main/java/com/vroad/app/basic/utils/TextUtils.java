package com.vroad.app.basic.utils;

public class TextUtils {
  public static final int HEXDUMP_LINE_WIDTH = 16;
  public static String hexDump(byte[] data, boolean withSuffix, boolean printText) {
    int l = 0, i, c, size = data.length;
    int lines = size / HEXDUMP_LINE_WIDTH + 1;
    int HALF_LINE_WIDTH = HEXDUMP_LINE_WIDTH / 2;
    StringBuilder s = new StringBuilder();
    String suffixFormat = size > 0xffff ? "%08x" : "%04x";
    if (size == 0)
      return s.toString();
    for (; l < lines; l++) {
      s.append(String.format(suffixFormat, l));
      for (i = 0; i < HEXDUMP_LINE_WIDTH; i++) {
        c = l * HEXDUMP_LINE_WIDTH + i;
        if (c < size)
          s.append(String.format(" %02x", data[c]));
        else
          s.append("   ");
        if (i == HALF_LINE_WIDTH - 1)
          s.append(" ");
        if (i == HEXDUMP_LINE_WIDTH - 1)
          s.append("  |");
      }
      for (i = 0; i < HEXDUMP_LINE_WIDTH; i++) {
        c = l * HEXDUMP_LINE_WIDTH + i;
        if (c < size)
          s.append(String.format("%c", data[c] > 32 && data[c] < 127 ? data[c] : '.'));
        if(i == HEXDUMP_LINE_WIDTH - 1)
          s.append("|\n");
      }
    }
    return s.toString();
  }

  public static String hexDump(byte[] data) {
    return hexDump(data, true, true);
  }
}
