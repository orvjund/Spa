package com.example.rat.spa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rat.spa.R;
import com.example.rat.spa.activity.StoreDetailActivity;
import com.example.rat.spa.model.Store;

import java.util.ArrayList;

public class StoreListAdapter extends BaseAdapter {
  ArrayList<Store> stores;
  Activity activity;
  public StoreListAdapter(Activity activity, ArrayList<Store> stores) {
    this.activity = activity;
    this.stores = stores;
  }

  @Override
  public int getCount() {
    return stores.size();
  }

  @Override
  public Object getItem(int position) {
    return stores.get(position);
  }

  @Override
  public long getItemId(int position) {
    return stores.get(position).storeId;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    MyHolder myHolder;
    if(convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.storelist_layout, null);
      myHolder = new MyHolder();
      myHolder.txtStoreName = convertView.findViewById(R.id.txt_store_name);
      myHolder.txtPhoneNumber = convertView.findViewById(R.id.txt_phone_number);
      myHolder.txtDistrict = convertView.findViewById(R.id.txt_district);
      myHolder.txtCity = convertView.findViewById(R.id.txt_city);
      convertView.setTag(myHolder);
    } else {
      myHolder = (MyHolder)convertView.getTag();
    }

    final Store store = stores.get(position);
    myHolder.txtStoreName.setText(store.name);
    myHolder.txtPhoneNumber.setText(store.phone);
    myHolder.txtDistrict.setText(store.districtName);
    myHolder.txtCity.setText(store.provinceName);

    convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toStoreDetailActivity(store);
      }
    });

    return convertView;
  }

  private void toStoreDetailActivity(Store store) {
    Intent intent = new Intent(activity, StoreDetailActivity.class);
    intent.putExtra("store-id", store.storeId);

    activity.startActivity(intent);
  }

  class MyHolder {
    TextView txtStoreName;
    TextView txtPhoneNumber;
    TextView txtDistrict;
    TextView txtCity;
  }
}
