package com.example.rat.spa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rat.spa.R;
import com.example.rat.spa.activity.StoreDetailActivity;
import com.example.rat.spa.model.Promotion;
import com.example.rat.spa.model.Store;

import java.util.ArrayList;

public class StorePromotionAdapter extends BaseAdapter {
  ArrayList<Promotion> promotions;
  Activity activity;
  public StorePromotionAdapter(Activity activity, ArrayList<Promotion> promotions) {
    this.activity = activity;
    this.promotions = promotions;
  }

  @Override
  public int getCount() {
    return promotions.size();
  }

  @Override
  public Object getItem(int position) {
    return promotions.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    MyHolder myHolder;
    if(convertView == null) {
      convertView = activity.getLayoutInflater().inflate(R.layout.store_detail_tabview_promotin_layout, null);
      myHolder = new MyHolder();
      myHolder.txtPromotionDescription = convertView.findViewById(R.id.txt_promotion_describe);
      myHolder.txtPromotionTime = convertView.findViewById(R.id.txt_promotion_time);
      convertView.setTag(myHolder);
    } else {
      myHolder = (MyHolder)convertView.getTag();
    }

    final Promotion promotion = promotions.get(position);
    myHolder.txtPromotionDescription.setText(promotion.getDescribe());
    myHolder.txtPromotionTime.setText(promotion.getFormattedTime());

//    convertView.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        toStoreDetailActivity(promotion);
//      }
//    });

    return convertView;
  }

  private void toStoreDetailActivity(Store store) {
    Intent intent = new Intent(activity, StoreDetailActivity.class);
    intent.putExtra("store-id", store.storeId);

    activity.startActivity(intent);
  }

  class MyHolder {
    TextView txtPromotionDescription;
    TextView txtPromotionTime;
  }
}
