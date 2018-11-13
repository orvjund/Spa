package com.example.rat.spa.api;

import android.content.Context;

import com.example.rat.spa.model.Order;
import com.example.rat.spa.util.SpaURL;

import java.util.HashMap;

public abstract class OrderIndexRequest extends RequestBase {
  String token;
  Order order;

  public OrderIndexRequest(Context context, String token, Order order) {
    super(context);
    this.token = token;
    this.order = order;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    params.put("IDStore", Integer.toString(order.idStore));
    params.put("Name", order.name);
    params.put("Describe", order.describe);
    params.put("OnDate", order.onDate);
    params.put("IDCategory", Integer.toString(order.idCategory));
    params.put("Type", Integer.toString(order.type));
    POST(SpaURL.STORE_INDEX, params);
  }
}
