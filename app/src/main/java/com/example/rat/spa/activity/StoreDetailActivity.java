package com.example.rat.spa.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.adapter.StorePromotionAdapter;
import com.example.rat.spa.api.RateStoreRequest;
import com.example.rat.spa.api.StoreDetailRequest;
import com.example.rat.spa.model.Promotion;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SharedPref;

import java.util.ArrayList;

public class StoreDetailActivity extends AppCompatActivity {
  TextView txtStoreName;
  Button btnBook;
  RatingBar rating;
  RatingBar myRating;
  TextView txtAddress;
  ListView lvTabview;
  int storeId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store_detail);
    getSupportActionBar().setTitle("Store List Detail");

    this.storeId = getIntent().getIntExtra("store-id", -1);

    initViewVariables();
    loadStoreDetail();
    initRating();
  }

  public void initRating() {
    final Activity thisActivity = this;
    myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        new RateStoreRequest(thisActivity, SharedPref.getToken(thisActivity), storeId, rating) {
          @Override
          public void handleResult(String result) {
            Toast.makeText(StoreDetailActivity.this, "Rated...!", Toast.LENGTH_SHORT).show();
            loadStoreDetail();
          }

          @Override
          public void handleError(VolleyError error) {
            Toast.makeText(StoreDetailActivity.this, "Rating failed...!", Toast.LENGTH_SHORT).show();
            myRating.setRating(0);
          }
        };
      }
    });
  }

  private void initViewVariables() {
    lvTabview = findViewById(R.id.list_tabview);
    txtStoreName = findViewById(R.id.txt_store_name);
    btnBook = findViewById(R.id.btn_book);
    rating = findViewById(R.id.rating);
    myRating = findViewById(R.id.my_rating);
    txtAddress = findViewById(R.id.txt_address);
    rating.setEnabled(false);
  }

  private void loadStoreDetail() {
    new StoreDetailRequest(this, SharedPref.getToken(this), this.storeId) {
      @Override
      public void handleError(VolleyError error) {
        error.printStackTrace();
        Toast.makeText(StoreDetailActivity.this, "Error: Can't load store details...!", Toast.LENGTH_SHORT).show();
        finish();
      }

      @Override
      public void handleStore(Store store) {
        fillDetailToViews(store);
      }
    };
  }

  private void fillDetailToViews(Store store) {
    txtStoreName.setText(store.name);
    rating.setRating(store.rating);
    txtAddress.setText(store.address);
    loadPromotions(store.promotions);
  }

  private void loadPromotions(ArrayList<Promotion> promotions) {
    lvTabview.setAdapter(new StorePromotionAdapter(this, promotions));
  }
}
