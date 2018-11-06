package com.example.rat.spa.model;

import java.util.ArrayList;

public class Province {
  int id;
  String code;
  String name;
  ArrayList<District> districts;

  public Province(int id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
    this.districts = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addDistrict(District district) {
    districts.add(district);
  }

  public ArrayList<District> getDistricts() {
    return districts;
  }
}
