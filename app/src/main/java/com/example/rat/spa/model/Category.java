package com.example.rat.spa.model;

import com.example.rat.spa.util.SpaUtil;

import java.util.Date;

public class Category {
  int categoryId;
  String name;
  String describe;
  int hour;
  int minute;
  float price;

  public Category(int categoryId, String name, String describe, int hour, int minute, float price) {
    this.categoryId = categoryId;
    this.name = name;
    this.describe = describe;
    this.hour = hour;
    this.minute = minute;
    this.price = price;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
