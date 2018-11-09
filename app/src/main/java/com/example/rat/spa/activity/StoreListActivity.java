package com.example.rat.spa.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.adapter.StoreListAdapter;
import com.example.rat.spa.api.StoreListRequest;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SharedPref;

import java.util.ArrayList;

public class StoreListActivity extends AppCompatActivity {

  ListView storeList;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store_list);
    getSupportActionBar().setTitle("Store List");

    storeList = findViewById(R.id.store_list_view);

    loadStoreList();
  }

  private void loadStoreList() {
    final Activity thisActivity = this;
    new StoreListRequest(this, SharedPref.getToken(thisActivity)) {
      @Override
      public void handleError(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(StoreListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
      }

      @Override
      public void handleStores(ArrayList<Store> stores) {
        storeList.setAdapter(new StoreListAdapter(thisActivity, stores));
      }
    };
  }
}
