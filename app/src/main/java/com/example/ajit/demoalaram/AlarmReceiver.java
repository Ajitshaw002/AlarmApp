package com.example.ajit.demoalaram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("@@@@","in receiver");

        String getvalue=intent.getExtras().getString("extra");
        Log.e("what is the key ",getvalue);
        Intent intent1=new Intent(context,RingtoneService.class);
        intent1.putExtra("extra",getvalue);
        context.startService(intent1);
    }
}
