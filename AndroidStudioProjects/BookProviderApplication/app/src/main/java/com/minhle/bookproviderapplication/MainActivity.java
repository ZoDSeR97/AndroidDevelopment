package com.minhle.bookproviderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        startActivity(new Intent("com.minhle.bookproviderapplication.SecondActivity"));
    }
}