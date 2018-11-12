package com.example.rat.spa.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Store {
  public int storeId;
  public String name;
  public String describe;
  public String provinceName;
  public String districtName;
  public String address;
  public String email;
  public String phone;
  public float rating;
  public ArrayList<Promotion> promotions;

  public static Store parseJSON(String json) throws JSONException {
    Store store = new Store();

    store.rating = getRating(json);

    JSONObject storeJSON = new JSONObject(json)
        .getJSONObject("Data")
        .getJSONObject("UserStore");
    store.storeId = storeJSON.getInt("IDStore");
    store.name = storeJSON.getString("Name");
    store.describe = storeJSON.getString("Describe");
    store.provinceName = storeJSON.getString("ProvinceName");
    store.districtName = storeJSON.getString("DistrictName");
    store.address = storeJSON.getString("Address");
    store.email = storeJSON.getString("Email");
    store.phone = storeJSON.getString("Phone");

    store.promotions = getPromotions(json);

    return store;
  }

  private static ArrayList<Promotion> getPromotions(String json) throws JSONException {
    ArrayList<Promotion> promotions = new ArrayList<>();
    JSONArray jsonPromotions = new JSONObject(json)
        .getJSONObject("Data")
        .getJSONArray("Promotions");

    for (int i = 0; i < jsonPromotions.length(); i++) {
      JSONObject promotion = jsonPromotions.getJSONObject(i);
      String describe = promotion.getString("Describe");
      Date startDate = new Date(promotion.getLong("StartDate"));
      Date endDate = new Date(promotion.getLong("EndDate"));
      promotions.add(new Promotion(describe, startDate, endDate));
    }

    return promotions;
  }

  private static float getRating(String json) throws JSONException {
    return (float) new JSONObject(json)
        .getJSONObject("Data")
        .getDouble("Rating");
  }
}
