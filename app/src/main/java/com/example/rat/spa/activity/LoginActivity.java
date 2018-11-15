package com.example.rat.spa.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.api.UserIndexRequest;
import com.example.rat.spa.api.UserLoginRequest;
import com.example.rat.spa.util.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
  private EditText etUsername;
  private EditText etPassword;
  private Button btnLogin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    getSupportActionBar().hide();
    initViewVariables();

    String savedToken = SharedPref.getToken(this);
    if (savedToken != null) {
      handleSavedToken(savedToken);
    } else {
      initLogin();
    }

    if (getIntent().getStringExtra("username") != null) {
      etUsername.setText(getIntent().getStringExtra("username"));
      etPassword.setText(getIntent().getStringExtra("password"));
      attemptLogin(btnLogin);
    }
  }

  private void initViewVariables() {
    etUsername = findViewById(R.id.et_username);
    etPassword = findViewById(R.id.et_password);
    btnLogin = findViewById(R.id.btn_login);
  }

  private void handleSavedToken(String token) {
    new UserIndexRequest(this, token) {
      @Override
      public void handleResult(String response) {
        toHomeActivity();
      }

      @Override
      public void handleError(VolleyError error) {
        error.printStackTrace();
        toastMessage("Please sign in again...!");
        initLogin();
      }
    };
  }

  private void initLogin() {
    String username = SharedPref.getString(this, "username");
    String password = SharedPref.getString(this, "password");

    if (username != null) etUsername.setText(username);
    if (password != null) etPassword.setText(password);
    btnLogin.setEnabled(true);
  }

  public void attemptLogin(View view) {
    String username = etUsername.getText().toString();
    String password = etPassword.getText().toString();

    if (validLoginInput(username, password)) {
      btnLogin.setEnabled(false);

      new UserLoginRequest(this, username, password) {
        @Override
        public void handleResult(String response) {
          handleLoginResult(response);
        }

        @Override
        public void handleError(VolleyError error) {
          error.printStackTrace();
          toastMessage("Login failed...!");
          initLogin();
        }
      };
    } else {
      toastMessage("Username & password can't be empty...!");
    }
  }

  private void handleLoginResult(String response) {
    try {
      String token = new JSONObject(response)
          .getJSONObject("Data")
          .getString("Token");
      saveLoginDetail(token);
      toHomeActivity();
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void toHomeActivity() {
    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    startActivity(intent);
    finish();
  }

  private void toastMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  private boolean validLoginInput(String username, String password) {
    return username.length() > 0 && password.length() > 0;
  }

  private void saveLoginDetail(@Nullable String token) {
    if (token != null) SharedPref.putString(this, "token", token);

    String username = etUsername.getText().toString();
    SharedPref.putString(this, "username", username);

    String password = etPassword.getText().toString();
    SharedPref.putString(this, "password", password);
  }

  public void handleRegister(View view) {
    Intent intent = new Intent(this, EditUserInfoActivity.class);
    intent.putExtra("is-register", true);
    startActivity(intent);
    finish();
  }
}
