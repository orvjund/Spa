package com.example.rat.spa.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rat.spa.R;
import com.example.rat.spa.adapter.StoreCategoryAdapter;
import com.example.rat.spa.adapter.StoreDiscussAdapter;
import com.example.rat.spa.adapter.StorePromotionAdapter;
import com.example.rat.spa.adapter.StoreRatingAdapter;
import com.example.rat.spa.api.RateStoreRequest;
import com.example.rat.spa.api.StoreDetailRequest;
import com.example.rat.spa.model.Category;
import com.example.rat.spa.model.Discuss;
import com.example.rat.spa.model.Promotion;
import com.example.rat.spa.model.Rating;
import com.example.rat.spa.model.Store;
import com.example.rat.spa.util.SharedPref;

import java.util.ArrayList;

public class StoreDetailActivity extends AppCompatActivity {
  TextView txtStoreName;
  Button btnBook;
  RatingBar rating;
  RatingBar myRating;
  TextView txtAddress;
  TextView txtPhone;
  ListView lvTabLayout;
  TabLayout tabLayout;
  int storeId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_store_detail);
    getSupportActionBar().setTitle("Store Detail");

    this.storeId = getIntent().getIntExtra("store-id", -1);

    initViewVariables();
    loadStoreDetail();
    initRating();
  }

  public void initRating() {
    final Activity thisActivity = this;
    myRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {
        if (!fromUser) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(StoreDetailActivity.this);
        builder.setTitle("Enter a note...!");

        final EditText input = new EditText(StoreDetailActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            String content = input.getText().toString();
            if (content.length() == 0) {
              Toast.makeText(thisActivity, "Note can't be empty...!", Toast.LENGTH_SHORT).show();
              return;
            }
            new RateStoreRequest(thisActivity, SharedPref.getToken(thisActivity), storeId, rating, content) {
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
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });

        builder.show();
      }
    });
  }

  private void initViewVariables() {
    tabLayout = findViewById(R.id.tab_layout);
    lvTabLayout = findViewById(R.id.list_tabview);
    txtStoreName = findViewById(R.id.txt_store_name);
    btnBook = findViewById(R.id.btn_book);
    rating = findViewById(R.id.rating);
    myRating = findViewById(R.id.my_rating);
    txtAddress = findViewById(R.id.txt_address);
    txtPhone = findViewById(R.id.txt_phone);
//    rating.setEnabled(false);
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
    rating.setRating(store.averageRating);
    myRating.setRating(store.myRating);
    txtAddress.setText(store.getFullAddress());
    txtPhone.setText(store.phone);
    initTabLayout();
    tabLayout.getTabAt(0).select();
    loadPromotions(store.promotions);
  }

  private void initTabLayout() {
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(final TabLayout.Tab tab) {
        new StoreDetailRequest(StoreDetailActivity.this, SharedPref.getToken(StoreDetailActivity.this), storeId) {
          @Override
          public void handleError(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(StoreDetailActivity.this, "Error: Can't load store details...!", Toast.LENGTH_SHORT).show();
            finish();
          }
          @Override
          public void handleStore(final Store store) {
            int index = tab.getPosition();
            switch (index) {
              case 0:
                loadPromotions(store.promotions);
                break;
              case 1:
                loadServices(store.categories);
                break;
              case 2:
                loadDiscussion(store.discusses);
                break;
              case 3:
                loadRatings(store.ratings);
                break;
            }
          }
        };
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  private void loadRatings(ArrayList<Rating> ratings) {
    lvTabLayout.setAdapter(new StoreRatingAdapter(this, ratings));
  }

  private void loadDiscussion(ArrayList<Discuss> discusses) {
    lvTabLayout.setAdapter(new StoreDiscussAdapter(this, discusses));
  }

  private void loadServices(ArrayList<Category> categories) {
    lvTabLayout.setAdapter(new StoreCategoryAdapter(this, categories));
  }

  private void loadPromotions(ArrayList<Promotion> promotions) {
    lvTabLayout.setAdapter(new StorePromotionAdapter(this, promotions));
  }

  public void toBookingActivity(View view) {
    Intent intent = new Intent(this, BookingActivity.class);
    intent.putExtra("store-id", this.storeId);
    startActivity(intent);
    finish();
  }
}
