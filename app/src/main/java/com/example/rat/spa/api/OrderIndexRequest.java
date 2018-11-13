package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.model.Order;
import com.example.rat.spa.util.SpaURL;
import com.example.rat.spa.util.SpaUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class OrderIndexRequest extends RequestBase {
  String token;
  int status;

  public OrderIndexRequest(Context context, String token, int status) {
    super(context);
    this.token = token;
    this.status = status;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    params.put("Status", Integer.toString(status));
    POST(SpaURL.ORDER_INDEX, params);
  }

  @Override
  public void handleResult(String result) {
    try {
      ArrayList<Order> orders = new ArrayList<>();
      JSONArray storesJSON = new JSONObject(result)
          .getJSONObject("Data")
          .getJSONArray("Orders");

      for (int i = 0; i < storesJSON.length(); i++) {
        JSONObject orderJSON = storesJSON.getJSONObject(i);

        Order order = new Order();
        order.orderId = orderJSON.getInt("ID");
        order.status = orderJSON.getInt("Status");
//        order.name = orderJSON.getString("Name");
        order.describe = orderJSON.getString("Describe");
        order.onDate = SpaUtil.getFormattedDateTime(new Date(orderJSON.getLong("OnDate")));
        order.idStore = orderJSON.getInt("StoreID");
        order.storeName = orderJSON.getString("StoreName");
        order.userName = orderJSON
            .getJSONObject("User")
            .getString("Name");
        order.address = orderJSON
            .getJSONObject("User")
            .getString("Address");
        order.phone = orderJSON
            .getJSONObject("User")
            .getString("Phone");
        order.idCategory = orderJSON
            .getJSONArray("Categories")
            .getJSONObject(0)
            .getInt("ID");
        order.categoryName = orderJSON
            .getJSONArray("Categories")
            .getJSONObject(0)
            .getString("Name");
        order.categoryDescribe = orderJSON
            .getJSONArray("Categories")
            .getJSONObject(0)
            .getString("Describe");

        orders.add(order);
      }

      handleOrders(orders);
    } catch (JSONException e) {
      e.printStackTrace();
      handleError(new VolleyError(e));
    }
  }

  protected abstract void handleOrders(ArrayList<Order> orders);
}
