package com.minhle.contactmanagement;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends Activity {

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
    }
}