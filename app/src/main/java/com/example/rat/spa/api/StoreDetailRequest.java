package com.example.rat.spa.api;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SpaURL;

import org.json.JSONException;

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

      Store store = Store.parseJSON(result);

      handleStore(store);

    } catch (JSONException e) {
      e.printStackTrace();
      handleError(new VolleyError(e));
    }
  }

  public abstract void handleStore(Store store);
}
