package com.example.rat.spa.api;

import android.content.Context;
import android.content.Intent;

import com.example.rat.spa.model.UserApp;
import com.example.rat.spa.util.SpaURL;

import java.util.HashMap;

public abstract class RegisterRequest extends RequestBase {
  private UserApp userApp;

  public RegisterRequest(Context context, UserApp userApp) {
    super(context);
    this.userApp = userApp;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Email", userApp.getEmail());
    params.put("Birthday", userApp.getStringDoB());
    params.put("Phone", userApp.getPhone());
    params.put("Address", userApp.getAddress());
    params.put("Gender", Integer.toString(userApp.getGender()));
    params.put("Status", "1"); // FIXME: Constant status
    params.put("Username", userApp.getUserName());
    params.put("Name", userApp.getName());
    params.put("Password", userApp.getPassword());
    POST(SpaURL.USER_CREATE, params);
  }
}
