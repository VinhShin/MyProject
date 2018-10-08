package com.example.yongquan.autocall;

import android.content.Context;
import android.content.SharedPreferences;
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
        SharedPreferences.Editor editor = MyApp.getContent().getSharedPreferences("YongQuan", Context.MODE_PRIVATE).edit();
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            Global_Variable.STATE_PHONE = "ringing";
            Log.d("YongQuan1","ringing");
        }

        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
            Global_Variable.STATE_PHONE = "offhook";
            editor.putString(Global_Variable.PHONE_STATE_STR, Global_Variable.STATE_PHONE);
            Log.d("state","offhook");
            Global_Variable.statusIdel=0;
        }

        if (state == TelephonyManager.CALL_STATE_IDLE) {
            Log.d("state","idle");
            if(Global_Variable.statusIdel>2){
                editor.putString(Global_Variable.PHONE_STATE_STR, Global_Variable.STATE_PHONE);
                Global_Variable.STATE_PHONE ="idle";
            }
            Global_Variable.statusIdel++;
        }
        editor.commit();
    }
}
