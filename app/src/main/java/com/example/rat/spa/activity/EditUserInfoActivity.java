package com.example.rat.spa.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.api.AddressesRequest;
import com.example.rat.spa.api.RegisterRequest;
import com.example.rat.spa.api.UserIndexRequest;
import com.example.rat.spa.api.UserUpdateRequest;
import com.example.rat.spa.model.District;
import com.example.rat.spa.model.Province;
import com.example.rat.spa.model.UserApp;
import com.example.rat.spa.util.SharedPref;
import com.example.rat.spa.util.SpaUtil;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditUserInfoActivity extends AppCompatActivity {
  Button btnUpdate;
  EditText etName;
  EditText etPhone;
  Spinner spinnerCity;
  Spinner spinnerDistrict;
  EditText etAddress;
  EditText etEmail;
  TextView txtDoB;
  TextView txtGender;
  TextView labelOldPassword;
  EditText etOldPassword;
  EditText etNewPassword;
  EditText etRetypePassword;
  CheckBox cbChangePassword;
  int currentProvinceId = -1;
  int currentDistrictId = -1;
  int userId;
  boolean isRegister;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_user_info);

    Intent intent = getIntent();
    isRegister = intent.getBooleanExtra("is-register", false);

    initViewVariables();
    initSpinners();
    initDoBSelection();

    if (isRegister) {
      getSupportActionBar().setTitle("Register");
      initRegister();
    } else {
      getSupportActionBar().setTitle("Edit User Info");
      initCheckbox();
      loadUserInfo();
    }
  }

  private void initDoBSelection() {
    txtDoB.setText(SpaUtil.getFormattedDate(new Date()));
    txtDoB.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            txtDoB.setText(SpaUtil.getFormattedDate(calendar.getTime()));
          }
        };
        new DatePickerDialog(EditUserInfoActivity.this, date, calendar
            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });
  }

  private void initRegister() {
    btnUpdate.setText("Register");
    etOldPassword.setInputType(InputType.TYPE_CLASS_TEXT);
    etOldPassword.setEnabled(true);
    labelOldPassword.setText("Username");
    cbChangePassword.setVisibility(View.GONE);
  }

  private void initCheckbox() {
    cbChangePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        etNewPassword.setEnabled(isChecked);
        etRetypePassword.setEnabled(isChecked);
      }
    });
  }

  private void loadUserInfo() {
    new UserIndexRequest(this, SharedPref.getToken(this)) {
      @Override
      public void handleResult(String result) {
        try {
          UserApp userApp = UserApp.parseJSON(result);
          userId = userApp.getUserId();
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
    txtDoB.setText(userApp.getStringDoB());

    switch (userApp.getGender()) {
      case 1:
        txtGender.setText(R.string.female);
        break;
      default:
        txtGender.setText(R.string.male);
    }
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

  private void setDistrictSpinnerAdapter(final ArrayList<District> districts) {
    ArrayList<String> districtNames = new ArrayList<>();
    for (District district : districts) {
      districtNames.add(district.getName());
    }
    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),
        R.layout.simple_spinner_item,
        districtNames);
    spinnerDistrict.setAdapter(spinnerAdapter);
    spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currentDistrictId = districts.get(position).getId();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
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
    labelOldPassword = findViewById(R.id.label_old_password);
    btnUpdate = findViewById(R.id.btn_update);
    etName = findViewById(R.id.et_name);
    etPhone = findViewById(R.id.et_phone);
    spinnerCity = findViewById(R.id.spinner_city);
    spinnerDistrict = findViewById(R.id.spinner_district);
    etAddress = findViewById(R.id.et_address);
    etEmail = findViewById(R.id.et_email);
    txtDoB = findViewById(R.id.et_dob);
    txtGender = findViewById(R.id.et_gender);
    etOldPassword = findViewById(R.id.et_old_password);
    etNewPassword = findViewById(R.id.et_new_password);
    etRetypePassword = findViewById(R.id.et_retype);
    cbChangePassword = findViewById(R.id.cb_change_password);
  }

  public void updateUserInfo(View view) {
    btnUpdate.setEnabled(false);
    if (isRegister) {
      handleRegister();
    } else {
      handleEditUserInfo();
    }
  }

  private void handleEditUserInfo() {
    UserApp userApp = new UserApp();
    String oldPassword = etOldPassword.getText().toString();
    if (oldPassword.length() == 0) {
      Toast.makeText(this, "Enter current password...!", Toast.LENGTH_SHORT).show();
      btnUpdate.setEnabled(true);
      return;
    }
    userApp.setName(etName.getText().toString());
    userApp.setPhone(etPhone.getText().toString());
    userApp.setProvinceId(currentProvinceId);
    userApp.setDistrictId(currentDistrictId);
    userApp.setAddress(etAddress.getText().toString());
    userApp.setEmail(etEmail.getText().toString());
    try {
      userApp.setBirthday(SpaUtil.parseFormattedDate(txtDoB.getText().toString()));
    } catch (ParseException e) {
      e.printStackTrace();
      Toast.makeText(this, "Invalid birthday...!", Toast.LENGTH_SHORT).show();
      btnUpdate.setEnabled(true);
      return;
    }
    userApp.setGender(txtGender.getText().toString().equals("Make") ? 1 : 2);

    new UserUpdateRequest(this, SharedPref.getToken(this), userApp, oldPassword) {
      @Override
      public void handleResult(String result) {
        Toast.makeText(EditUserInfoActivity.this, "Updated...!", Toast.LENGTH_SHORT).show();
        startUserInfoActivity();
      }

      @Override
      public void handleError(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(EditUserInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        btnUpdate.setEnabled(true);
      }
    };
  }

  private void handleRegister() {
    UserApp userApp = new UserApp();
    userApp.setUserName(etOldPassword.getText().toString());
    String newPassword = etNewPassword.getText().toString();
    userApp.setPassword(newPassword);
    userApp.setName(etName.getText().toString());
    userApp.setPhone(etPhone.getText().toString());
    userApp.setProvinceId(currentProvinceId);
    userApp.setDistrictId(currentDistrictId);
    userApp.setAddress(etAddress.getText().toString());
    userApp.setEmail(etEmail.getText().toString());
    try {
      userApp.setBirthday(SpaUtil.parseFormattedDate(txtDoB.getText().toString()));
    } catch (ParseException e) {
      e.printStackTrace();
      Toast.makeText(this, "Invalid birthday...!", Toast.LENGTH_SHORT).show();
      btnUpdate.setEnabled(true);
      return;
    }
    userApp.setGender(txtGender.getText().toString().equals("Make") ? 1 : 2);
    if (!validUserApp(userApp)) {
      btnUpdate.setEnabled(true);
      return;
    }
    new RegisterRequest(this, userApp) {
      @Override
      public void handleResult(String result) {
        Toast.makeText(EditUserInfoActivity.this, "Registered...!", Toast.LENGTH_SHORT).show();
        startLoginActivity();
      }

      @Override
      public void handleError(VolleyError error) {
        btnUpdate.setEnabled(true);
        error.printStackTrace();
        Toast.makeText(EditUserInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    };
  }

  private boolean validUserApp(UserApp userApp) {
    if (userApp.getUserName().length() == 0) {
      Toast.makeText(this, "Enter username...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (userApp.getPassword().length() == 0) {
      Toast.makeText(this, "Enter password...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (!userApp.getPassword().equals(etRetypePassword.getText().toString())) {
      Toast.makeText(this, "Passwords must matches...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (userApp.getName().length() == 0) {
      Toast.makeText(this, "Enter name...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (userApp.getPhone().length() == 0) {
      Toast.makeText(this, "Enter phone number...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (userApp.getAddress().length() == 0) {
      Toast.makeText(this, "Enter address...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (userApp.getEmail().length() == 0) {
      Toast.makeText(this, "Enter email...!", Toast.LENGTH_SHORT).show();
      return false;
    }

    return true;
  }

  private void startLoginActivity() {
    Intent intent = new Intent(this, LoginActivity.class);
    intent.putExtra("username", etOldPassword.getText().toString());
    intent.putExtra("password", etNewPassword.getText().toString());
    startActivity(intent);
    finish();
  }

  private void startUserInfoActivity() {
    startActivity(new Intent(this, UserInfoActivity.class));
    finish();
  }

  public void selectGender(View view) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Select gender")
        .setPositiveButton("Male", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            txtGender.setText("Male");
          }
        })
        .setNegativeButton("Female", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            txtGender.setText("Female");
          }
        });
    builder.create().show();
  }
}
