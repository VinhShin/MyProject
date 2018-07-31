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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by DELL on 7/31/2018.
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    Context contextParent;
    SharedPreferences sharedPreferences;
    int checkTime = 0;
    int hesoWait = 1;
    int hesoConnect = 1;
    public MyAsyncTask(Context contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        hesoWait = 1;
        hesoConnect = 1;

        sharedPreferences = contextParent.getSharedPreferences("YongQuan",Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("contact","");
        MyService.listContact = Global_Function.convertStringToArray(str);
        if(MyService.listContact==null){
            MyService.listContact =new ArrayList<Contact>();
        }
        Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR,0);
        Global_Variable.TIME_CONNECT = sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR,0);
        Global_Variable.TIME_CONNECT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR,true);
        Global_Variable.TIME_WAIT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR,true);
        Global_Variable.TIME_START = sharedPreferences.getInt(Global_Variable.TIME_START_STR,0);
        Global_Variable.TIME_END = sharedPreferences.getInt(Global_Variable.TIME_END_STR,0);
        Global_Variable.TIME_SEND_SMS = sharedPreferences.getInt(Global_Variable.TIME_SEND_SMS_STR,1);
        Global_Variable.DAY_SEND_SMS = sharedPreferences.getInt(Global_Variable.DAY_SEND_SMS_STR,1);
        Global_Variable.WAS_SEND_SMS = sharedPreferences.getBoolean(Global_Variable.WAS_SEND_SMS_STR,false);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        Log.d("Yong","current time : "+calendar.get(Calendar.HOUR_OF_DAY) + "time start : "+
                Global_Variable.TIME_START + " time end : "+Global_Variable.TIME_END);
        Log.d("bala",Global_Variable.WAS_SEND_SMS+"");
        if(!Global_Variable.WAS_SEND_SMS&&(
                Global_Variable.DAY_SEND_SMS == calendar.get(Calendar.DAY_OF_WEEK)||
                (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY&&Global_Variable.DAY_SEND_SMS ==8))&&
                calendar.get(Calendar.HOUR_OF_DAY)==Global_Variable.TIME_SEND_SMS)
        {
            Global_Variable.WAS_SEND_SMS=true;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
            editor.apply();

//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:01675027634"));
//            intent.putExtra("sms_body", "Ứng dụng còn tồn tại");
//            startActivity(intent);
            Log.d("YongYong","Was Send");
//            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", "01675027634", null));
//            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            sendIntent.putExtra("sms_body", "ung dụng còn sống");
//            contextParent.startActivity(sendIntent);
            String messageToSend = "ung dung con song";
            String number = "01675027634";

            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
        }
        Log.d("bala",Global_Variable.WAS_SEND_SMS+"");
        //qua chi ky moi
        if(Global_Variable.WAS_SEND_SMS&&calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY && Global_Variable.DAY_SEND_SMS ==8){
            Global_Variable.WAS_SEND_SMS=false;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
            editor.apply();
        }
        Log.d("bala",Global_Variable.WAS_SEND_SMS+"");
        if((Global_Variable.TIME_START == 0 && Global_Variable.TIME_END == 0) ||
                calendar.get(Calendar.HOUR_OF_DAY)<Global_Variable.TIME_START ||
                calendar.get(Calendar.HOUR_OF_DAY) > Global_Variable.TIME_END)
        {
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

           // Log.d("YongQuan","is running");
           // Log.d("YongQuan","phone stage "+MainActivity.STATE_PHONE);
           // Log.d("YongQuan","Size " + MyService.listContact.size());
            if(Global_Variable.INDEX_PHONE>MyService.listContact.size()-1){
                Global_Variable.INDEX_PHONE = 0;
            }
            callTo(MyService.listContact.get(Global_Variable.INDEX_PHONE).getPhone());
            Global_Variable.INDEX_PHONE++;
            if(Global_Variable.INDEX_PHONE>MyService.listContact.size()-1){
                Global_Variable.INDEX_PHONE = 0;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Global_Variable.INDEX_PHONE_STR, Global_Variable.INDEX_PHONE);
            editor.apply();
            Log.d("YongQuan", "Wait");

            while(true){
                Thread.sleep(1000);
                Log.d("YongQuan","check current time :"+ checkTime + "/"+Global_Variable.TIME_CONNECT*60 +"  "+MainActivity.STATE_PHONE);
                checkTime += 1;
                //chon thoi gian la phut or giay
                if(Global_Variable.TIME_CONNECT_MUNITE){
                    hesoConnect = 60;
                }
                if(Global_Variable.TIME_WAIT_MUNITE){
                    hesoWait = 60;
                }
                if(checkTime > Global_Variable.TIME_CONNECT * hesoConnect){
                    Log.d("YongQuan", "ngat ket noi");
                    disconnectCall();
                    Thread.sleep(Global_Variable.TIME_WAITING*1000 * hesoWait);
                    break;
                }
                else if(MainActivity.STATE_PHONE == "idle") {
                    Log.d("YongQuan", "continue");
                    Thread.sleep(Global_Variable.TIME_WAITING*1000 * hesoWait);
                    return null;
                }
                if(MyService.StopService) {
                    Thread.sleep(Global_Variable.TIME_WAITING*1000 * hesoWait);
                    return null;
                }
                publishProgress(1);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(!MyService.StopService) {
            new MyAsyncTask(contextParent).execute();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        final PhoneCallListener phoneListener2 = new PhoneCallListener();
        TelephonyManager telephonyManager2 = (TelephonyManager)contextParent.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager2.listen(phoneListener2, PhoneStateListener.LISTEN_CALL_STATE);
        super.onProgressUpdate(values);
    }

    private void callTo(String phone) {
        Log.d("YongQuan", phone);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setPackage("com.android.phone");
        intent.setData(Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        contextParent.startActivity(intent);
    }

    public void disconnectCall() {
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("YongQuan",
                    "FATAL ERROR: could not connect to telephony subsystem");
            Log.e("YongQuan", "Exception object: " + e);
        }
    }
}