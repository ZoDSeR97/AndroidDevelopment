package com.minhle.json;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ReadJSONFeedTask().execute("https://api.androidhive.info/contacts/");

    }
    public String readJSONFeed(String address){
        URL url = null;
        try{
            url = new URL(address);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            InputStream content = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return stringBuilder.toString();
    }
    private void UpdateListView(List<String> data1){
        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        listView.setAdapter(adapter);
    }
    private class ReadJSONFeedTask extends AsyncTask<String, Void, String>{
        protected  String doInBackground(String... urls){
            return readJSONFeed(urls[0]);
        }

        protected  void onPostExecute(String result){


            try{
                System.out.println("result: " + result);
                JSONObject jsonObj = new JSONObject(result);
                JSONArray contacts = jsonObj.getJSONArray("contacts");
                Log.i("JSON","Number of contacts in feed: " + contacts.length());


                for(int i = 0; i < contacts.length();i++){
                    JSONObject tmp = contacts.getJSONObject(i);
                    data.add(tmp.getString("name") + " - " + tmp.getString("email") + "\n");
                }
                UpdateListView(data);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}