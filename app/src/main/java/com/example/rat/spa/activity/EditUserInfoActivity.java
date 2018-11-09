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
import com.example.rat.spa.api.UserIndexRequest;
import com.example.rat.spa.model.District;
import com.example.rat.spa.model.Province;
import com.example.rat.spa.model.UserApp;
import com.example.rat.spa.util.SharedPref;

import org.json.JSONException;

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
  int currentProvinceId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_user_info);

    getSupportActionBar().setTitle("Edit User Info");

    initViewVariables();
    initSpinners();
    loadUserInfo();
  }

  private void loadUserInfo() {
    new UserIndexRequest(this, SharedPref.getString(this, "token")) {
      @Override
      public void handleResult(String result) {
        try {
          UserApp userApp = UserApp.parseJSON(result);
          fillUserInfo(userApp);

        } catch (JSONException e) {
          Toast.makeText(EditUserInfoActivity.this, "Error: Can't load user information...!", Toast.LENGTH_SHORT).show();
          e.printStackTrace();
        }
      }

      @Override
      public void handleError(VolleyError error) {

      }
    };
  }

  private void fillUserInfo(UserApp userApp) {
    etName.setText(userApp.getName());
    etPhone.setText(userApp.getPhone());
    etAddress.setText(userApp.getAddress());
    etEmail.setText(userApp.getEmail());
    etDoB.setText(userApp.getStringDoB());

    switch (userApp.getGender()) {
      case 1:
        etGender.setText(R.string.female);
        break;
      default:
        etGender.setText(R.string.male);
    }

    setCurrentAddress();
  }

  private void setCurrentAddress() {
//    TODO: Update spinners
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
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setDistrictSpinnerAdapter(provinces.get(position).getDistricts());
            currentProvinceId = provinces.get(position).getId();
          }
          @Override
          public void onNothingSelected(AdapterView<?> parent) {
          }
        });
        setProvinceSpinnerAdapter(provinces);
      }
    };
  }

  private void setDistrictSpinnerAdapter(ArrayList<District> districts) {
    ArrayList<String> districtNames = new ArrayList<>();
    for (District district : districts) {
      districtNames.add(district.getName());
    }
    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
        R.layout.simple_spinner_item,
        districtNames);
    spinnerDistrict.setAdapter(spinnerAdapter);
    spinnerDistrict.setSelection(0, true);
  }

  private void setProvinceSpinnerAdapter(ArrayList<Province> provinces) {
    ArrayList<String> provinceNames = new ArrayList<>();
    for (Province province : provinces) {
      provinceNames.add(province.getName());
    }
    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
        R.layout.simple_spinner_item,
        provinceNames);
    spinnerCity.setAdapter(spinnerAdapter);
    spinnerCity.setSelection(0, true);
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
