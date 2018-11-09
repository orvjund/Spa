package com.example.rat.spa.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserApp {
  private String token;
  private String name;
  private String phone;
  private String provinceName;
  private String districtName;
  private String address;
  private String email;
  private Date birthday;
  private int gender;

  public static UserApp parseJSON(String json) throws JSONException {
    JSONObject jsonUserApp = getJSONUserApp(json);
    UserApp userApp = new UserApp();

    userApp.setName(jsonUserApp.getString("Name"));
    userApp.setPhone(jsonUserApp.getString("Phone"));
    userApp.setProvinceName(jsonUserApp.getString("ProvinceName"));
    userApp.setDistrictName(jsonUserApp.getString("DistrictName"));
    userApp.setAddress(jsonUserApp.getString("Address"));
    userApp.setEmail(jsonUserApp.getString("Email"));
    userApp.setBirthday(new Date(jsonUserApp.getLong("Birthday")));
    userApp.setGender(jsonUserApp.getInt("Gender"));

    return userApp;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getDistrictName() {
    return districtName;
  }

  public void setDistrictName(String districtName) {
    this.districtName = districtName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getBirthday() {
    return birthday;
  }

  public String getStringDoB() {
    return new SimpleDateFormat("yyyy/MM/dd").format(birthday);
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }


  static JSONObject getJSONUserApp(String json) throws JSONException {
    JSONObject data = new JSONObject(json).getJSONObject("Data");

    try {
      return data
          .getJSONArray("UserApps")
          .getJSONObject(0);
    } catch (JSONException e) {
      return data.getJSONObject("UserApp");
    }
  }
}
