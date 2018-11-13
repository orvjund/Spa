package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.model.Order;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SpaURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CreateOrderRequest extends RequestBase {
  String token;
  Order order;

  public CreateOrderRequest(Context context, String token, Order order) {
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
    POST(SpaURL.ORDER_CREATE, params);
  }
}
