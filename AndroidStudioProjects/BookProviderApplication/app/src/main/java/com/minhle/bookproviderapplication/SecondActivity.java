package com.minhle.bookproviderapplication;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.content.CursorLoader;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends Activity {

    ListView listView;
    ArrayAdapter adapter;
    List<String> bookList = new ArrayList<String>();
    static final Uri CONTENT_URI = Uri.parse("content://com.minhle.mycontentprovider/books");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        System.out.println(CONTENT_URI);
        getApplicationContext().grantUriPermission("com.minhle.bookproviderapplication",CONTENT_URI,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
        Cursor c = getContentResolver().query(CONTENT_URI,null,null,null,null);
        this.listView = findViewById(R.id.listView);
        this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        if(c != null){
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                System.out.println(c.getString(c.getColumnIndex("_id")));
                System.out.println(c.getString(c.getColumnIndex("title")));
                System.out.println(c.getString(c.getColumnIndex("isbn")));
                String[] info = {c.getString(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("title")),
                        c.getString(c.getColumnIndex("isbn"))};
                  bookList.addAll(Arrays.asList(info));
                  adapter.add(c.getString(c.getColumnIndex("_id")));
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int loc, long id) {
                    Toast.makeText(SecondActivity.this, "clicked item: "+loc+" "+adapter.getItem(loc), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent("com.minhle.bookproviderapplication.ThirdActivity");
                    i.putExtra("ID", bookList.get(loc*3));
                    i.putExtra("ISBN", bookList.get(loc*3+1));
                    i.putExtra("Title", bookList.get(loc*3+2));
                    startActivity(i);
                }
            });
        }
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(SecondActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(SecondActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(SecondActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void sortAscending(View view){

    }

    public void sortDescending(View view){

    }

}