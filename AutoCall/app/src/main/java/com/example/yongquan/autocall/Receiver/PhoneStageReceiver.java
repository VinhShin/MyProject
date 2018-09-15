package com.example.yongquan.autocall.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.Global_Variable;

public class PhoneStageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            Global_Variable.STATE_PHONE = "ringing";
            SharedPreferences sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
            boolean serviceActivated = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR, false);
            if(serviceActivated) {
                AlarmManager.actionCall(context, 15);
            }
            Log.d("YongQuan","ringring");
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            Global_Variable.STATE_PHONE = "offhook";
            Log.d("YongQuan","offhook");
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Global_Variable.STATE_PHONE = "idle";
            Log.d("YongQuan","idle");
        }

    }
}