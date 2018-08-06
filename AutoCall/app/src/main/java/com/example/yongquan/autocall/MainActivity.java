package com.example.yongquan.autocall;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Model.Contact;

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

    Button buttonStart, buttonAddPhone,buttonSetting;
    TextView textViewTitleApp;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

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

//        Typeface face = Typeface.createFromAsset(getAssets(),
//                "fonts/gitchgitch-italic.otf");
//        textViewTitleApp.setTypeface(face);

    }

    private boolean checkPermissions() {
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
        permision_ok = true;
        return true;
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
//        final PhoneCallListener phoneListener = new PhoneCallListener();
//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        sharedPreferences = getSharedPreferences("YongQuan",Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("contact","");
        Global_Variable.listContact = Global_Function.convertStringToArray(str);

        Global_Variable.SERVICE_IS_START = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR,false);
        Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR,0);
        Global_Variable.TIME_CONNECT = sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR,1);
        Global_Variable.TIME_WAITING = sharedPreferences.getInt(Global_Variable.TIME_WAITING_STR,10);
        Global_Variable.TIME_CONNECT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR,true);
        Global_Variable.TIME_WAIT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR,false);
        Global_Variable.TIME_START = sharedPreferences.getString(Global_Variable.TIME_START_STR,"00:00");
        Global_Variable.TIME_END = sharedPreferences.getString(Global_Variable.TIME_END_STR,"23:59");
        Global_Variable.TIME_SEND_SMS = sharedPreferences.getInt(Global_Variable.TIME_SEND_SMS_STR,1);
        Global_Variable.DAY_SEND_SMS = sharedPreferences.getInt(Global_Variable.DAY_SEND_SMS_STR,1);
        Global_Variable.WAS_SEND_SMS = sharedPreferences.getBoolean(Global_Variable.WAS_SEND_SMS_STR,false);
        Global_Variable.SMS_UNABLE = sharedPreferences.getBoolean(Global_Variable.SMS_UNABLE_STR,false);
        Global_Variable.SMS_CONTENT = sharedPreferences.getString(Global_Variable.SMS_CONTENT_STR,"");
        Global_Variable.SMS_SENDTO = sharedPreferences.getString(Global_Variable.SMS_SENDTO_STR,"");
    }
    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart:
                checkPermissions();
                if(permision_ok) {
                    if (!Global_Variable.SERVICE_IS_START) {
                        if (Global_Variable.listContact != null && Global_Variable.listContact.size() > 0) {
                            Global_Variable.SERVICE_IS_START = true;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(Global_Variable.SERVICE_IS_START_STR, Global_Variable.SERVICE_IS_START);
                            //tao list moi de goi random
                            editor.putString("contact_t", Global_Function.converStringFromArray(Global_Variable.listContact));
                            editor.apply();
                            startService(new Intent(this, MyService.class));
                            buttonStart.setText("Tắt Chương Trình");
                            Toast.makeText(this, "Chạy Chương Trình", Toast.LENGTH_LONG).show();
                            Global_Function.sendNotification(this,"Ứng dụng đang chạy ngầm",1);
                        } else {
                            Toast.makeText(this, "Không có số gọi", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Global_Variable.SERVICE_IS_START = false;
                        Global_Variable.WAS_SEND_SMS = false;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Global_Variable.SERVICE_IS_START_STR, Global_Variable.SERVICE_IS_START);
                        editor.putBoolean(Global_Variable.WAS_SEND_SMS_STR, Global_Variable.WAS_SEND_SMS);
                        editor.remove("contact_t");
                        editor.apply();

                        stopService(new Intent(this, MyService.class));
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



}
