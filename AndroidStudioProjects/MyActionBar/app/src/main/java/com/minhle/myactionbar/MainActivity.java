package com.minhle.myactionbar;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        CreateMenu(menu);
        return true;
    }

    private void CreateMenu(Menu menu) {
        MenuItem mnu1 = menu.add(0, 0, 0, "Item 1");
        {
            mnu1.setIcon(R.mipmap.ic_launcher);
            mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Item 2");
        {
            mnu2.setIcon(R.mipmap.ic_launcher);
            mnu2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "Item 3");
        {
            mnu3.setIcon(R.mipmap.ic_launcher);
            mnu3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "Item 4");
        {
            mnu4.setIcon(R.mipmap.ic_launcher);
            mnu4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        MenuItem mnu5 = menu.add(0, 4, 4,"Item 5");
        {
            mnu5.setIcon(R.mipmap.ic_launcher);
            mnu5.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //return super.onOptionsItemSelected(item);
        return  MenuChoice(item);
    }

    private boolean MenuChoice(MenuItem item)
    {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "You clicked on Item 1", Toast.LENGTH_SHORT).show();
                makeTextView("You clicked on Item 1");
                return true;
            case 1:
                Toast.makeText(this, "You clicked on Item 2",Toast.LENGTH_SHORT).show();
                makeTextView("You clicked on Item 2");
                return true;
            case 2:
                Toast.makeText(this, "You clicked on Item 3",Toast.LENGTH_SHORT).show();
                makeTextView("You clicked on Item 3");
                return true;
            case 3:
                Toast.makeText(this, "You clicked on Item 4",Toast.LENGTH_SHORT).show();
                makeTextView("You clicked on Item 4");
                return true;
            case 4:
                Toast.makeText(this, "You clicked on Item 5",Toast.LENGTH_SHORT).show();
                clearText();
                return true;
        }
        return false;
    }

    private void makeTextView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.addView(textView);
    }

    private void clearText(){
        LinearLayout layout = findViewById(R.id.linearLayout);
        layout.removeAllViews();
    }

}