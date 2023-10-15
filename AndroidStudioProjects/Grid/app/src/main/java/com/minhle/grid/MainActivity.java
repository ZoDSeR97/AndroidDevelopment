package com.minhle.grid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import androidx.appcompat.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    //---the images to display---
    Integer[] imageIDs = {
            R.mipmap.butterfly,
            R.mipmap.windows,
            R.mipmap.ic_launcher
    };

    private ImageSwitcher imgSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imgSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        imgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        imgSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        GridView gridView = (GridView) findViewById(R.id.gridview);
        ImageAdapter adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(),"pic" + (position + 1) + " selected",Toast.LENGTH_SHORT).show();
                if(position == 0){
                    imgSwitcher.setImageResource(R.mipmap.butterfly);
                } else if(position == 1){
                    imgSwitcher.setImageResource(R.mipmap.windows);
                }else {
                    imgSwitcher.setImageResource(R.mipmap.ic_launcher);
                }
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        public ImageAdapter(Context c) { context = c; }

        //---returns the number of images---
        public int getCount() { return imageIDs.length;  }
        //---returns the item---
        public Object getItem(int position) { return position; }
        //---returns the ID of an item---
        public long getItemId(int position) { return position; }
        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imageIDs[position]);
            return imageView;
        }
    }
}



