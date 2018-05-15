package com.example.ajit.demoalaram;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;


public class RingtoneService extends Service {
    MediaPlayer mediaPlayer;
    boolean isRunning;
    int stateIds;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("@@@@", "in service class");

        String state = intent.getExtras().getString("extra");
        Log.e("Ringtone state", state);




        assert state != null;
        switch (state) {
            case "alarm_on":
                stateIds = 1;
                break;
            case "alarm_off":
                stateIds = 0;
                break;
            default:
                stateIds = 0;
                break;
        }

        //if there is no music playing and the user press "alarm on"
        //music should start playing
        if (!this.isRunning && stateIds ==1) {
            Log.e("there is no music, ", "and you want start");
            mediaPlayer = MediaPlayer.create(this, R.raw.alarm_tone);
            mediaPlayer.start();

            isRunning=true;
            stateIds=0;
            NotificationManager notify_manager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            // set up an intent that goes to the Main Activity
            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
            // set up a pending intent
            PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                    intent_main_activity, 0);

            // make the notification parameters
            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off!")
                    .setContentText("Click me!")
                    .setSmallIcon(R.drawable.ic_notofication)
                    .setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true)
                    .build();
            notify_manager.notify(0, notification_popup);
        }
        //if music is playing and the user press "alarm off"
        //music should stop
        else if (this.isRunning && stateIds == 0) {
            Log.e("there is  music, ", "and you want end");
            mediaPlayer.stop();
            mediaPlayer.reset();
            isRunning=false;
            stateIds=0;


        }
        //if no music playing and the user press "alarm off"
        //do nothing
        else if (!this.isRunning && stateIds ==0) {
            Log.e("there is no music, ", "and you want end");
            isRunning=false;
            stateIds=0;
        }
        //if music playing and press the "alarm on "
        //do nothing
        else if (this.isRunning && stateIds==1) {
            Log.e("there is  music, ", "and you want start");
            isRunning=true;
            stateIds=1;
        }
        else{
            Log.e("else ", "some how u reach here");
        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("ondestroyed called","hmmm");
        super.onDestroy();
        isRunning=false;
    }


}
