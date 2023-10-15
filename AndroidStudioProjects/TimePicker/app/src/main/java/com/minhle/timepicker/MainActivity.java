package com.minhle.timepicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TimePicker timePicker;
    DatePicker datePicker;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener()
    {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour)
        {
            hour = hourOfDay;
            minute = minuteOfHour;
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa"); Date date = new Date();
            String strDate = timeFormat.format(date);
            Toast.makeText(getBaseContext(),
                    "You have selected " + strDate,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View view) {
        Toast.makeText(getBaseContext(),"Date selected:" + (datePicker.getMonth() + 1) +"/" + datePicker.getDayOfMonth() +"/" + datePicker.getYear() + "\n" +"Time selected:" + timePicker.getHour() +":" + timePicker.getMinute(),Toast.LENGTH_SHORT).show();
    }
}