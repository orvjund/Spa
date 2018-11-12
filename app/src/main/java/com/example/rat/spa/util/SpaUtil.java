package com.example.rat.spa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpaUtil {
  public static String getFormattedDate(Date date) {
    return new SimpleDateFormat("yyyy/MM/dd").format(date);
  }
}
