package com.example.yongquan.autocall.Receiver;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Global.MyAsyncTaskDisConnect;
import static com.example.yongquan.autocall.Global.Global_Variable.childrenAlarmManager;


public class ChildrenAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (Global_Variable.sharedPreferences == null) {
                Global_Variable.sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
            }
            if (Global_Variable.STATE_PHONE.equals("idle")) {
                //alarm was be cancel in fuction actioncall
                AlarmManager.actionCall(context, 4);
                new MyAsyncTaskDisConnect().execute();
                Global_Function.addTimeToTal(context);
            } else {
                childrenAlarmManager.actionCheckCall(context, Global_Variable.TIME_DISTANCE_CHECK);
                saveToTalTimeCallInCall(Global_Variable.sharedPreferences, Global_Variable.TIME_DISTANCE_CHECK);

            }
        } catch (Exception e) {
            e.printStackTrace();
//            appendLog("loi -_- \n" + e.toString());
        }
    }

    private void saveToTalTimeCallInCall(SharedPreferences sharedPreferences, long totalTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Global_Variable.TOTAL_TIME_TEMP = sharedPreferences.getLong(Global_Variable.TOTAL_TIME_TEMP_STR, 0);
        editor.putLong(Global_Variable.TOTAL_TIME_TEMP_STR, Global_Variable.TOTAL_TIME_TEMP + totalTime);
        editor.apply();
    }


}
