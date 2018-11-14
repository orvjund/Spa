package com.example.rat.spa.model;

public class Order {
  public int orderId = -1;
  public int idStore;
  public String userName;
  public String name;
  public String storeName;
  public String describe;
  public String onDate;
  public int idCategory;
  public String categoryName;
  public String categoryDescribe;
  public int type;
  public int status = 1;
  public String address;
  public String phone;

  public String getFullAddress() {
    return address;
  }
}
