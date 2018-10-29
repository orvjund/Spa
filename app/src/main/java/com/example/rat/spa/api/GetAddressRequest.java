package com.example.rat.spa.api;

import android.content.Context;

import com.example.rat.spa.util.SpaURL;

public abstract class GetAddressRequest extends RequestBase {
  public GetAddressRequest(Context context) {
    super(context);
    request();
  }

  private void request() {
    GET(SpaURL.GET_ADDRESS, null);
  }
}
