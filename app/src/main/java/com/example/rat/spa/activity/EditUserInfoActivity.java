package com.example.rat.spa.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.api.AddressesRequest;
import com.example.rat.spa.model.District;
import com.example.rat.spa.model.Province;
import com.example.rat.spa.util.SharedPref;

import java.util.ArrayList;

public class EditUserInfoActivity extends AppCompatActivity {
  EditText etName;
  EditText etPhone;
  Spinner spinnerCity;
  Spinner spinnerDistrict;
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
    getSupportActionBar().setTitle("Edit User Info");
    initViewVariables();
    initSpinners();
  }

  private void initSpinners() {
    new AddressesRequest(this) {
      @Override
      public void handleError(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(EditUserInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
      }

      @Override
      public void handleAddresses(final ArrayList<Province> provinces) {
        ArrayList<String> provinceNames = new ArrayList<>();
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<String> districNames = new ArrayList<>();
            for (District district : provinces.get(position).getDistricts()) {
              districNames.add(district.getName());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.simple_spinner_item,
                districNames);
            spinnerDistrict.setAdapter(spinnerAdapter);
            spinnerDistrict.setSelection(0, true);
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {}
        });
        for (Province province : provinces) {
          provinceNames.add(province.getName());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
            R.layout.simple_spinner_item,
            provinceNames);
        spinnerCity.setAdapter(spinnerAdapter);
        spinnerCity.setSelection(0, true);
      }
    };
  }

  private void initViewVariables() {
    etName = findViewById(R.id.et_name);
    etPhone = findViewById(R.id.et_phone);
    spinnerCity = findViewById(R.id.spinner_city);
    spinnerDistrict = findViewById(R.id.spinner_district);
    etAddress = findViewById(R.id.et_address);
    etEmail = findViewById(R.id.et_email);
    etDoB = findViewById(R.id.et_dob);
    etGender = findViewById(R.id.et_gender);
    etOldPassword = findViewById(R.id.et_old_password);
    etNewPassword = findViewById(R.id.et_new_password);
    etRetypePassword = findViewById(R.id.et_retype);
  }

  public void updateUserInfo(View view) {
    String token = SharedPref.getString(this, "token");
  }

  public void selectGender(View view) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Select gender")
        .setPositiveButton("Male", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(EditUserInfoActivity.this, Integer.toString(which), Toast.LENGTH_SHORT).show();
          }
        })
        .setNegativeButton("Female", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(EditUserInfoActivity.this, Integer.toString(which), Toast.LENGTH_SHORT).show();
          }
        });
    builder.create();
  }
}
