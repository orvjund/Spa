package com.example.rat.spa.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rat.spa.model.Store;

import java.util.ArrayList;

public class StoreListAdapter extends BaseAdapter {
  ArrayList<Store> stores;
  public StoreListAdapter(ArrayList<Store> stores) {
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

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return null;
  }
}
