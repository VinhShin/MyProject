package com.example.yongquan.autocall.Global;

import android.content.Context;
import android.content.SharedPreferences;

public class Global_Variable {

    public static boolean SERVICE_IS_START = false;
    public static int INDEX_PHONE = 0;
    public static int TIME_CONNECT = 0;
    public static int TIME_WAITING = 0;
    public static boolean TIME_CONNECT_MUNITE = true;
    public static boolean TIME_WAIT_MUNITE = true;
    public static int TIME_START = 0;
    public static int TIME_END = 0;
    public static int TIME_SEND_SMS;
    public static int DAY_SEND_SMS;
    public static boolean WAS_SEND_SMS = false;
    //
    public static String SERVICE_IS_START_STR = "service_start";
    public static String INDEX_PHONE_STR ="indexPhone";
    public static String TIME_CONNECT_STR = "time_connect";
    public static String TIME_WAITING_STR = "time_waiting";
    public static String TIME_CONNECT_MUNITE_STR = "time_connect_munite";
    public static String TIME_WAIT_MUNITE_STR = "time_wait_munite";
    public static String TIME_START_STR = "time_start";
    public static String TIME_END_STR = "time_end";
    public static String TIME_SEND_SMS_STR = "time_send_sms";
    public static String DAY_SEND_SMS_STR = "day_send_sms";
    public static String WAS_SEND_SMS_STR = "was_send_sms";


}
