package com.example.rat.spa.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.adapter.StoreServicesAdapter;
import com.example.rat.spa.api.CreateOrderRequest;
import com.example.rat.spa.api.StoreDetailRequest;
import com.example.rat.spa.model.Order;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SharedPref;
import com.example.rat.spa.util.SpaUtil;

import java.util.Calendar;
import java.util.Date;

public class BookingActivity extends AppCompatActivity {
  int storeId;
  TextView txtStoreName;
  TextView txtStoreInfo;
  TextView txtBookingTime;
  EditText etDescription;
  EditText etOrderName;
  ListView lvStoreServices;
  private int idCategory = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking);
    getSupportActionBar().setTitle("BookingActivity");

    storeId = getIntent().getIntExtra("store-id", -1);
    if (storeId == -1) {
      finish();
      return;
    }

    initViewVariables();
    initBookingTimeSelection();
    loadStoreDetail();
  }

  private void initBookingTimeSelection() {
    final Calendar myCalendar = Calendar.getInstance();
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      }
    };
    txtBookingTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new DatePickerDialog(BookingActivity.this, date, myCalendar
            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });
  }

  private void initViewVariables() {
    txtStoreName = findViewById(R.id.txt_store_name);
    txtStoreInfo = findViewById(R.id.txt_store_info);
    txtBookingTime = findViewById(R.id.txt_booking_time);
    lvStoreServices = findViewById(R.id.lv_store_services);
    etDescription = findViewById(R.id.et_description);
    etOrderName = findViewById(R.id.et_order_name);
  }

  private void loadStoreDetail() {
    new StoreDetailRequest(this, SharedPref.getToken(this), this.storeId) {
      @Override
      public void handleError(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(BookingActivity.this, "Error: Can't load store details...!", Toast.LENGTH_SHORT).show();
        finish();
      }

      @Override
      public void handleStore(Store store) {
        fillDetailToViews(store);
      }
    };
  }

  private void fillDetailToViews(Store store) {
    txtBookingTime.setText(SpaUtil.getFormattedDate(new Date()));
    txtStoreName.setText(store.name);
    txtStoreInfo.setText(store.describe);
    lvStoreServices.setAdapter(new StoreServicesAdapter(this, store) {
      @Override
      public void updateSelected(boolean isChecked, int categoryId) {
        if (isChecked) {
          BookingActivity.this.idCategory = categoryId;
        } else {
          if (BookingActivity.this.idCategory == categoryId) {
            BookingActivity.this.idCategory = -1;
          }
        }
      }
    });
  }

  public void createOrder(View view) {
    Order order = new Order();
    order.idStore = storeId;
    order.name = etOrderName.getText().toString();
    order.describe = etDescription.getText().toString();
    order.onDate = txtBookingTime.getText().toString();
    order.type = 1; // FIXME: Constant order type
    order.idCategory = idCategory;
    if (handleVerify(order)) {
      new CreateOrderRequest(this, SharedPref.getToken(this), order) {
        @Override
        public void handleResult(String result) {
          Toast.makeText(BookingActivity.this, "Order succeed...!", Toast.LENGTH_SHORT).show();
          finish();
        }

        @Override
        public void handleError(VolleyError error) {
          Toast.makeText(BookingActivity.this, "Failed to create order...!", Toast.LENGTH_SHORT).show();
        }
      };
    }
  }

  private boolean handleVerify(Order order) {
    if (order.name.length() == 0) {
      Toast.makeText(this, "Order name can't be empty...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (order.describe.length() == 0) {
      Toast.makeText(this, "Order name can't be empty...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    if (idCategory < 0) {
      Toast.makeText(this, "Select a service...!", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }
}
