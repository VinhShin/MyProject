package com.example.yongquan.autocall;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Variable;

/**
 * Created by DELL on 7/29/2018.
 */

public class PhoneCallListener extends PhoneStateListener {

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        if (state == TelephonyManager.CALL_STATE_RINGING) {
            MainActivity.STATE_PHONE = "ringing";
            Log.d("YongQuan","ringing");
        }

        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
            MainActivity.STATE_PHONE = "offhook";
            Log.d("YongQuan","offhook");
            Global_Variable.statusIdel=0;
        }

        if (state == TelephonyManager.CALL_STATE_IDLE) {
            Log.d("YongQuan","idle");
            if(Global_Variable.statusIdel>4){
                MainActivity.STATE_PHONE ="idle";
            }
            Global_Variable.statusIdel++;
        }
    }
}
