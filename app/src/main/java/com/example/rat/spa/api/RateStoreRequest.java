package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.util.SharedPref;
import com.example.rat.spa.util.SpaURL;

import java.util.HashMap;

public abstract class RateStoreRequest extends RequestBase {
  String token;
  int storeId;
  float stars;
  public RateStoreRequest(Context context, String token, int storeId, float stars) {
    super(context);
    this.token = token;
    this.storeId = storeId;
    this.stars = stars;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    params.put("ID", Integer.toString(storeId));
    params.put("Rate", Float.toString(stars));
    POST(SpaURL.STORE_RATE, params);
  }
}
