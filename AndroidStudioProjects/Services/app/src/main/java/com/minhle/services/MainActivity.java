package com.minhle.services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(mLocalBroadcast ,
                new IntentFilter("myBroadcast"));
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLocalBroadcast);
    }

    public void startService(View view) {
        progressBar.setProgress(0);
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }

    private BroadcastReceiver mLocalBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // take values from intent which contains in intent if you putted their
            // here update the progress bar
            int progress = intent.getIntExtra("progress", 0);
            if (progress > progressBar.getProgress()){
                progressBar.setProgress(progress);
            }else{
                Toast.makeText(MainActivity.this, "Something is Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    };

}