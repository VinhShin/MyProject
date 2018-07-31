package com.example.yongquan.autocall;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by DELL on 7/29/2018.
 */

public class PhoneCallListener extends PhoneStateListener {

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        if (state == TelephonyManager.CALL_STATE_RINGING) {
            MainActivity.STATE_PHONE = "ringing";
            Log.d("YongQuan1","ringing");
        }

        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
            MainActivity.STATE_PHONE = "offhook";
            Log.d("YongQuan1","offhook");
        }

        if (state == TelephonyManager.CALL_STATE_IDLE) {
            MainActivity.STATE_PHONE = "idle";
            Log.d("YongQuan1","idle");
        }
    }
}
