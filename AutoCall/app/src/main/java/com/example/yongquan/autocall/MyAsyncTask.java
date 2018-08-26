package com.example.yongquan.autocall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by DELL on 7/31/2018.
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    Context contextParent;
    SharedPreferences sharedPreferences;
    int TIME_START = 0;
    int TIME_END = 0;
    long currentTime = 0;
    Date date; // given date
    Calendar calendar;

    public MyAsyncTask(Context contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        date = new Date();
        calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        sharedPreferences = contextParent.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("contact_t", "");
        Global_Variable.listContact_temp = Global_Function.convertStringToArray(str);
        if (Global_Variable.listContact_temp == null) {
            str = sharedPreferences.getString("contact", "");
            Global_Variable.listContact_temp = Global_Function.convertStringToArray(str);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact_temp));
        }

        Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR, 0);
        Global_Variable.TIME_START = sharedPreferences.getString(Global_Variable.TIME_START_STR, "00:00");
        Global_Variable.TIME_END = sharedPreferences.getString(Global_Variable.TIME_END_STR, "23:59");
        Global_Variable.SMS_UNABLE = sharedPreferences.getBoolean(Global_Variable.SMS_UNABLE_STR, false);
        Global_Variable.SERVICE_IS_START = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR, false);
        if (Global_Variable.SMS_UNABLE) {
            Global_Variable.TIME_SEND_SMS = sharedPreferences.getInt(Global_Variable.TIME_SEND_SMS_STR, 7);
            Global_Variable.DAY_SEND_SMS = sharedPreferences.getInt(Global_Variable.DAY_SEND_SMS_STR, 7);
            Global_Variable.WAS_SEND_SMS = sharedPreferences.getBoolean(Global_Variable.WAS_SEND_SMS_STR, false);
            Global_Variable.SMS_CONTENT = sharedPreferences.getString(Global_Variable.SMS_CONTENT_STR, "");
            Global_Variable.SMS_SENDTO = sharedPreferences.getString(Global_Variable.SMS_SENDTO_STR, "");
        }
        TIME_START = (Integer.valueOf(Global_Variable.TIME_START.split(":")[0])) * 60 + (Integer.valueOf(Global_Variable.TIME_START.split(":")[1]));
        TIME_END = (Integer.valueOf(Global_Variable.TIME_END.split(":")[0])) * 60 + (Integer.valueOf(Global_Variable.TIME_END.split(":")[1]));

        Random rand = new Random();
        Global_Variable.INDEX_PHONE = rand.nextInt(Global_Variable.listContact_temp.size());

    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!Global_Variable.SERVICE_IS_START) {
            return null;
        }

        if (Global_Variable.SMS_UNABLE) {
            if (!Global_Variable.WAS_SEND_SMS && (
                    Global_Variable.DAY_SEND_SMS == calendar.get(Calendar.DAY_OF_WEEK) ||
                            (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && Global_Variable.DAY_SEND_SMS == 8)) &&
                    calendar.get(Calendar.HOUR_OF_DAY) == Global_Variable.TIME_SEND_SMS) {
                Global_Variable.CALL_SUCCESS = sharedPreferences.getInt(Global_Variable.CALL_SUCCESS_STR, 0);
                long thoigiangoi = Global_Variable.TOTAL_TIME_CALL / 60;
                long thoigiangois = Global_Variable.TOTAL_TIME_CALL % 60;
                String messageToSend = Global_Variable.SMS_CONTENT +
                        " \n " + "Tong thoi gian goi : " + thoigiangoi + " phut " + thoigiangois + " giay \n " +
                        "So cuoc goi thanh cong : " + Global_Variable.CALL_SUCCESS;
                String number = Global_Variable.SMS_SENDTO;

                SmsManager sms = SmsManager.getDefault();
                ArrayList<String> parts = sms.divideMessage(messageToSend);
                sms.sendMultipartTextMessage(number, null, parts, null, null);

                Global_Variable.WAS_SEND_SMS = true;
                Global_Variable.TOTAL_TIME_CALL = 0;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(Global_Variable.TOTAL_TIME_CALL_STR, Global_Variable.TOTAL_TIME_CALL);
                editor.putInt(Global_Variable.CALL_SUCCESS_STR, 0);
                editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
                editor.apply();
                //set lai

            }
//            Log.d("bala", Global_Variable.WAS_SEND_SMS + "");
            //qua chi ky moi
            if (Global_Variable.WAS_SEND_SMS && ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && Global_Variable.DAY_SEND_SMS == 8) ||
                    calendar.get(Calendar.DAY_OF_WEEK) > Global_Variable.DAY_SEND_SMS)) {

                Global_Variable.WAS_SEND_SMS = false;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
                editor.apply();
            }
        }
        if (TIME_START == TIME_END ||
                TIME_START > TIME_END ||
                calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) < TIME_START ||
                calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) > TIME_END) {
            return null;
        }
        Log.d("YongQuan", "-_-");
        if (Global_Variable.SERVICE_IS_START && Global_Variable.STATE_PHONE == "idle") {
            Log.d("YongQuan", "=))");
            Global_Function.callTo(contextParent, Global_Variable.listContact_temp.get(Global_Variable.INDEX_PHONE).getPhone());
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Global_Variable.INDEX_PHONE_STR, Global_Variable.INDEX_PHONE);
        Global_Variable.listContact_temp.remove(Global_Variable.INDEX_PHONE);
        editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact_temp));
        editor.apply();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }


}