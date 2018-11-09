package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SpaURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class StoreListRequest extends RequestBase {
  String token;

  public StoreListRequest(Context context, String token) {
    super(context);
    this.token = token;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    POST(SpaURL.STORE_INDEX, params);
  }

  @Override
  public void handleResult(String result) {
    try {
      ArrayList<Store> stores = new ArrayList<>();
      JSONArray storesJSON = new JSONObject(result)
          .getJSONObject("Data")
          .getJSONArray("UserStores");

      for (int i = 0; i < storesJSON.length(); i++) {
        JSONObject storeJSON = storesJSON.getJSONObject(i);

        Store store = new Store();
        store.storeId = storeJSON.getInt("IDStore");
        store.name = storeJSON.getString("Name");
        store.describe = storeJSON.getString("Describe");
        store.rated = storeJSON.getInt("Rated");
        store.provinceName = storeJSON.getString("ProvinceName");
        store.districName = storeJSON.getString("DistrictName");
        store.address = storeJSON.getString("Address");
        store.email = storeJSON.getString("Email");
        store.phone = storeJSON.getString("Phone");

        stores.add(store);
      }

      handleStores(stores);
    } catch (JSONException e) {
      e.printStackTrace();
      handleError(new VolleyError(e));
    }
  }

  public abstract void handleStores(ArrayList<Store> stores);
}
