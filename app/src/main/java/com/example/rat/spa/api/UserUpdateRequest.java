package com.example.rat.spa.api;

import android.content.Context;

import com.example.rat.spa.model.UserApp;
import com.example.rat.spa.util.SpaURL;

import java.util.HashMap;

public abstract class UserUpdateRequest extends RequestBase {
  private String token;
  private UserApp userApp;
  private String oldPassword;

  public UserUpdateRequest(Context context, String token, UserApp userApp, String oldPassword) {
    super(context);
    this.token = token;
    this.userApp = userApp;
    this.oldPassword = oldPassword;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("PasswordOld", oldPassword);
    params.put("Token", token);
    params.put("ID", Integer.toString(userApp.getUserId()));
    params.put("Name", userApp.getName());
    params.put("Email", userApp.getEmail());
    params.put("Birthday", Long.toString(userApp.getBirthday().getTime()));
    params.put("ProvinceID", Integer.toString(userApp.getProvinceId()));
    params.put("DistrictID", Integer.toString(userApp.getDistrictId()));
    params.put("Address", userApp.getAddress());
    params.put("Gender", Integer.toString(userApp.getGender()));
    POST(SpaURL.USER_UPDATE, params);
  }
}
