package com.minhle.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {
    int counter = 0;
    static final int UPDATE_INTERVAL = 1000;
    private Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent arg0) {return null;}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        doSomethingRepeatedly();
        try {
            new DoBackgroundTask().execute(new URL("http://www.amazon.com/somefiles.pdf"),
                    new URL("http://www.wrox.com/somefiles.pdf"),
                    new URL("http://www.google.com/somefiles.pdf"),
                    new URL("http://www.learn2develop.net/somefiles.pdf"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
        }
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    private void doSomethingRepeatedly() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.d("MyService", String.valueOf(++counter));
            }
        }, 0, UPDATE_INTERVAL);
    }

    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        private Context context;
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                publishProgress((int) (((i + 1) / (float) count) * 100));
                Intent intent = new Intent("myBroadcast");
                // You can also include some extra data.
                intent.putExtra("progress", (int) (((i + 1) / (float) count) * 100)); // progress update
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            return totalBytesDownloaded;
        }

        protected void onProgressUpdate(Integer... progress) {
            Log.d("Downloading files", String.valueOf(progress[0]) + "% downloaded");
            Toast.makeText(getBaseContext(), String.valueOf(progress[0]) + "% downloaded-"+counter,Toast.LENGTH_LONG).show();

        }

        protected void onPostExecute(Long result) {
            Toast.makeText(getBaseContext(), "Downloaded " + result + " bytes", Toast.LENGTH_LONG).show();
            stopSelf();
        }

        private int DownloadFile(URL url) {
            try {
                //---simulate taking some time to download a file---
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //---return an arbitrary number representing
            // the size of the file downloaded---
            return 100;
        }
    }
}
