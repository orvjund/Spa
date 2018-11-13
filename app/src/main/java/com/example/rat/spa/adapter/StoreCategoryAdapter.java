package com.example.rat.spa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rat.spa.R;
import com.example.rat.spa.activity.StoreDetailActivity;
import com.example.rat.spa.model.Category;
import com.example.rat.spa.model.Promotion;
import com.example.rat.spa.model.Store;

import java.util.ArrayList;

public class StoreCategoryAdapter extends BaseAdapter {
  ArrayList<Category> categories;
  Activity activity;
  public StoreCategoryAdapter(Activity activity, ArrayList<Category> categories) {
    this.activity = activity;
    this.categories = categories;
  }

  @Override
  public int getCount() {
    return categories.size();
  }

  @Override
  public Object getItem(int position) {
    return categories.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    MyHolder myHolder;
    if(convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.store_service_name_layout, null);
      myHolder = new MyHolder();
      myHolder.txtServiceName = convertView.findViewById(R.id.txt_service_name);
      convertView.setTag(myHolder);
    } else {
      myHolder = (MyHolder)convertView.getTag();
    }

    myHolder.txtServiceName.setText(categories.get(position).getName());

    return convertView;
  }

  class MyHolder {
    TextView txtServiceName;
  }
}
