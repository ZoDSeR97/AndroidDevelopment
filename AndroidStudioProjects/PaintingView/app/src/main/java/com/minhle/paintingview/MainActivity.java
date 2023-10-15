package com.minhle.paintingview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        View lineView = (View) findViewById(R.id.simpleDrawingView);
        View circleView = (View) findViewById(R.id.CircleDrawingView);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked()){
                    lineView.setVisibility(View.VISIBLE);
                    circleView.setVisibility(View.INVISIBLE);
                }else{
                    lineView.setVisibility(View.INVISIBLE);
                    circleView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}