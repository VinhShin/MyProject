package com.example.yongquan.autocall.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;

/**
 * Created by DELL on 8/2/2018.
 */

public class NotificationDismissedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getExtras().getInt("com.stack.notificationId");
        Log.d("TAG","GET "+notificationId);
        Global_Function.sendNotification(context,"Ứng dụng đang chạy ngầm",1);
  /* Your code to handle the event here */
    }
}