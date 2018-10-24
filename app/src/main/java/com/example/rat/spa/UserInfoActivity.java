package com.example.rat.spa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserInfoActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);

    Intent intent = getIntent();
    String jsonUserInfo = intent.getStringExtra("json-user-info");
    if (jsonUserInfo != null) {
      ((TextView)findViewById(R.id.txt_user_info)).setText(jsonUserInfo);
    } else {
      Toast.makeText(this, "Failed to get user info..!", Toast.LENGTH_SHORT).show();
    }
  }
}
