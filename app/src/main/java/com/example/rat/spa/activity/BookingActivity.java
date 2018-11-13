package com.example.rat.spa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.adapter.StoreServicesAdapter;
import com.example.rat.spa.api.StoreDetailRequest;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SharedPref;
import com.example.rat.spa.util.SpaUtil;

import java.util.Date;

public class BookingActivity extends AppCompatActivity {
  int storeId;
  TextView txtStoreName;
  TextView txtStoreInfo;
  TextView txtBookingTime;
  ListView lvStoreServices;
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
    loadStoreDetail();
  }

  private void initViewVariables() {
    txtStoreName = findViewById(R.id.txt_store_name);
    txtStoreInfo = findViewById(R.id.txt_store_info);
    txtBookingTime = findViewById(R.id.txt_booking_time);
    lvStoreServices = findViewById(R.id.lv_store_services);
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
    lvStoreServices.setAdapter(new StoreServicesAdapter(this, store));
  }
}
