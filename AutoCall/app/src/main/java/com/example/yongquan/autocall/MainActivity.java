package com.example.yongquan.autocall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonStart, buttonAddPhone,buttonSetting;

    public static int THOIGIANGOI = 0;
    public static int THOIGIANCHO = 0;
    public static int KHUNGGIO_BATDAU = 0;
    public static int KHUNGGIO_KETHUC = 0;
    public static int SMS_GIO = 0;
    public static int SMS_NGAY = 0;

    public static String STATE_PHONE = "";
    public static boolean SERVICE_IS_START = false;
    private SharedPreferences sharedPreferences;
    public static int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonAddPhone = (Button) findViewById(R.id.buttonAddPhone);
        buttonSetting = (Button) findViewById(R.id.buttonSetting);

        buttonStart.setOnClickListener(this);
        buttonAddPhone.setOnClickListener(this);
        buttonSetting.setOnClickListener(this);

        if(Global_Variable.SERVICE_IS_START){
            buttonStart.setText("Tắt Chương Trình");
        }
        else {
            buttonStart.setText("Chạy Chương Trình");
        }

        if(MyService.listContact==null){
            MyService.listContact =new ArrayList<Contact>();
        }
        getAllSetting();

    }
    private void init(){
//        final PhoneCallListener phoneListener = new PhoneCallListener();
//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        sharedPreferences = getSharedPreferences("YongQuan",Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("contact","");
        MyService.listContact = Global_Function.convertStringToArray(str);

        Global_Variable.SERVICE_IS_START = sharedPreferences.getBoolean(Global_Variable.SERVICE_IS_START_STR,false);
        Global_Variable.INDEX_PHONE = sharedPreferences.getInt(Global_Variable.INDEX_PHONE_STR,0);
        Global_Variable.TIME_CONNECT = sharedPreferences.getInt(Global_Variable.TIME_CONNECT_STR,1);
        Global_Variable.TIME_WAITING = sharedPreferences.getInt(Global_Variable.TIME_WAITING_STR,0);
        Global_Variable.TIME_CONNECT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR,true);
        Global_Variable.TIME_WAIT_MUNITE = sharedPreferences.getBoolean(Global_Variable.TIME_WAIT_MUNITE_STR,true);
        Global_Variable.TIME_START = sharedPreferences.getInt(Global_Variable.TIME_START_STR,0);
        Global_Variable.TIME_END = sharedPreferences.getInt(Global_Variable.TIME_END_STR,0);
        Global_Variable.TIME_SEND_SMS = sharedPreferences.getInt(Global_Variable.TIME_SEND_SMS_STR,1);
        Global_Variable.DAY_SEND_SMS = sharedPreferences.getInt(Global_Variable.DAY_SEND_SMS_STR,1);
        Global_Variable.WAS_SEND_SMS = sharedPreferences.getBoolean(Global_Variable.WAS_SEND_SMS_STR,false);
    }
    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart:
                if(!Global_Variable.SERVICE_IS_START) {
                    if(MyService.listContact != null && MyService.listContact.size()>0) {
                        Global_Variable.SERVICE_IS_START = true;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(Global_Variable.SERVICE_IS_START_STR, Global_Variable.SERVICE_IS_START);
                        editor.apply();
                        startService(new Intent(this, MyService.class));
                        buttonStart.setText("Tắt Chương Trình");
                        Toast.makeText(this, "Chạy Chương Trình", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, "Không có số gọi", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Global_Variable.SERVICE_IS_START = false;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Global_Variable.SERVICE_IS_START_STR, Global_Variable.SERVICE_IS_START);
                    editor.apply();
                    stopService(new Intent(this, MyService.class));
                    buttonStart.setText("Chạy Chương Trình");
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

    private void getAllSetting(){
        THOIGIANGOI = sharedPreferences.getInt("callTGKN",0);
        THOIGIANCHO = sharedPreferences.getInt("callTGBD",0);
        KHUNGGIO_BATDAU = sharedPreferences.getInt("callTGKT",0);
        KHUNGGIO_KETHUC = sharedPreferences.getInt("callTGC",0);
        SMS_GIO = sharedPreferences.getInt("smsGio",0);
        SMS_NGAY = sharedPreferences.getInt("smsNgay",0);
    }

}
