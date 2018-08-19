package com.example.yongquan.autocall.Receiver;


import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Service.WakeupService;

public class DisconnectCallReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // This is the Intent to deliver to our service.
//        Intent service = new Intent(context, WakeupService.class);
        Global_Function.disconnectCall();

        // Start the service, keeping the device awake while it is launching.
//        Log.i("DisconnectCallReceiver", "Starting service @ " + SystemClock.elapsedRealtime());
//        startWakefulService(context, service);
    }
}
