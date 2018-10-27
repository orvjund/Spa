package com.example.rat.spa.api;

import android.content.Context;

import com.example.rat.spa.util.SpaURL;

import java.util.HashMap;

public abstract class UserLoginRequest extends RequestBase {
  private String username;
  private String password;

  public UserLoginRequest(Context context, String username, String password) {
    super(context);
    this.username = username;
    this.password = password;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Username", username);
    params.put("Password", password);
    POST(SpaURL.USER_LOGIN, params);
  }
}
