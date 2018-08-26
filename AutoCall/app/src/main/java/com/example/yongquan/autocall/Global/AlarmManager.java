package com.example.yongquan.autocall.Global;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yongquan.autocall.Receiver.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

public class AlarmManager {

    static PendingIntent sender;
    static android.app.AlarmManager alarmManager;
    public AlarmManager(){}

    public static void actionCall(Context context, long timeSecond){
        Log.d("YongQuan1","action call");
        cancelAlarm();
        Intent receiverIntent = new Intent(context, AlarmReceiver.class);
        receiverIntent.setAction(Global_Variable.ACTION_CALL);
        sender = PendingIntent.getBroadcast(context, 123456789, receiverIntent, 0);
        alarmManager = (android.app.AlarmManager)context.getSystemService(ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact (android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        }

    }
    public static void disConnectCall(Context context, long timeSecond){
        Intent receiverIntent = new Intent(context, AlarmReceiver.class);
        receiverIntent.setAction(Global_Variable.ACTION_DISCONNECT);
        sender = PendingIntent.getBroadcast(context, 123456789, receiverIntent, 0);
        alarmManager = (android.app.AlarmManager)context.getSystemService(ALARM_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact (android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        }
        Log.d("YongQuan2","start");
        Log.d("YongQuan2",timeSecond+"");

    }
    public static void cancelAlarm(){
        if(alarmManager!=null && sender!=null) {
            Global_Function.disconnectCall();
            alarmManager.cancel(sender);
        }
    }
    public static boolean isLive(){
        return alarmManager!=null;
    }
}
