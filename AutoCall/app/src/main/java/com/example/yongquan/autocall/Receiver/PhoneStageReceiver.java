package com.example.yongquan.autocall.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.MyApp;
import com.example.yongquan.autocall.PhoneCallListener;
import com.example.yongquan.autocall.WakeLocker;

public class PhoneStageReceiver extends WakefulBroadcastReceiver{
    TelephonyManager telephony;
    @Override
    public void onReceive(Context context, Intent intent) {

        WakeLocker.acquire(context);
        final String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        SharedPreferences.Editor editor = MyApp.getContent().getSharedPreferences("YongQuan", Context.MODE_PRIVATE).edit();

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            Global_Variable.STATE_PHONE = "ringing";
            SharedPreferences sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
            boolean serviceActivated = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR, false);
            if(serviceActivated) {
                AlarmManager.cancelAlarm();
                AlarmManager.actionCall(context, 15);
            }
            Log.d("YongQuan","ringring");
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            Global_Variable.STATE_PHONE = "offhook";
            editor.putString(Global_Variable.PHONE_STATE_STR, Global_Variable.STATE_PHONE);
            Log.d("YongQuan","offhook");
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Global_Variable.STATE_PHONE = "idle";
            editor.putString(Global_Variable.PHONE_STATE_STR, Global_Variable.STATE_PHONE);
            Log.d("YongQuan","idle");
        }
        editor.commit();
        WakeLocker.release();
    }
}