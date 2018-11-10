package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SpaURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public abstract class StoreDetailRequest extends RequestBase {
  private String token;
  private int storeId;
  private Context context;

  public StoreDetailRequest(Context context, String token, int storeId) {
    super(context);
    this.context = context;
    this.token = token;
    this.storeId = storeId;
    request();
  }

  private void request() {
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    params.put("ID", Integer.toString(storeId));
    POST(SpaURL.STORE_DETAIL, params);
  }

  @Override
  public void handleResult(String result) {
    try {
      JSONObject storeJSON = new JSONObject(result)
          .getJSONObject("Data")
          .getJSONObject("UserStore");

      int storeId = storeJSON.getInt("IDStore");
      if (storeId == this.storeId) {
        Store store = new Store();
        store.storeId = storeId;
        store.name = storeJSON.getString("Name");
        store.describe = storeJSON.getString("Describe");
        store.rating = (float) new JSONObject(result)
            .getJSONObject("Data")
            .getDouble("Rating");
        store.provinceName = storeJSON.getString("ProvinceName");
        store.districtName = storeJSON.getString("DistrictName");
        store.address = storeJSON.getString("Address");
        store.email = storeJSON.getString("Email");
        store.phone = storeJSON.getString("Phone");

        handleStores(store);
      }

    } catch (JSONException e) {
      e.printStackTrace();
      handleError(new VolleyError(e));
    }
  }

  public abstract void handleStores(Store store);
}
