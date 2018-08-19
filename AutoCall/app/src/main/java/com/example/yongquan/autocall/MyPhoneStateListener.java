package com.example.yongquan.autocall;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Variable;

public class MyPhoneStateListener extends PhoneStateListener {

    public static Boolean phoneRinging = false;

    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("YongQuan1", "IDLE");
                MainActivity.STATE_PHONE = "idle";
                phoneRinging = false;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("YongQuan1", "OFFHOOK");
                MainActivity.STATE_PHONE = "offhook";

                break;
            case TelephonyManager.CALL_STATE_RINGING:
                MainActivity.STATE_PHONE = "ringing";
                phoneRinging = true;

                break;
        }
    }
}