package com.example.yongquan.autocall.Receiver;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.ChildrenAlarmManager;
import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.MyAsyncTask;

import static com.example.yongquan.autocall.Global.Global_Variable.childrenAlarmManager;


public class AlarmReceiver extends WakefulBroadcastReceiver {

    int heSoWait = 1;
    int heSoConnect = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
//        PowerManager.WakeLock mWakeLock;
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "My Tag");
//        mWakeLock.acquire();

        Global_Function.SetPhoneStageListener(context);
        Log.d("YongQuan","Phone State: "+Global_Variable.STATE_PHONE);
        getVariable(context);

        //disconnect
        if(intent.getAction().equals(Global_Variable.ACTION_CALL)){
            //start call
            Global_Variable.myAsyncTask = new MyAsyncTask(context);
            Global_Variable.myAsyncTask.execute();
            //start time disconnect
            Log.d("Alarm","Alarm");
//            if(Global_Variable.alarmManager!=null) {
//                Log.d("Alarm","not null. haha");
//                Global_Variable.alarmManager.disConnectCall(context, heSoConnect * Global_Variable.TIME_CONNECT);
//            }
            AlarmManager.disConnectCall(context, heSoConnect * Global_Variable.TIME_CONNECT);
            //check call not success
            childrenAlarmManager = new ChildrenAlarmManager();
            childrenAlarmManager.actionCheckCall(context,Global_Variable.TIME_DISTANCE_CHECK);
        }//wait
        else {
            if(childrenAlarmManager!=null){
                Log.d("YongQuan","Kill Chidren Alarm");
                childrenAlarmManager.cancelAlarm();
            }
            Global_Function.addTimeToTal(context);
            Global_Function.disconnectCall();
            if(Global_Variable.SERVICE_IS_START) {
                AlarmManager.actionCall(context, Global_Variable.TIME_WAITING * heSoWait + 2);
            }
        }
    }
    private void getVariable(Context context){
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
        Global_Variable.TIME_WAIT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR, false);
        Global_Variable.TIME_WAITING = sharedPreferences.getInt(Global_Variable.TIME_WAITING_STR, 9);
        Global_Variable.TIME_CONNECT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR, true);
        Global_Variable.TIME_CONNECT = sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR, 9);
        if (Global_Variable.TIME_WAIT_MUNITE) {
            heSoWait = 60;
        }
        if (Global_Variable.TIME_CONNECT_MUNITE) {
            heSoConnect = 60;
        }
    }
}
