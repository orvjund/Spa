package com.example.rat.spa;

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

    HashMap<String, String> params = new HashMap<>();
    params.put("Username", "admin_cloud");
    params.put("Password", "123123");

    new RequestHandler(this) {
      @Override
      public void handleResult(String result) {
        showUserInfo(result);
      }

      @Override
      public void handleError(VolleyError error) {
        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    }.POST(SpaUtil.URL.USER_LOGIN, params);
  }

  private void showUserInfo(String jsonStr) {
    try {
      JSONObject json = new JSONObject(jsonStr);
      if (json.getInt("Status") != 1) return;

      JSONObject data = json.getJSONObject("Data");
      String token = data.getString("Token");
      String name = data.getJSONObject("UserApp").getString("Name");

      ((TextView)findViewById(R.id.txt_user_info)).setText(name);
      Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    } catch (JSONException e) {
      e.printStackTrace();
      // TODO: Handle this.
    }
  }
}
