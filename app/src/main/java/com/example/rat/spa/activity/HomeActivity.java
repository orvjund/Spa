package com.example.rat.spa.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rat.spa.R;

public class HomeActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        constraintLayout = findViewById(R.id.home_layout);
        constraintLayout.setBackgroundResource(R.color.colorHome);
    }
}
