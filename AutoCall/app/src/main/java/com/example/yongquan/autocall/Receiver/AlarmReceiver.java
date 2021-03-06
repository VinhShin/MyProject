package com.example.yongquan.autocall.Receiver;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Global.MyAsyncTaskCall;
import com.example.yongquan.autocall.Global.MyAsyncTaskDisConnect;

import static com.example.yongquan.autocall.Global.Global_Variable.childrenAlarmManager;


public class AlarmReceiver extends WakefulBroadcastReceiver {

    int heSoWait = 1;
    int heSoConnect = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("YongQuan", "Phone State: " + Global_Variable.STATE_PHONE);
            getVariable(context);
            //disconnect
            if (intent.getAction().equals(Global_Variable.ACTION_CALL)) {
                childrenAlarmManager.actionCheckCall(context, Global_Variable.TIME_DISTANCE_CHECK);
                AlarmManager.disConnectCall(context, heSoConnect * Global_Variable.TIME_CONNECT);
//                Global_Function.SetPhoneStageListener(context);
                new MyAsyncTaskCall(context).execute();
            }//wait
            else {
                childrenAlarmManager.cancelAlarm();
                if (Global_Variable.SERVICE_IS_START) {
                    AlarmManager.actionCall(context, Global_Variable.TIME_WAITING * heSoWait + 2);
                }
                Global_Function.addTimeToTal(context);
                new MyAsyncTaskDisConnect().execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Global_Function.appendLog("bala"+e.toString() + " \n");
        }
    }

    private void getVariable(Context context) {
        if(Global_Variable.sharedPreferences==null) {
            Global_Variable.sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
        }
        Global_Variable.TIME_WAIT_MUNITE = Global_Variable.sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR, false);
        Global_Variable.TIME_WAITING = Global_Variable.sharedPreferences.getInt(Global_Variable.TIME_WAITING_STR, 9);
        Global_Variable.TIME_CONNECT_MUNITE = Global_Variable.sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR, true);
        Global_Variable.TIME_CONNECT = Global_Variable.sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR, 9);
        Global_Variable.SERVICE_IS_START = Global_Variable.sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR, false);
        if (Global_Variable.TIME_WAIT_MUNITE) {
            heSoWait = 60;
        }
        if (Global_Variable.TIME_CONNECT_MUNITE) {
            heSoConnect = 60;
        }
    }
}
