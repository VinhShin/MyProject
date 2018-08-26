package com.example.yongquan.autocall.Receiver;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.solver.Goal;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.MainActivity;
import com.example.yongquan.autocall.MyAsyncTask;

import static com.example.yongquan.autocall.Global.Global_Variable.childrenAlarmManager;


public class ChildrenAlarmReceiver extends WakefulBroadcastReceiver {

    int heSoWait = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
//        PowerManager.WakeLock mWakeLock;
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "My Tag");
//        mWakeLock.acquire();

        Global_Function.SetPhoneStageListener(context);
        Log.d("YongQuan","phone state:"+Global_Variable.STATE_PHONE);
        Log.d("YongQuan","children be call");
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
        Global_Function.SetPhoneStageListener(context);
        if (Global_Variable.STATE_PHONE.equals("idle")) {
//            Global_Variable.alarmManager.cancelAlarm();//kill alarm
//            Global_Variable.alarmManager.actionCall(context, 3);
//            Global_Function.addTimeToTal(context);
            AlarmManager.cancelAlarm();
            AlarmManager.actionCall(context,3);
            Global_Function.addTimeToTal(context);
        } else if(childrenAlarmManager!=null) {
            childrenAlarmManager.actionCheckCall(context, Global_Variable.TIME_DISTANCE_CHECK);
            saveToTalTimeCallInCall(sharedPreferences,Global_Variable.TIME_DISTANCE_CHECK);
        }
    }
    private void saveToTalTimeCallInCall(SharedPreferences sharedPreferences,long totalTime){
        Log.d("YongQuan","Save total time : "+totalTime);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Global_Variable.TOTAL_TIME_TEMP = sharedPreferences.getLong(Global_Variable.TOTAL_TIME_TEMP_STR, 0);
        editor.putLong(Global_Variable.TOTAL_TIME_TEMP_STR, Global_Variable.TOTAL_TIME_TEMP+totalTime);
        editor.apply();
    }

}
