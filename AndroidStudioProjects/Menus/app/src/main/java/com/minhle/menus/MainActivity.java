package com.minhle.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.view.ContextMenu;
import android.widget.Button;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList <String> bookmarkUrl = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.bookmark);
        //btn.setOnCreateContextMenuListener(this);
        WebView wv = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        /* Hope the link still work */
        wv.loadUrl("https://www.futurity.org/wp/wp-content/uploads/2019/02/viceroy-butterfly_1600.jpg");
    }
    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        createMenu(menu);
    }
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        createMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuChoice(item);
    }

    private void createMenu(Menu menu) {
        MenuItem mnu1 = menu.add(0, 0, 1, "Item 1");
        {
            mnu1.setAlphabeticShortcut('a');
        }
        MenuItem mnu2 = menu.add(0, 1, 2, "Item 2");
        {
            mnu2.setAlphabeticShortcut('b');
        }
        MenuItem mnu3 = menu.add(0, 2, 3, "Item 3");
        {
            mnu3.setAlphabeticShortcut('c');
        }
        MenuItem mnu4 = menu.add(0, 3, 4, "Item 4");
        {
            mnu4.setAlphabeticShortcut('d');
        }
        menu.add(0, 4, 5, "Item 5");
        menu.add(0, 5, 6, "Item 6");
        menu.add(0, 6, 7, "Item 7");
    }

    private boolean menuChoice(MenuItem item) {
        Toast.makeText(this, "You clicked on Item " +item.getOrder(),Toast.LENGTH_LONG).show();
        if(bookmarkUrl.get(item.getItemId()) != null){
            WebView wv = (WebView) findViewById(R.id.webView);
            WebSettings webSettings = wv.getSettings();
            webSettings.setBuiltInZoomControls(true);
            wv.loadUrl(bookmarkUrl.get(item.getItemId()));
        }
        return true;
    }
    public void onSubmit(View view) {
        EditText userURL = findViewById(R.id.editText);
        WebView wv = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        wv.loadUrl(String.valueOf(userURL.getText()));
    }
    public void onBookmark(View view) {
        WebView wv = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        if(!bookmarkUrl.contains(wv.getUrl())){
            bookmarkUrl.add(wv.getUrl());
        }else{
            bookmarkUrl.remove(bookmarkUrl.indexOf(wv.getUrl()));
        }
        invalidateOptionsMenu();
    }
    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        menu.removeGroup(0);
        if (bookmarkUrl.size() != 0){
            for(int i = 0; i<bookmarkUrl.size(); i++){
                menu.add(0, i, i+1, bookmarkUrl.get(i));
            }
        }
        return true;
    }

}