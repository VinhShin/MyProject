package com.example.yongquan.autocall.Receiver;


import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;


public class DisconnectCallReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Global_Function.disconnectCall();
        Log.d("YongQuan2","end");
    }
}
