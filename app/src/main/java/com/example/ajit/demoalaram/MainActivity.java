package com.example.ajit.demoalaram;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TimePicker time_picker;
    private Button btn_alarm_on, btn_alarm_off;
    private AlarmManager alarmManager;
    private TextView tv_status;
    private Calendar calendar;
    private Intent intent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        btn_alarm_off = findViewById(R.id.btn_alarm_off);
        btn_alarm_on = findViewById(R.id.btn_alarm_on);
        tv_status = findViewById(R.id.tv_status);
        time_picker = findViewById(R.id.time_picker);
        calendar = Calendar.getInstance();
        btn_alarm_off.setOnClickListener(this);
        btn_alarm_on.setOnClickListener(this);
        intent=new Intent(getApplicationContext(),AlarmReceiver.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alarm_on:
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, time_picker.getHour());
                calendar.set(Calendar.MINUTE, time_picker.getMinute());
                int hour = time_picker.getHour();
                int minuts = time_picker.getMinute();
                String mainHoue = String.valueOf(hour);
                String mainMinute = String.valueOf(minuts);
                if (hour > 12) {
                    //24 to 12 format
                mainHoue=String.valueOf(hour - 12);
                }
                if (minuts < 10) {
                    // 10:7--> 10:07
                   mainMinute="0" + String.valueOf(minuts);
                }

                setTextOn("Alarm On at " + mainHoue + ":" + mainMinute);
                intent.putExtra("extra","alarm_on");
                pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                // Check if we're running on Android 5.0 or higher
                long time=calendar.getTimeInMillis()-(23*1000);
                //alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    // Call some material design APIs here
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);

                } else {
                    // Implement this feature without material design
                    alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
                }

                break;

            case R.id.btn_alarm_off:
                setTextOff("Alarm Off!!");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","alarm_off");
                sendBroadcast(intent);
                break;
        }
    }

    private void setTextOff(String s) {
        tv_status.setText(s);
    }

    private void setTextOn(String s) {
        tv_status.setText(s);
    }
}
