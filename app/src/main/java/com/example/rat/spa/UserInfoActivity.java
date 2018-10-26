package com.example.rat.spa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

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

    initViewVariables();

    Intent intent = getIntent();
    String jsonUserInfo = intent.getStringExtra("json-user-info");
    loadUserInfo(jsonUserInfo);

  }

  private void loadUserInfo(String json) {
    try {
      JSONObject data = new JSONObject(json)
          .getJSONObject("Data");
      JSONObject userApp;

      try {
        userApp = data.getJSONArray("UserApps").getJSONObject(0);
      } catch (JSONException e) {
        userApp = data.getJSONObject("UserApp");
      }

      txtName.setText(userApp.getString("Name"));
      txtPhoneNumber.setText(userApp.getString("Phone"));
      txtCity.setText(userApp.getString("ProvinceName"));
      txtDistrict.setText(userApp.getString("DistrictName"));
      txtAddress.setText(userApp.getString("Address"));
      txtEmail.setText(userApp.getString("Email"));

      long intDoB = userApp.getLong("Birthday");
      Date date = new Date(intDoB);
      txtDob.setText(date.toString());
      switch (userApp.getInt("Gender")) {
        case 1:
          txtGender.setText(R.string.female);
          break;
        default:
          txtGender.setText(R.string.male);
      }

    } catch (JSONException e) {
      e.printStackTrace();
      Toast.makeText(this, "Failed to get user info..!", Toast.LENGTH_SHORT).show();
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


}
