package com.minhle.mycontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    List<String> bookList = new ArrayList<String>();
    static final Uri CONTENT_URI = Uri.parse("content://com.minhle.mycontentprovider/books");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        System.out.println(CONTENT_URI);
        Cursor c = getContentResolver().query(CONTENT_URI,null,null,null,null);
        this.listView = findViewById(R.id.listView);
        this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        if(c != null){
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                String[] info = {c.getString(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("title")),
                        c.getString(c.getColumnIndex("isbn"))};
                bookList.addAll(Arrays.asList(info));
                adapter.add(c.getString(c.getColumnIndex("title")));
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int loc, long id) {
                    Toast.makeText(SecondActivity.this, "clicked item: "+loc+" "+adapter.getItem(loc), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent("com.minhle.mycontentprovider.ThirdActivity");
                    i.putExtra("ID", bookList.get(loc*3));
                    i.putExtra("Title", bookList.get(loc*3+1));
                    i.putExtra("ISBN", bookList.get(loc*3+2));
                    startActivity(i);
                }
            });
        }
    }

    public void sortAscending(View view){
        Cursor c = getContentResolver().query(CONTENT_URI,null,null,null,null);
        this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        if(c != null){
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                String[] info = {c.getString(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("title")),
                        c.getString(c.getColumnIndex("isbn"))};
                bookList.addAll(Arrays.asList(info));
                adapter.add(c.getString(c.getColumnIndex("title")));
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int loc, long id) {
                    Toast.makeText(SecondActivity.this, "clicked item: "+loc+" "+adapter.getItem(loc), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent("com.minhle.mycontentprovider.ThirdActivity");
                    i.putExtra("ID", bookList.get(loc*3));
                    i.putExtra("Title", bookList.get(loc*3+1));
                    i.putExtra("ISBN", bookList.get(loc*3+2));
                    startActivity(i);
                }
            });
        }
    }

    public void sortDescending(View view){
        Cursor c = getContentResolver().query(CONTENT_URI,null,null,null,null);
        this.adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        if(c != null){
            for(c.moveToLast(); !c.isBeforeFirst(); c.moveToPrevious()){
                String[] info = {c.getString(c.getColumnIndex("_id")),
                        c.getString(c.getColumnIndex("title")),
                        c.getString(c.getColumnIndex("isbn"))};
                bookList.addAll(Arrays.asList(info));
                adapter.add(c.getString(c.getColumnIndex("title")));
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int loc, long id) {
                    Toast.makeText(SecondActivity.this, "clicked item: "+loc+" "+adapter.getItem(loc), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent("com.minhle.mycontentprovider.ThirdActivity");
                    i.putExtra("ID", bookList.get(loc*3));
                    i.putExtra("Title", bookList.get(loc*3+1));
                    i.putExtra("ISBN", bookList.get(loc*3+2));
                    startActivity(i);
                }
            });
        }
    }

}