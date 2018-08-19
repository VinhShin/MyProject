package com.example.yongquan.autocall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Model.Contact;
import com.example.yongquan.autocall.Receiver.AlarmReceiver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by DELL on 7/31/2018.
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    Context contextParent;
    SharedPreferences sharedPreferences;
    int checkTime;
    int hesoWait;
    int hesoConnect;
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

        hesoWait = 1;
        hesoConnect = 1;
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
        Log.d("YongQuans", "list :" + str);

        Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR, 0);
        Global_Variable.TIME_CONNECT = sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR, 9);
        Global_Variable.TIME_WAITING = sharedPreferences.getInt(Global_Variable.TIME_WAITING_STR, 9);
        Global_Variable.TIME_CONNECT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR, true);
        Global_Variable.TIME_WAIT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR, false);
        Global_Variable.TIME_START = sharedPreferences.getString(Global_Variable.TIME_START_STR, "00:00");
        Global_Variable.TIME_END = sharedPreferences.getString(Global_Variable.TIME_END_STR, "23:59");
        Global_Variable.TOTAL_TIME_CALL = sharedPreferences.getLong(Global_Variable.TOTAL_TIME_CALL_STR, 0);
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
                Log.d("YongQuana", "gui lai");
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
            try {
                Thread.sleep(5000);
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        try {
            Thread.sleep(1000);

            //kiểm tra thêm lần nữa mắc công. haha
            if (!Global_Variable.SERVICE_IS_START) {
                return null;
            }

            callTo(Global_Variable.listContact_temp.get(Global_Variable.INDEX_PHONE).getPhone());
            Log.d("YongQuans", "phone :" + Global_Variable.listContact_temp.get(Global_Variable.INDEX_PHONE).getPhone());
            //set time endcall
            if (Global_Variable.TIME_CONNECT_MUNITE) {
                hesoConnect = 60;
            }
            if (Global_Variable.TIME_WAIT_MUNITE) {
                hesoWait = 60;
            }
//            AlarmReceiver.getInstance().scheduleAlarm(contextParent, Global_Variable.TIME_CONNECT * hesoConnect);
            Global_Function.DisconnectCall(contextParent,Global_Variable.TIME_CONNECT * hesoConnect);
            currentTime = System.currentTimeMillis();
            Log.d("YongQuan", "time before " + currentTime);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Global_Variable.INDEX_PHONE_STR, Global_Variable.INDEX_PHONE);
            Global_Variable.listContact_temp.remove(Global_Variable.INDEX_PHONE);
            editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact_temp));
            editor.apply();
            Log.d("YongQuan", "Wait: " + Global_Variable.TIME_CONNECT_MUNITE);

            boolean check_Sucees = false;
            checkTime = 0;
            while (true) {
                Thread.sleep(1000);
                Log.d("YongQuan", "check current time :" + checkTime + "/" + Global_Variable.TIME_CONNECT * hesoConnect);
                checkTime += 1;
                if(checkTime%10==0){
                    publishProgress(1);
                }
                if (MainActivity.STATE_PHONE == "idle") {
                    Log.d("YongQuans", "continue1");
                    //                    Thread.sleep(Global_Variable.TIME_WAITING*1000 * hesoWait);
                    Thread.sleep(5000);
                    break;
                }
                if (!Global_Variable.SERVICE_IS_START) {
                    Thread.sleep(5000);
                    break;
                }


            }
            if (checkTime > 55 && (checkTime < Global_Variable.TIME_CONNECT * hesoConnect)) {

                Thread.sleep(Global_Variable.TIME_WAITING * 1000 * hesoWait + 1000);
            }
            Log.d("YongQuan", "time before " + System.currentTimeMillis());
            if (System.currentTimeMillis() - currentTime > 45000) {
                Log.d("YongQuan", "call success");
                check_Sucees = true;
            }
            if (check_Sucees) {
                Global_Variable.CALL_SUCCESS = sharedPreferences.getInt(Global_Variable.CALL_SUCCESS_STR, 0);
                Global_Variable.CALL_SUCCESS++;
                editor.putInt(Global_Variable.CALL_SUCCESS_STR, Global_Variable.CALL_SUCCESS);
            }
            Global_Variable.TOTAL_TIME_CALL += checkTime;
            editor.putLong(Global_Variable.TOTAL_TIME_CALL_STR, Global_Variable.TOTAL_TIME_CALL);
            editor.apply();

        } catch (InterruptedException e) {
            Log.d("YongQuans", e.toString());
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Global_Variable.myAsyncTask = null;
        if (Global_Variable.SERVICE_IS_START) {
            Global_Variable.myAsyncTask = new MyAsyncTask(contextParent);
            Global_Variable.myAsyncTask.execute();
        }
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        contextParent.startActivity(new Intent(contextParent.getApplicationContext(), MainActivity.class));
        super.onProgressUpdate(values);
    }
    private void callTo(String phone) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager packageManager = contextParent.getPackageManager();
        List activities = packageManager.queryIntentActivities(callIntent, PackageManager.MATCH_DEFAULT_ONLY);

        for (int j = 0; j < activities.size(); j++) {

            if (activities.get(j).toString().toLowerCase().contains("com.android.phone")) {
                callIntent.setPackage("com.android.phone");
            } else if (activities.get(j).toString().toLowerCase().contains("call")) {
                String pack = (activities.get(j).toString().split("[ ]")[1].split("[/]")[0]);
                callIntent.setPackage(pack);
            }
        }

        if (ActivityCompat.checkSelfPermission(contextParent, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        contextParent.startActivity(callIntent);

    }

}