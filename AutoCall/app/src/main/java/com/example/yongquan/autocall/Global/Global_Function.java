package com.example.yongquan.autocall.Global;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.yongquan.autocall.MainActivity;
import com.example.yongquan.autocall.Model.Contact;
import com.example.yongquan.autocall.Receiver.DisconnectCallReceiver;
import com.example.yongquan.autocall.Receiver.NotificationDismissedReceiver;
import com.example.yongquan.autocall.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Global_Function {
    public static ArrayList<Contact> convertStringToArray(String str){
        if(str!="") {
            ArrayList<Contact> contact = new ArrayList<Contact>();
            String[] tempArray;
            tempArray = str.split("__");
            for (int i = 0; i < tempArray.length; i++) {
                String[] temp = tempArray[i].split("_");
                if(temp.length>1) {
                    contact.add(new Contact(temp[0], temp[1]));
                }
            }
            if(contact.size()>0)
            {
                return contact;
            }
            else {
                return null;
            }
        }
        return null;
    }
    public static String converStringFromArray(ArrayList<Contact> list){
        String str = "";
        for(int i=0;i<list.size();i++){
            str += list.get(i).getName() +"_"+ list.get(i).getPhone()+"__";
        }
        return  str;
    }
	public static void sendNotification(Context context, String message, int notificationId) {
        Bundle b = new Bundle();
        Intent intent = new Intent(context, NotificationDismissedReceiver.class);
        intent.putExtra("com.stack.notificationId", notificationId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setDeleteIntent(createOnDismissedIntent(context, 11))
                .setSound(defaultSoundUri);
//                .setContentIntent(pendingIntent);
        Notification note = notificationBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }
    private static PendingIntent createOnDismissedIntent(Context context, int notificationId) {
        Intent intent = new Intent(context, NotificationDismissedReceiver.class);
        intent.putExtra("com.stack.notificationId", notificationId);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context.getApplicationContext(),
                        notificationId, intent, PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }
    public static void disconnectCall() {
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
    public static void DisconnectCall(Context context, long timeSecond){
        Intent intent = new Intent(context, DisconnectCallReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,
                0, intent, 0);
        // We want the alarm to go off 30 seconds from now.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.add(Calendar.SECOND, timeSecond);
        // Schedule the alarm!
        Log.d("YongQuanCheck",timeSecond+"");
        Log.d("YongQuanCheck",System.currentTimeMillis() +"");
        Log.d("YongQuanCheck",System.currentTimeMillis() + timeSecond*1000+"");
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeSecond*1000, sender);

//        Intent intent = new Intent();
////        i.putExtra("STRING_I_NEED", strName);
//        intent.setAction("yongquan.bala");
//        context.sendBroadcast(intent);
//        PendingIntent sender = PendingIntent.getBroadcast(context,
//                0, intent, 0);
//        // We want the alarm to go off 30 seconds from now.
//
//        // Schedule the alarm!
//        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ timeSecond * 1000, sender);

    }

}
