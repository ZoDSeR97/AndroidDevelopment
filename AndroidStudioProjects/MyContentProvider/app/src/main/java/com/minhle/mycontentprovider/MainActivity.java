package com.minhle.mycontentprovider;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddTitle(View view) {
        //---add a book---
        ContentValues values = new ContentValues();
        values.put(BooksProvider.TITLE, ((EditText)
                findViewById(R.id.txtTitle)).getText().toString());
        values.put(BooksProvider.ISBN, ((EditText)
                findViewById(R.id.txtISBN)).getText().toString());
        Uri uri = getContentResolver().insert(
                BooksProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(),uri.toString(),
                Toast.LENGTH_LONG).show();
    }

    public void onClickSearch(View view) {
        //---add a book---
        Cursor c = getContentResolver().query(Uri.parse("content://com.minhle.mycontentprovider/books"),null,null,null,null);
        String title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
        String isbn = ((EditText) findViewById(R.id.txtISBN)).getText().toString();
        boolean found = false;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            if((!title.isEmpty() && c.getString(c.getColumnIndex("title")).contains(title))
                || (!isbn.isEmpty() && c.getString(c.getColumnIndex("isbn")).contains(isbn))){
                Intent i = new Intent("com.minhle.mycontentprovider.ThirdActivity");
                i.putExtra("ID", c.getString(c.getColumnIndex("_id")));
                i.putExtra("Title", c.getString(c.getColumnIndex("title")));
                i.putExtra("ISBN", c.getString(c.getColumnIndex("isbn")));
                startActivity(i);
                found = true;
                break;
            }
        }
        if(!found)
            Toast.makeText(this,"No Match", Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveTitles(View view) {
        //---retrieve the titles---
        /*
        Uri allTitles = Uri.parse("content://com.minhle.mycontentprovider/books");
        Cursor c;
        CursorLoader cursorLoader = new CursorLoader(this, allTitles, null, null, null, "title desc");
        c = cursorLoader.loadInBackground();
        if (c.moveToFirst()) {
            do {
                Toast.makeText(this, c.getString(c.getColumnIndex(BooksProvider._ID)) + ", "
                        + c.getString(c.getColumnIndex(BooksProvider.TITLE)) + ", "
                        + c.getString(c.getColumnIndex(BooksProvider.ISBN)), Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }
         */
        startActivity(new Intent("com.minhle.mycontentprovider.SecondActivity"));
    }
}