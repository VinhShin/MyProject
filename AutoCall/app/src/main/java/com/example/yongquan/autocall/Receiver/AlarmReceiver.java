package com.example.yongquan.autocall.Receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;

public class AlarmReceiver extends BroadcastReceiver {

    private static AlarmReceiver instance = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Global_Function.disconnectCall();
        Log.d("YongQuan","disconnet alarmReceiver");
        //Wake up every 6 hours
//        AlarmReceiver.getInstance().scheduleAlarm(context, 6);
    }


    public synchronized static AlarmReceiver getInstance() {
        if (instance == null) {
            instance = new AlarmReceiver();
        }

        return instance;
    }


    /**
     * Schedule Alarm in the specified time after the current time
     *
     * @param context Application Context
     */
    public void scheduleAlarm(Context context, int second) {

        /**
         * Remember add to Manifest  packageName.AlarmReceiver the receiver
         *   <receiver android:name="AlarmReceiver"/>
         */

        // Actual time plus y +   hour in milliseconds
        long millis = System.currentTimeMillis() + (second * 1000);

        Intent intentAlarm = new Intent(context, AlarmReceiver.class);

        // Get the Alarm Service.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    millis, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        } else if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, millis, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, millis, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        }
        // Set the alarm for a particular time.

        Log.i("Alarm Scheduled", "TEST");


    }
}