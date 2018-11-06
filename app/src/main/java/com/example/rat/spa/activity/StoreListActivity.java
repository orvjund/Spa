package com.example.rat.spa.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rat.spa.R;

public class StoreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        getSupportActionBar().setTitle("Store List");
    }
}
