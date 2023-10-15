package com.minhle.databases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    EditText textBox;
    long rowId = 0;
    DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new DBAdapter(this);
        this.textBox = findViewById(R.id.editText);
    }

    public void onInsert(View view){
        String contactInfo = textBox.getText().toString();
        String contactEmail = contactInfo.replaceAll("\\s+","").toLowerCase()+"@minhle.com";
        System.out.println(contactInfo + contactEmail);
        db.open();
        long id = db.insertContact(contactInfo, contactEmail);
        rowId = id;
        db.close();
    }

    public void onLoad(View view){
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()){
            do {
                DisplayContact(c);
            } while (c.moveToNext());
        }
        db.close();
    }

    public void on1Load(View view){
        db.open();
        Cursor c = db.getContact(rowId);
        if (c.moveToFirst())
            DisplayContact(c);
        else
            Toast.makeText(this, "No contact found", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void onUpdate(View view){
        String contactInfo = textBox.getText().toString();
        String contactEmail = contactInfo.replaceAll("\\s+","").toLowerCase()+"@minhle.com";
        db.open();
        if (db.updateContact(rowId, contactInfo, contactEmail))
            Toast.makeText(this, "Update successful.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Update failed.", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public void onDelete(View view){
        db.open();
        if (db.deleteContact(rowId)) {
            Toast.makeText(this, "Delete successful.", Toast.LENGTH_SHORT).show();
            rowId -= 1;
        }else {
            Toast.makeText(this, "Delete failed.", Toast.LENGTH_SHORT).show();
            rowId = 0;
        }
        db.close();
    }

    public void DisplayContact(Cursor c) {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n" +
                        "Email:  " + c.getString(2),
                Toast.LENGTH_SHORT).show();
    }
}