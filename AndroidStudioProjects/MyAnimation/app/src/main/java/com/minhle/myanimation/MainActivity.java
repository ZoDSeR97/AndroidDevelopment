package com.minhle.myanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    int request_Code = 1;
    int counter = 0;
    public static final String tag = "LifeCycle Step";
    ListView listView;
    ArrayAdapter adapter;
    List<String> contact = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        this.listView = listView;
        this.adapter = adapter;

        //---log event---
        Log.d(tag, "onCreate");
    }

    public void onClick(View view) {
        Intent i = new Intent("com.minhle.myanimation.SecondActivity");

        //---use putExtra() to add new name/value pairs---
        i.putExtra("str1", "Adding Contact");

        //---use a Bundle object to add new name/values pairs---
        Bundle extras = new Bundle();
        extras.putString("str2", "Fill out all the fields!");
        extras.putInt("id", counter);

        //---attach the Bundle object to the Intent object---
        i.putExtras(extras);

        //---start the activity to get a result back---
        startActivityForResult(i, 1);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //---check if the request code is 1---
        if (requestCode == request_Code) {
            //---if the result is OK---
            if (resultCode == RESULT_OK) {
                //---get the result using getData()---
                Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT).show();

                //---add button into layout---
                String[] info = {data.getStringExtra("Name"),
                        data.getStringExtra("phoneNumber"),
                        data.getStringExtra("emailAddress")};
                contact.addAll(Arrays.asList(info));
                adapter.add(info[0]);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int loc, long id) {
                        Toast.makeText(MainActivity.this, "clicked item: "+loc+" "+adapter.getItem(loc), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent("com.minhle.myanimation.ThirdActivity");
                        i.putExtra("Name", contact.get(loc*3));
                        i.putExtra("phoneNumber", contact.get(loc*3+1));
                        i.putExtra("emailAddress", contact.get(loc*3+2));
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in,R.anim.left_out);
                    }
                });
                counter ++;
            } else {
                Toast.makeText(this, "Missing info!\n No contact were added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onStart() {
        super.onStart();

        //---log event---
        Log.d(tag, "onStart");
    }

    protected void onRestart() {
        super.onRestart();

        //---log event---
        Log.d(tag, "onRestart");
    }

    protected void onResume() {
        super.onResume();
        if(counter != 0){
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText("Welcome back!\nIteration: "+ counter);
        }
        counter++;

        //---log event---
        Log.d(tag, "onResume");
    }

    protected void onPause() {
        super.onPause();

        //---log event---
        Log.d(tag, "onPause");
    }

    protected void onStop() {
        super.onStop();

        //---log event---
        Log.d(tag, "onStop");
    }

    protected void onDestroy() {
        super.onDestroy();

        //---log event---
        Log.d(tag, "onDestroy");
    }

}