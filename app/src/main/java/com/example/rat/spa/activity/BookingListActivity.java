package com.example.rat.spa.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.adapter.OrderListAdapter;
import com.example.rat.spa.api.OrderIndexRequest;
import com.example.rat.spa.model.Order;
import com.example.rat.spa.util.SharedPref;

import java.util.ArrayList;

public class BookingListActivity extends AppCompatActivity {
  ListView lvBookingList;
  TabLayout tlBookingList;

  @Override

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_booking_list);
    getSupportActionBar().setTitle("BookingActivity list");

    lvBookingList = findViewById(R.id.listview_booking_list);
    tlBookingList = findViewById(R.id.booking_list_tab_layout);

    tlBookingList.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        loadOrders(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
    loadOrders(0);
  }

  private void loadOrders(int position) {
    new OrderIndexRequest(this, SharedPref.getToken(this), position + 1) {
      @Override
      public void handleError(VolleyError error) {
        Toast.makeText(BookingListActivity.this, "Failed to load orders...!", Toast.LENGTH_SHORT).show();
      }

      @Override
      protected void handleOrders(ArrayList<Order> orders) {
        lvBookingList.setAdapter(new OrderListAdapter(BookingListActivity.this, orders));
      }
    };
  }
}
