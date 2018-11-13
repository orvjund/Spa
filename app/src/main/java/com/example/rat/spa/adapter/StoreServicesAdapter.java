package com.example.rat.spa.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.rat.spa.R;
import com.example.rat.spa.activity.BookingActivity;
import com.example.rat.spa.model.Category;
import com.example.rat.spa.model.Store;

import java.util.ArrayList;

public abstract class StoreServicesAdapter extends BaseAdapter {
  Activity activity;
  ArrayList<Category> categories;
  ArrayList<CheckBox> checkBoxes = new ArrayList<>();

  public StoreServicesAdapter(BookingActivity activity, Store store) {
    this.activity = activity;
    this.categories = store.categories;
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
  public View getView(final int position, View convertView, ViewGroup parent) {
    final MyHolder myHolder;
    if (convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.store_services_layout, null);
      myHolder = new MyHolder();
      myHolder.cbService = convertView.findViewById(R.id.cb_services);
      convertView.setTag(myHolder);
      checkBoxes.add(myHolder.cbService);
    } else {
      myHolder = (MyHolder) convertView.getTag();
    }

    myHolder.cbService.setText(categories.get(position).getName());
    myHolder.cbService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (int i = 0; i < checkBoxes.size(); i++) {
          if (isChecked && checkBoxes.get(i) != myHolder.cbService) {
            checkBoxes.get(i).setChecked(false);
          }
        }
        updateSelected(isChecked, categories.get(position).getCategoryId());
      }
    });
    return convertView;
  }

  public abstract void updateSelected(boolean isChecked, int categoryId);


  class MyHolder {
    CheckBox cbService;
  }
}
