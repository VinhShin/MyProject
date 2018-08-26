package com.example.yongquan.autocall.Global;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yongquan.autocall.Receiver.ChildrenAlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

public class ChildrenAlarmManager {

    PendingIntent sender;
    android.app.AlarmManager alarmManager;
    public ChildrenAlarmManager(){}

    public void actionCheckCall(Context context, long timeSecond){
        Intent receiverIntent = new Intent(context, ChildrenAlarmReceiver.class);
        sender = PendingIntent.getBroadcast(context, 123456789, receiverIntent, 0);
        alarmManager = (android.app.AlarmManager)context.getSystemService(ALARM_SERVICE);
        cancelAlarm();
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact (android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        }
    }

    public void cancelAlarm(){
        if(alarmManager!=null && sender!=null) {
            alarmManager.cancel(sender);
        }
    }
}
