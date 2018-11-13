package com.example.rat.spa.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.rat.spa.R;
import com.example.rat.spa.activity.StoreDetailActivity;
import com.example.rat.spa.model.Discuss;
import com.example.rat.spa.util.SpaUtil;

import java.util.ArrayList;

public class StoreDiscussAdapter extends BaseAdapter {
  Activity activity;
  ArrayList<Discuss> discusses;

  public StoreDiscussAdapter(Activity activity, ArrayList<Discuss> discusses) {
    this.activity = activity;
    this.discusses = discusses;
  }

  @Override
  public int getCount() {
    return discusses.size();
  }

  @Override
  public Object getItem(int position) {
    return discusses.get(position);
  }

  @Override
  public long getItemId(int position) {
    return discusses.get(position).id;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    MyHolder myHolder;
    if (convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.store_detail_tabview_discussion_layout, null);
      myHolder = new MyHolder();
      myHolder.txtUsername = convertView.findViewById(R.id.txt_username);
      myHolder.txtContent = convertView.findViewById(R.id.txt_content);
      myHolder.txtTime = convertView.findViewById(R.id.txt_time);
      convertView.setTag(myHolder);
    } else {
      myHolder = (MyHolder) convertView.getTag();
    }

    myHolder.txtUsername.setText(discusses.get(position).createdByName);
    myHolder.txtContent.setText(discusses.get(position).body);
    myHolder.txtTime.setText(SpaUtil.getFormattedDate(discusses.get(position).created));

    return convertView;
  }

  class MyHolder {
    TextView txtUsername;
    TextView txtContent;
    TextView txtTime;

  }
}
