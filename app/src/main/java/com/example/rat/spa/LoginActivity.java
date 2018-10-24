package com.example.rat.spa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    String savedToken = getSavedToken();
    String url = SpaUtil.URL.USER_LOGIN;
    HashMap<String, String> params = new HashMap<>();
    boolean updateToken = true;

    if (savedToken != null) {
      updateToken = false;
      url = SpaUtil.URL.USER_INDEX;
      params.put("Token", savedToken);
    } else {
      params.put("Username", "admin_cloud");
      params.put("Password", "123123");
    }

    final boolean finalUpdateToken = updateToken;
    new RequestHandler(this) {
      @Override
      public void handleResult(String result) {
        if (finalUpdateToken) {
          try {
            saveUserInfo(result);
          } catch (Exception e) {
            e.printStackTrace();
            return;
          }
        }

        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
        intent.putExtra("json-user-info", result);
        startActivity(intent);
        finish();
      }

      @Override
      public void handleError(VolleyError error) {
        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    }.POST(url, params);
  }

  private void saveUserInfo(String jsonStr) throws Exception{
    try {
      JSONObject json = new JSONObject(jsonStr);
      if (json.getInt("Status") != 1) throw new Exception("Request failed...!");
      String token = json.getJSONObject("Data").getString("Token");
      saveToken(token);
    } catch (JSONException e) {
      e.printStackTrace();
      throw new Exception("Parse JSON failed...!");
    }
  }

  @Nullable
  private String getSavedToken() {
    SharedPreferences prefs = getSharedPreferences("spa", Context.MODE_PRIVATE);
    return prefs.getString("token", null);
  }

  private void saveToken(String token) {
    SharedPreferences prefs = getSharedPreferences("spa", Context.MODE_PRIVATE);
    prefs.edit().putString("token", token).apply();
  }
}
