package com.example.rat.spa.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rat.spa.R;
import com.example.rat.spa.model.Rating;
import com.example.rat.spa.util.SpaUtil;

import java.util.ArrayList;

public class StoreRatingAdapter extends BaseAdapter {
  Activity activity;
  ArrayList<Rating> ratings;

  public StoreRatingAdapter(Activity activity, ArrayList<Rating> ratings) {
    this.activity = activity;
    this.ratings = ratings;
  }

  @Override
  public int getCount() {
    return ratings.size();
  }

  @Override
  public Object getItem(int position) {
    return ratings.get(position);
  }

  @Override
  public long getItemId(int position) {
    return ratings.get(position).id;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    MyHolder myHolder;
    if (convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.store_detail_tabview_rating_layout, null);
      myHolder = new MyHolder();
      myHolder.txtCreatedByName = convertView.findViewById(R.id.txt_people_rating);
      myHolder.ratingBar = convertView.findViewById(R.id.rate_bar);
      myHolder.txtContent = convertView.findViewById(R.id.txt_content);
      myHolder.txtTime = convertView.findViewById(R.id.txt_time);
      convertView.setTag(myHolder);
    } else {
      myHolder = (MyHolder) convertView.getTag();
    }

    myHolder.txtCreatedByName.setText(ratings.get(position).createdByName);
    myHolder.ratingBar.setRating(ratings.get(position).rate);
    myHolder.txtContent.setText(ratings.get(position).content);
    myHolder.txtTime.setText(SpaUtil.getFormattedDate(ratings.get(position).created));

    return convertView;
  }

  class MyHolder {
    TextView txtCreatedByName;
    RatingBar ratingBar;
    TextView txtContent;
    TextView txtTime;

  }
}
