package com.example.rat.spa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.api.GetAddressRequest;

public class EditUserInfoActivity extends AppCompatActivity {
  EditText etName;
  EditText etPhone;
  EditText etCity;
  EditText etDistrict;
  EditText etAddress;
  EditText etEmail;
  EditText etDoB;
  EditText etGender;
  EditText etOldPassword;
  EditText etNewPassword;
  EditText etRetypePassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_user_info);

    initViewVariables();
  }

  private void initViewVariables() {

  }
}
