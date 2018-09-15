package com.example.yongquan.autocall.Global;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyAsyncTaskCall extends AsyncTask<Void, Integer, Void> {
        Context context;
        public MyAsyncTaskCall(Context context) {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (Global_Variable.date == null) {
                    Global_Variable.date = new Date();
                }
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(Global_Variable.date);
                SharedPreferences sharedPreferences = context.getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String str = sharedPreferences.getString("contact_t", "");
                Global_Variable.listContact_temp = Global_Function.convertStringToArray(str);

                if (Global_Variable.listContact_temp == null) {
                    str = sharedPreferences.getString("contact", "");
                    Global_Variable.listContact_temp = Global_Function.convertStringToArray(str);
                    editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact_temp));
                }
                Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR, 0);
                Global_Variable.TIME_START = sharedPreferences.getString(Global_Variable.TIME_START_STR, "00:00");
                Global_Variable.TIME_END = sharedPreferences.getString(Global_Variable.TIME_END_STR, "23:59");
                Global_Variable.SMS_UNABLE = sharedPreferences.getBoolean(Global_Variable.SMS_UNABLE_STR, false);
                Global_Variable.SERVICE_IS_START = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR, false);
                if (Global_Variable.SMS_UNABLE) {
                    checkSendSMS(sharedPreferences, calendar);
                }
                int TIME_START = (Integer.valueOf(Global_Variable.TIME_START.split(":")[0])) * 60 + (Integer.valueOf(Global_Variable.TIME_START.split(":")[1]));
                int TIME_END = (Integer.valueOf(Global_Variable.TIME_END.split(":")[0])) * 60 + (Integer.valueOf(Global_Variable.TIME_END.split(":")[1]));

                if (Global_Variable.random == null) {
                    Global_Variable.random = new Random();
                }
                Global_Variable.INDEX_PHONE = Global_Variable.random.nextInt(Global_Variable.listContact_temp.size());

                if (!Global_Variable.SERVICE_IS_START || TIME_START == TIME_END ||
                        TIME_START > TIME_END ||
                        calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) < TIME_START ||
                        calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) > TIME_END) {
                    return null;
                }
                //call
                String phone =  Global_Variable.listContact_temp.get(Global_Variable.INDEX_PHONE).getPhone();
                if (Global_Variable.callIntent == null) {
                    Global_Variable.callIntent = new Intent(Intent.ACTION_CALL);
                    Global_Variable.callIntent.setData(Uri.parse("tel:" + phone));
                    Global_Variable.callIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(Global_Variable.callIntent);


//                callTo(context, Global_Variable.listContact_temp.get(Global_Variable.INDEX_PHONE).getPhone());
                    editor.putInt(Global_Variable.INDEX_PHONE_STR, Global_Variable.INDEX_PHONE);
                    Global_Variable.listContact_temp.remove(Global_Variable.INDEX_PHONE);
                    editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact_temp));
                    editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
//                appendLog("hy: "+e.getMessage().toString() + " \n");
            }
            return null;
        }
    public static void checkSendSMS(SharedPreferences sharedPreferences, Calendar calendar) {
        Global_Variable.TIME_SEND_SMS = sharedPreferences.getInt(Global_Variable.TIME_SEND_SMS_STR, 7);
        Global_Variable.DAY_SEND_SMS = sharedPreferences.getInt(Global_Variable.DAY_SEND_SMS_STR, 7);
        Global_Variable.WAS_SEND_SMS = sharedPreferences.getBoolean(Global_Variable.WAS_SEND_SMS_STR, false);
        Global_Variable.SMS_CONTENT = sharedPreferences.getString(Global_Variable.SMS_CONTENT_STR, "");
        Global_Variable.SMS_SENDTO = sharedPreferences.getString(Global_Variable.SMS_SENDTO_STR, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
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

            editor.putLong(Global_Variable.TOTAL_TIME_CALL_STR, Global_Variable.TOTAL_TIME_CALL);
            editor.putInt(Global_Variable.CALL_SUCCESS_STR, 0);
            editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
        }
        if (Global_Variable.WAS_SEND_SMS && ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && Global_Variable.DAY_SEND_SMS == 8) ||
                calendar.get(Calendar.DAY_OF_WEEK) > Global_Variable.DAY_SEND_SMS)) {

            Global_Variable.WAS_SEND_SMS = false;
            editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
        }
        editor.apply();
    }
    }