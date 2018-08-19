package com.example.yongquan.autocall.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Service.AutoCallService;

public class AutoStart extends BroadcastReceiver {

    // Method is called after device bootup is complete
    public void onReceive(final Context context, Intent arg1) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
        boolean serviceActivated = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR, false);
        Log.d("YongQuan","Check start service: "+serviceActivated);
        if (serviceActivated) {
            Log.d("YongQuan","restart service");
            context.startService(new Intent(context, AutoCallService.class));
            Global_Function.sendNotification(context,"Ứng dụng đang chạy ngầm",1);
//            Intent serviceIntent = new Intent(context, MainActivity.class);
//            context.startService(serviceIntent);
        }
        Log.d("YongQuan","action "+arg1.getAction());

    }

}
