package com.example.yongquan.autocall.Global;


import com.example.yongquan.autocall.Model.Contact;
import com.example.yongquan.autocall.MyAsyncTask;

import java.util.ArrayList;

public class Global_Variable {

    public static ArrayList<Contact> listContact_temp;
    public static ArrayList<Contact> listContact;
    public static boolean SERVICE_IS_START = false;
    public static String STATE_PHONE = "idle";
    public static int INDEX_PHONE = 0;
    public static int TIME_CONNECT = 0;
    public static int TIME_WAITING = 0;
    public static boolean TIME_CONNECT_MUNITE = true;
    public static boolean TIME_WAIT_MUNITE = true;
    public static String TIME_START = "";
    public static String TIME_END = "";
    public static int TIME_SEND_SMS;
    public static int DAY_SEND_SMS;
    public static boolean WAS_SEND_SMS = false;
    public static boolean SMS_UNABLE = false;
    public static String SMS_CONTENT = "";
    public static String SMS_SENDTO = "";
    public static int CALL_SUCCESS = 0;
    public static long TOTAL_TIME_CALL = 0;
    public static int statusIdel = 0;
    public static MyAsyncTask myAsyncTask = null;
//    public static AlarmManager alarmManager = null;
    public static ChildrenAlarmManager childrenAlarmManager = null;
    public static String ACTION_CALL = "action_call";
    public static String ACTION_DISCONNECT = "action_disconnect";
    public static int TIME_DISTANCE_CHECK = 5;
    public static long TOTAL_TIME_TEMP = 0;

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
    public static String SMS_UNABLE_STR = "sms_unable";
    public static String SMS_CONTENT_STR = "sms_content";
    public static String SMS_SENDTO_STR = "sms_sendto";
    public static String CALL_SUCCESS_STR = "call_success";
    public static String TOTAL_TIME_CALL_STR = "total_time_call";
    public static String TOTAL_TIME_TEMP_STR = "total_time_temp";

}
