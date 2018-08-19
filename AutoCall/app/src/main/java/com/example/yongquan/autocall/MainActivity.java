package com.example.yongquan.autocall;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Model.Contact;
import com.example.yongquan.autocall.Service.AutoCallService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String STATE_PHONE = "";
    public final int MULTIPLE_PERMISSIONS = 10;
    public static String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private boolean permision_ok = false;
    private PowerManager.WakeLock wakeLock;
    Button buttonStart, buttonAddPhone,buttonSetting;
    TextView textViewTitleApp;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_fade_in_activity, R.anim.anim_fade_out_activity);
        setContentView(R.layout.activity_main);
        checkPermissions();
        init();
        turnScreenOn();
//test..................
//        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
//        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +2* 60*1000, sender);

        //.........................
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonAddPhone = (Button) findViewById(R.id.buttonAddPhone);
        buttonSetting = (Button) findViewById(R.id.buttonSetting);
//        textViewTitleApp = (TextView)findViewById(R.id.titleApp);
        buttonStart.setOnClickListener(this);
        buttonAddPhone.setOnClickListener(this);
        buttonSetting.setOnClickListener(this);

        if(Global_Variable.SERVICE_IS_START){
            buttonStart.setText("Tắt Chương Trình");
        }
        else {
            buttonStart.setText("Chạy Chương Trình");
        }

        if(Global_Variable.listContact==null){
            Global_Variable.listContact =new ArrayList<Contact>();
        }

//        ServiceNoDelay mSensorService = new ServiceNoDelay(getApplicationContext());
//        Intent mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
//        if (!isMyServiceRunning(mSensorService.getClass())) {
//            startService(mServiceIntent);
//        }
//        PowerManager.WakeLock wakelock;
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        wakelock= pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getCanonicalName());
//        wakelock.acquire();

    }
    private void settingBrightness(int valueBright){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        int brightness;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {
                Settings.System.putInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

                brightness = Settings.System.getInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS);
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams layoutpars = getWindow().getAttributes();
                layoutpars.screenBrightness = valueBright / (float) 255;
                getWindow().setAttributes(layoutpars);
            } catch (Settings.SettingNotFoundException e) {
                Log.e("Error", "Cannot access system brightness");
                e.printStackTrace();
            }
        }

    }
    private boolean checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            int result;
            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                result = ContextCompat.checkSelfPermission(this, p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
                return false;
            }
            return true;

        } else{
            return true;
        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permision_ok = true;
                } else {
                    String permissions1 = "";
                    for (String per : MainActivity.permissions) {
                        permissions1 += "\n" + per;
                    }
                    // permissions list of don't granted permission
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("YongQuan","thoat");
        this.moveTaskToBack(true);
    }

    private void init(){

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");

        sharedPreferences = getSharedPreferences("YongQuan",Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("contact","");
        Global_Variable.listContact = Global_Function.convertStringToArray(str);

        Global_Variable.SERVICE_IS_START = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR,false);
        Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR,0);
        Global_Variable.TIME_CONNECT = sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR,9);
        Global_Variable.TIME_WAITING = sharedPreferences.getInt(Global_Variable.TIME_WAITING_STR,9);
        Global_Variable.TIME_CONNECT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR,true);
        Global_Variable.TIME_WAIT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR,false);
        Global_Variable.TIME_START = sharedPreferences.getString(Global_Variable.TIME_START_STR,"00:00");
        Global_Variable.TIME_END = sharedPreferences.getString(Global_Variable.TIME_END_STR,"23:59");
        Global_Variable.TIME_SEND_SMS = sharedPreferences.getInt(Global_Variable.TIME_SEND_SMS_STR,7);
        Global_Variable.DAY_SEND_SMS = sharedPreferences.getInt(Global_Variable.DAY_SEND_SMS_STR,7);
        Global_Variable.WAS_SEND_SMS = sharedPreferences.getBoolean(Global_Variable.WAS_SEND_SMS_STR,false);
        Global_Variable.SMS_UNABLE = sharedPreferences.getBoolean(Global_Variable.SMS_UNABLE_STR,false);
        Global_Variable.SMS_CONTENT = sharedPreferences.getString(Global_Variable.SMS_CONTENT_STR,"");
        Global_Variable.SMS_SENDTO = sharedPreferences.getString(Global_Variable.SMS_SENDTO_STR,"");
    }
    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart:
                if(checkPermissions()) {
                    if (!Global_Variable.SERVICE_IS_START) {
                        if (Global_Variable.listContact != null && Global_Variable.listContact.size() > 0) {
                            wakeLock.acquire();
                            settingBrightness(10);
                            Global_Variable.SERVICE_IS_START = true;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Global_Variable.SERVICE_IS_START_STR, Global_Variable.SERVICE_IS_START);
                            //tao list moi de goi random
                            editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact));
                            editor.apply();
                            startService(new Intent(this, AutoCallService.class));
                            buttonStart.setText("Tắt Chương Trình");
                            Toast.makeText(this, "Chạy Chương Trình", Toast.LENGTH_LONG).show();
                            Global_Function.sendNotification(this,"Ứng dụng đang chạy ngầm",1);
                        } else {
                            Toast.makeText(this, "Không có số gọi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if(wakeLock!=null && wakeLock.isHeld()){
                            wakeLock.release();
                        }
                        settingBrightness(175);
                        Global_Variable.SERVICE_IS_START = false;
                        Global_Variable.WAS_SEND_SMS = false;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Global_Variable.SERVICE_IS_START_STR, Global_Variable.SERVICE_IS_START);
                        editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
                        editor.remove("contact_t");
                        editor.apply();

                        stopService(new Intent(this, AutoCallService.class));
                        buttonStart.setText("Chạy Chương Trình");
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(1);
//                        sharedPreferences = getSharedPreferences("YongQuan",Context.MODE_PRIVATE);
//                        String str = sharedPreferences.getString("contact","");
//                        Global_Variable.listContact = Global_Function.convertStringToArray(str);



                    }
                }
                break;
            case R.id.buttonAddPhone:
                Intent intent2=new Intent(this,AddPhone.class);
                startActivity(intent2);
                break;
            case R.id.buttonSetting:
                Intent intent=new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void turnScreenOn() {
        int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


}
