package com.example.rat.spa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.rat.spa.R;

public class BookingListActivity extends AppCompatActivity {
  ListView lvBookingList;
  @Override

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_list);
    getSupportActionBar().setTitle("BookingActivity list");

    lvBookingList = findViewById(R.id.listview_booking_list);
  }

  public void loadUnconfirmedOrders(View view) {
  }

  public void loadConfirmedOrders(View view) {
  }
}
