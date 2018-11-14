package com.example.rat.spa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpaUtil {
  public static String getFormattedDate(Date date) {
    return new SimpleDateFormat("dd-MM-yyyy").format(date);
  }

//  public static String getFormattedDateTime(Date date) {
//    return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(date);
//  }
}
