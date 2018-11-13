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
  public ArrayList<Category> categories;
  public ArrayList<Discuss> discusses;
  public ArrayList<Rating> ratings;

  public static Store parseJSON(String json) throws JSONException {
    Store store = new Store();

    store.rating = parseRating(json);

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

    store.promotions = parsePromotions(json);
    store.categories = parseCategories(json);
    store.discusses = parseDiscusses(json);
    store.ratings = parseRatings(json);

    return store;
  }

  private static ArrayList<Rating> parseRatings(String json) throws JSONException {
    ArrayList<Rating> ratings = new ArrayList<>();
    JSONArray jsonRatings = new JSONObject(json)
        .getJSONObject("Data")
        .getJSONArray("Ratings");

    for (int i = 0; i < jsonRatings.length(); i++) {
      JSONObject jsonRating = jsonRatings.getJSONObject(i);
      Rating rating = new Rating();
      rating.id = jsonRating.getInt("ID");
      rating.rate = (float) jsonRating.getDouble("Rate");
      rating.content = jsonRating.getString("Note");
      rating.created = new Date(jsonRating.getLong("Created"));
      rating.createdByName = jsonRating.getString("CreatedByName");
      ratings.add(rating);
    }

    return ratings;
  }

  private static ArrayList<Discuss> parseDiscusses(String json) throws JSONException {
    ArrayList<Discuss> discusses = new ArrayList<>();
    JSONArray jsonDiscusses = new JSONObject(json)
        .getJSONObject("Data")
        .getJSONArray("Discusses");

    for (int i = 0; i < jsonDiscusses.length(); i++) {
      JSONObject jsonDiscuss = jsonDiscusses.getJSONObject(i);
      Discuss discuss = new Discuss();
      discuss.id = jsonDiscuss.getInt("ID");
      discuss.body = jsonDiscuss.getString("Body");
      discuss.created = new Date(jsonDiscuss.getLong("Created"));
      discuss.createdByName = jsonDiscuss.getString("CreatedByName");
      discusses.add(discuss);
    }

    return discusses;
  }

  private static ArrayList<Category> parseCategories(String json) throws JSONException {
    ArrayList<Category> categories = new ArrayList<>();
    JSONArray jsonCategories = new JSONObject(json)
        .getJSONObject("Data")
        .getJSONObject("UserStore")
        .getJSONArray("Categories");

    for (int i = 0; i < jsonCategories.length(); i++) {
      JSONObject jsonCategory = jsonCategories.getJSONObject(i);
      int categoryId = jsonCategory.getInt("ID");
      String name = jsonCategory.getString("Name");
      int hour = jsonCategory.getInt("Hour");
      String describe = jsonCategory.getString("Describe");
      int minute = jsonCategory.getInt("Minute");
      float price = (float) jsonCategory.getDouble("Price");
      categories.add(new Category(categoryId, name, describe, hour, minute, price));
    }

    return categories;
  }

  private static ArrayList<Promotion> parsePromotions(String json) throws JSONException {
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

  private static float parseRating(String json) throws JSONException {
    return (float) new JSONObject(json)
        .getJSONObject("Data")
        .getDouble("Rating");
  }

  public String getFullAddress() {
    return address + ", " + districtName + " - " + provinceName;
  }
}
