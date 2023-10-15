package com.minhle.myanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        TextView userName = (TextView)findViewById(R.id.userName);
        TextView phoneNumber = (TextView)findViewById(R.id.phoneNumber);
        TextView emailAddress = (TextView)findViewById(R.id.emailAddress);
        userName.setText(getIntent().getStringExtra("Name"));
        phoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
        emailAddress.setText(getIntent().getStringExtra("emailAddress"));
    }

    public void onClick(View view) {
        finish();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}