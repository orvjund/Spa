package com.example.rat.spa.model;

import com.example.rat.spa.util.SpaUtil;

import java.util.Date;

public class Promotion {
  String describe;
  Date startDate;
  Date endDate;

  public Promotion(String describe, Date startDate, Date endDate) {
    this.describe = describe;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getFormattedTime() {
    return SpaUtil.getFormattedDate(startDate) + " - " + SpaUtil.getFormattedDate(endDate);
  }
}
