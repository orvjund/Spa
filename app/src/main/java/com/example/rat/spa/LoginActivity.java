package com.example.rat.spa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
  private EditText etUsername;
  private EditText etPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    initViewVariables();

    String savedToken = getSavedToken();

    if (savedToken != null) {
      handleSavedToken(savedToken);
    } else {
      initLogin();
    }
  }

  private void initViewVariables() {
    etUsername = findViewById(R.id.et_username);
    etPassword = findViewById(R.id.et_password);
  }

  private void handleSavedToken(String token) {
    String url = SpaUtil.URL.USER_INDEX;
    HashMap<String, String> params = new HashMap<>();
    params.put("Token", token);
    new RequestHandler(this) {
      @Override
      public void handleResult(String result) {
        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
        intent.putExtra("json-user-info", result);
        startActivity(intent);
        finish();
      }

      @Override
      public void handleError(VolleyError error) {
        initLogin();
      }
    }.POST(url, params);
  }

  private void initLogin() {
    SharedPreferences prefs = getSharedPreferences("spa", Context.MODE_PRIVATE);
    String username = prefs.getString("username", "");
    String password = prefs.getString("password", "");

    etUsername.setText(username);
    etPassword.setText(password);
  }

  @Nullable
  private String getSavedToken() {
    SharedPreferences prefs = getSharedPreferences("spa", Context.MODE_PRIVATE);
    return prefs.getString("token", null);
  }

  private void saveLoginDetail(String token) {
    String username = etUsername.getText().toString();
    String password = etPassword.getText().toString();
    SharedPreferences prefs = getSharedPreferences("spa", Context.MODE_PRIVATE);
    prefs.edit().putString("token", token).apply();
    prefs.edit().putString("username", username).apply();
    prefs.edit().putString("password", password).apply();
  }

  public void attemptLogin(View view) {
    String username = etUsername.getText().toString();
    String password = etPassword.getText().toString();
    String url = SpaUtil.URL.USER_LOGIN;
    HashMap<String, String> params = new HashMap<>();
    params.put("Username", username);
    params.put("Password", password);
    new RequestHandler(this) {
      @Override
      public void handleResult(String json) {
        handleLoginResult(json);
      }

      @Override
      public void handleError(VolleyError error) {
        Toast.makeText(LoginActivity.this, "Login failed...!", Toast.LENGTH_SHORT).show();
      }
    }.POST(url, params);

  }

  private void handleLoginResult(String json) {
    try {
      JSONObject result = new JSONObject(json);
      int status = result.getInt("Status");
      if (status != 1) throw new Exception();

      Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
      intent.putExtra("json-user-info", json);
      startActivity(intent);

      String token = result.getJSONObject("Data").getString("Token");
      saveLoginDetail(token);
      finish();
    } catch (Exception e) {
      e.printStackTrace();
      Toast.makeText(this, "Login failed...!", Toast.LENGTH_SHORT).show();
    }
  }
}
