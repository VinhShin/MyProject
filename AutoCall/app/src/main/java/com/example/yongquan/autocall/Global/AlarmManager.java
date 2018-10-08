package com.example.yongquan.autocall.Global;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yongquan.autocall.Receiver.AlarmReceiver;

import java.util.Calendar;

import static android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP;
import static android.content.Context.ALARM_SERVICE;

public class AlarmManager {

    static PendingIntent sender;
    static android.app.AlarmManager alarmManager;
    public AlarmManager(){}

    public static void actionCall(Context context, long timeSecond){
            if(Global_Variable.receiverIntentCall==null) {
                Global_Variable.receiverIntentCall = new Intent(context, AlarmReceiver.class);
                Global_Variable.receiverIntentCall.setAction(Global_Variable.ACTION_CALL);
            }
            sender = PendingIntent.getBroadcast(context, 101, Global_Variable.receiverIntentCall, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
            cancelAlarm();
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeSecond * 1000, sender);
            } else {
                alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeSecond * 1000, sender);
            }

    }
    public static void disConnectCall(Context context, long timeSecond){
        Log.d("YongQuan","disconnect be call");
        if(Global_Variable.receiverIntentDisCC==null) {
            Global_Variable.receiverIntentDisCC = new Intent(context, AlarmReceiver.class);
            Global_Variable.receiverIntentDisCC.setAction(Global_Variable.ACTION_DISCONNECT);
        }
        sender = PendingIntent.getBroadcast(context, 102, Global_Variable.receiverIntentDisCC, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (android.app.AlarmManager)context.getSystemService(ALARM_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact (android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        }

    }
    public static void cancelAlarm(){
        if(alarmManager!=null && sender!=null) {
            alarmManager.cancel(sender);
            ChildrenAlarmManager.cancelAlarm();
        }
    }
}
