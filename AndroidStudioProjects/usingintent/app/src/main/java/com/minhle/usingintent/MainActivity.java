package com.minhle.usingintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    int counter = 0;
    public static final String tag = "LifeCycle Step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("interation: "+ counter);
    }

    public void onClick(View view) {
        startActivity(new Intent("com.example.myapplication.SecondActivity"));
    }

    public void onStart() {
        super.onStart();
        Log.d(tag, "onStart");
    }

    public void onRestart() {
        super.onRestart();
        Log.d(tag, "onRestart");
    }

    public void onResume() {
        super.onResume();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("interation: "+ counter);
        counter += 1;
        Log.d(tag, "onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(tag, "onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(tag, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy");
    }

}