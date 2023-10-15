package com.minhle.networking;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    final private int REQUEST_INTERNET = 123;
    ArrayList<String> listURL = new ArrayList();
    Bitmap[] imageIDs = null;
    private ImageSwitcher imgSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
        } else {
            downloadSomething();
        }
    }

    private void downloadSomething() {
        //String texturl = "https://www.google.com/";
        String imgurl = "https://upload.wikimedia.org/wikipedia/commons/0/04/New_York_Empire_Apples.jpg";
        new DownloadImageTask().execute(imgurl);
        //new DownloadTextTask().execute(texturl);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_INTERNET:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    downloadSomething();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;   int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private InputStream download(String URL) {
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            return in;
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return null;
    }

    private Bitmap[] downloadURL(String Url) {
        String content = null;
        try {
            URLConnection connection =  new URL("https://news.yahoo.com").openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        String[] arr = content.split("\"");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].endsWith("jpg")) {
                listURL.add(arr[i]);
            }
        }
        return DownloadImage(listURL);
    }

    private Bitmap[] DownloadImage(ArrayList<String> URL) {
        Bitmap bitmap[] = new Bitmap[URL.size()];
        InputStream in = null;
        for (int i = 0; i < URL.size(); i++) {
            if (URL.get(i).startsWith("https:")) {
                in = download(URL.get(i));
            }else {
                in = download("https:"+URL.get(i));
            }
            if(in != null) {
                bitmap[i] = BitmapFactory.decodeStream(in);
                try {
                    in.close();
                } catch (IOException e1) {
                    Log.d("NetworkingActivity", e1.getLocalizedMessage());
                }
            }
        }
        imageIDs = bitmap;

        return imageIDs;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap[]> {
        protected Bitmap[] doInBackground(String... urls) {
            return downloadURL(urls[0]);
        }

        protected void onPostExecute(Bitmap[] result) {
            ImageView img = (ImageView) findViewById(R.id.imageView);
            GridView gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new ImageAdapter(MainActivity.this));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    Toast.makeText(getBaseContext(),"pic" + (position + 1) + " selected",Toast.LENGTH_SHORT).show();
                    img.setImageBitmap(result[position]);
                }
            });
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        public ImageAdapter(Context c) {
            context = c;
        }
        //---returns the number of images---
        public int getCount() {
            return imageIDs.length;
        }
        //---returns the item---
        public Object getItem(int position) {
            return position;
        }
        //---returns the ID of an item---
        public long getItemId(int position) {
            return position;
        }
        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(imageIDs[position]);
            return imageView;
        }
    }
    /*
    private String DownloadText(String URL) {
        int BUFFER_SIZE = 2000;
        InputStream in = download(URL);
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
        return str;
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }
    }
     */
}