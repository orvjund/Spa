package com.example.rat.spa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rat.spa.R;
import com.example.rat.spa.model.UserApp;
import com.example.rat.spa.util.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity {
  TextView txtName;
  TextView txtPhoneNumber;
  TextView txtCity;
  TextView txtDistrict;
  TextView txtAddress;
  TextView txtEmail;
  TextView txtDob;
  TextView txtGender;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);
    getSupportActionBar().setTitle("User Info List");
    initViewVariables();

    Intent intent = getIntent();
    String jsonUserInfo = intent.getStringExtra("json-user-info");
    loadUserInfo(jsonUserInfo);
  }

  private void loadUserInfo(String json) {
    try {
      UserApp userApp = UserApp.parseJSON(json);
      fillUserInfo(userApp);
    } catch (JSONException e) {
      e.printStackTrace();
      Toast.makeText(this, "Failed to get user info..!", Toast.LENGTH_SHORT).show();
    }
  }

  private void fillUserInfo(UserApp userApp) {
    txtName.setText(userApp.getName());
    txtPhoneNumber.setText(userApp.getPhone());
    txtCity.setText(userApp.getProvinceName());
    txtDistrict.setText(userApp.getDistrictName());
    txtAddress.setText(userApp.getAddress());
    txtEmail.setText(userApp.getEmail());
    txtDob.setText(userApp.getStringDoB());

    switch (userApp.getGender()) {
      case 1:
        txtGender.setText(R.string.female);
        break;
      default:
        txtGender.setText(R.string.male);
    }
  }

  private void initViewVariables() {
    txtName = findViewById(R.id.txt_name);
    txtPhoneNumber = findViewById(R.id.txt_phone);
    txtCity = findViewById(R.id.txt_city);
    txtDistrict = findViewById(R.id.txt_district);
    txtAddress = findViewById(R.id.txt_address);
    txtEmail = findViewById(R.id.txt_email);
    txtDob = findViewById(R.id.txt_dob);
    txtGender = findViewById(R.id.txt_gender);
  }

  public void toEditUserInfo(View view) {
    startActivity(new Intent(this, EditUserInfoActivity.class));
    finish();
  }

  public void logOut(View view) {
    SharedPref.delete(this, "token");
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }
}
