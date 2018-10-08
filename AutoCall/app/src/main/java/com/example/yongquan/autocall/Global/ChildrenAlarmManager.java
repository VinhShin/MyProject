package com.example.yongquan.autocall.Global;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.yongquan.autocall.Receiver.ChildrenAlarmReceiver;
import static android.content.Context.ALARM_SERVICE;

public class ChildrenAlarmManager {

    private static PendingIntent sender;
    private static android.app.AlarmManager alarmManager;

    public static void actionCheckCall(Context context, long timeSecond) {
        if (Global_Variable.receiverIntentChild == null) {
            Global_Variable.receiverIntentChild = new Intent(context, ChildrenAlarmReceiver.class);
        }
        if(sender==null) {
        sender = PendingIntent.getBroadcast(context, 103, Global_Variable.receiverIntentChild, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (android.app.AlarmManager) context.getSystemService(ALARM_SERVICE);
        }
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact (android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+timeSecond * 1000, sender);
        } else {
            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeSecond * 1000, sender);
        }

//        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(context, ChildrenAlarmReceiver.class);
//        notificationIntent.setAction(Global_Variable.ACTION_CALL);
//        PendingIntent broadcast = PendingIntent.getBroadcast(context, 103, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, (int)timeSecond);
////        cal.add(Calendar.MINUTE, 5);
//
//        if (android.os.Build.VERSION.SDK_INT >= 19) {
//            alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
//        } else {
//            alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeSecond * 1000, sender);
//        }
    }

    public static void cancelAlarm() {
        if (alarmManager != null && sender != null) {
            alarmManager.cancel(sender);
        }
    }
}
