package com.minhle.contactmanagement;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void onClick(View view) {
        //---use an Intent object to return data---
        Intent i = new Intent();

        //Get user input
        EditText userName = (EditText)findViewById(R.id.userName);
        EditText phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        EditText emailAddress = (EditText)findViewById(R.id.emailAddress);

        if(stringCompare(userName.getText().toString(), "") != 0) {
            //---use the putExtra() method to return some value---
            i.putExtra("Id", getIntent().getExtras().getInt("id"));
            i.putExtra("Name", userName.getText().toString());
            i.putExtra("phoneNumber",phoneNumber.getText().toString());
            i.putExtra("emailAddress",emailAddress.getText().toString());

            //---use the setData() method to return some value---
            i.setData(Uri.parse(userName.getText().toString()+" got added\n"+"id: "+getIntent().getExtras().getInt("id")));

            //---set the result with OK and the Intent object---
            setResult(RESULT_OK, i);
        }else {
            setResult(RESULT_CANCELED, i);

            //---use the setData() method to return some value---
            i.setData(Uri.parse("Missing Name"));
        }

        //---destroy the current activity---
        finish();
    }

    public static int stringCompare(String str1, String str2) {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        // Edge case for a string inside a bigger string
        if (l1 != l2) {
            return l1 - l2;
        }

        /*
            If none of the above conditions is true,
            it implies both the strings are equal
        */
        else {
            return 0;
        }
    }

}