package com.example.rat.spa.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

  public static String getString(Activity activity, String name) {
    SharedPreferences prefs = activity.getSharedPreferences("spa", Context.MODE_PRIVATE);
    return prefs.getString(name, null);
  }

  public static String getToken(Activity activity) {
    SharedPreferences prefs = activity.getSharedPreferences("spa", Context.MODE_PRIVATE);
    return prefs.getString("token", null);
  }

  public static void putString(Activity activity, String name, String value) {
    SharedPreferences prefs = activity.getSharedPreferences("spa", Context.MODE_PRIVATE);
    prefs.edit().putString(name, value).apply();
  }

  public static void delete(Activity activity, String name) {
    SharedPreferences prefs = activity.getSharedPreferences("spa", Context.MODE_PRIVATE);
    prefs.edit().remove(name).apply();
  }
}
