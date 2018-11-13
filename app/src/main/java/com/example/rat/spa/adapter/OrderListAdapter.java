package com.example.rat.spa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rat.spa.R;
import com.example.rat.spa.activity.StoreDetailActivity;
import com.example.rat.spa.model.Order;
import com.example.rat.spa.model.Store;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter {
  ArrayList<Order> orders;
  Activity activity;
  public OrderListAdapter(Activity activity, ArrayList<Order> orders) {
    this.activity = activity;
    this.orders = orders;
  }

  @Override
  public int getCount() {
    return orders.size();
  }

  @Override
  public Object getItem(int position) {
    return orders.get(position);
  }

  @Override
  public long getItemId(int position) {
    return orders.get(position).orderId;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    MyHolder myHolder;
    if(convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.booking_list_tabview_confirmed_layout, null);
      myHolder = new MyHolder();
      myHolder.txtUser = convertView.findViewById(R.id.txt_user);
      myHolder.txtAddress = convertView.findViewById(R.id.txt_address);
      myHolder.txtPhone = convertView.findViewById(R.id.txt_phone);
      myHolder.txtServices = convertView.findViewById(R.id.txt_services);
      myHolder.txtTime = convertView.findViewById(R.id.txt_time);
      convertView.setTag(myHolder);
    } else {
      myHolder = (MyHolder)convertView.getTag();
    }

    final Order order = orders.get(position);
    myHolder.txtUser.setText(order.userName);
    myHolder.txtAddress.setText(order.address);
    myHolder.txtPhone.setText(order.phone);
    myHolder.txtServices.setText(order.categoryName);
    myHolder.txtTime.setText(order.onDate);

    return convertView;
  }

//  private void toStoreDetailActivity(Store store) {
//    Intent intent = new Intent(activity, StoreDetailActivity.class);
//    intent.putExtra("store-id", store.storeId);
//
//    activity.startActivity(intent);
//  }

  class MyHolder {
    TextView txtUser;
    TextView txtServices;
    TextView txtAddress;
    TextView txtTime;
    TextView txtPhone;
  }
}
