package com.example.rat.spa.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestBase {
  private Context context;

  RequestBase(Context context) {
    this.context = context;
  }

  void POST(String url, final HashMap<String, String> params) {
    RequestQueue queue = Volley.newRequestQueue(context);
    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            Log.i("Response", response);
            if (requestSucceeded(response)) {
              handleResult(response);
            } else {
              handleError(errorFromResponse(response));
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            handleError(error);
          }
        }
    ) {
      @Override
      protected Map<String, String> getParams() {
        return params;
      }
    };
    queue.add(postRequest);
  }

  void GET(String url, final HashMap<String, String> params) {
    RequestQueue queue = Volley.newRequestQueue(context);
    StringRequest postRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            Log.i("Response", response);
            if (requestSucceeded(response)) {
              handleResult(response);
            } else {
              handleError(errorFromResponse(response));
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            handleError(error);
          }
        }
    ) {
      @Override
      protected Map<String, String> getParams() {
        return params != null ? params : new HashMap<String, String>();
      }
    };
    queue.add(postRequest);
  }

  private VolleyError errorFromResponse(String response) {
    String message = "Unknown message...!";
    try {
      message = new JSONObject(response)
          .getJSONArray("Messages")
          .getJSONObject(0)
          .getString("text");
    } catch (Exception ignore) {
    }

    return new VolleyError(message);
  }

  public abstract void handleResult(String result);

  public abstract void handleError(VolleyError error);

  private static boolean requestSucceeded(String json) {
    JSONObject result;
    try {
      result = new JSONObject(json);
      int status = result.getInt("Status");
      return status == 1;
    } catch (JSONException e) {
      e.printStackTrace();
      return false;
    }
  }
}
