package com.minhle.mycontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        TextView ID = (TextView)findViewById(R.id.order);
        TextView ISBN = (TextView)findViewById(R.id.num);
        TextView title = (TextView)findViewById(R.id.name);
        ID.setText(getIntent().getStringExtra("ID"));
        ISBN.setText(getIntent().getStringExtra("ISBN"));
        title.setText(getIntent().getStringExtra("Title"));
    }

    public  void onClick(View view){finish();}

}