package com.example.rat.spa;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestHandler {
  private Context context;

  public RequestHandler(Context context) {
    this.context = context;
  }

  public void POST(String url, final HashMap<String, String> params) {
    RequestQueue queue = Volley.newRequestQueue(context);
    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
        new Response.Listener<String>()
        {
          @Override
          public void onResponse(String response) {
            Log.i("Response", response);
            handleResult(response);
          }
        },
        new Response.ErrorListener()
        {
          @Override
          public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            handleError(error);
          }
        }
    ) {
      @Override
      protected Map<String, String> getParams()
      {
        return params;
      }
    };
    queue.add(postRequest);
  }

  public abstract void handleResult(String result);
  public abstract void handleError(VolleyError error);
}
