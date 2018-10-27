package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.util.SpaURL;

import java.util.HashMap;

public abstract class UserIndexRequest extends RequestBase {
  private String token;

  public UserIndexRequest(Context context, String token) {
    super(context);
    this.token = token;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    POST(SpaURL.USER_INDEX, params);
  }
}
