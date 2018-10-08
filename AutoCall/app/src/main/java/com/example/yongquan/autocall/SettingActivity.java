package com.example.yongquan.autocall;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yongquan.autocall.Global.AlarmManager;
import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Receiver.AlarmReceiver;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SettingActivity extends AppCompatActivity {

    public static EditText TGKN,TGBD,TGKT,TGC,Gio,Ngay,edt_sms_content,edt_sms_sento;
    Button button;
    CheckBox checkBox_SMS;
    public static RadioButton rd_connect_munite,rd_connect_second, rd_wait_munite, rd_wait_second;
    private int house_start;
    private int munite_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TGKN = (EditText)findViewById(R.id.edt_tgkn);
        TGBD = (EditText)findViewById(R.id.edt_tgbd);
        TGKT = (EditText)findViewById(R.id.edt_tgkt);
        TGC = (EditText)findViewById(R.id.edt_tgc);
        Gio = (EditText)findViewById(R.id.edt_gio);
        Ngay = (EditText)findViewById(R.id.edt_ngay);
        checkBox_SMS = (CheckBox)findViewById(R.id.radio_sms);
        rd_connect_munite = (RadioButton) findViewById(R.id.rd_time_connect_munite);
        rd_connect_second = (RadioButton) findViewById(R.id.rd_time_connect_second);
        rd_wait_munite = (RadioButton) findViewById(R.id.rd_time_wait_munite);
        rd_wait_second = (RadioButton) findViewById(R.id.rd_time_wait_second);
        edt_sms_content = (EditText)findViewById(R.id.edt_sms_content);
        edt_sms_sento = (EditText)findViewById(R.id.edt_sms_sendto);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setup();
        setupSetting(false);
        button = (Button) findViewById(R.id.btn_thaydoi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.getText().equals("LƯU")){
                    int checkDK = checkCondition();
                    Log.d("YongQuan","Check: "+checkDK);
                    if(checkDK!=1)
                    {
                        if(checkDK==-1) {
                            Toast.makeText(getApplicationContext(), "Khung giờ bắt đầu không hợp lễ", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkDK==-2){
                            Toast.makeText(getApplicationContext(), "Khung giờ kết thúc không hợp lễ", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkDK==-3){
                            Toast.makeText(getApplicationContext(), "Thời gian kết nối không hợp lễ", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkDK==-4){
                            Toast.makeText(getApplicationContext(), "Thời gian chờ không hợp lễ", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkDK==-5){
                            Toast.makeText(getApplicationContext(), "Thời gian sms thông báo không hợp lễ", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkDK==-6){
                            Toast.makeText(getApplicationContext(),"Bạn không thể đặt thời gian chờ quá lớn",Toast.LENGTH_SHORT).show();
                        }
                        else if(checkDK==-7){
                            Toast.makeText(getApplicationContext(),"Bạn không thể đặt thời gian kết nối quá lớn",Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                    button.setText("THAY ĐỔI");
                    Global_Variable.TIME_CONNECT = Integer.valueOf(TGKN.getText().toString());
                    Global_Variable.TIME_START = TGBD.getText().toString();
                    Global_Variable.TIME_END = TGKT.getText().toString();
                    Global_Variable.TIME_WAITING = Integer.valueOf(TGC.getText().toString());
                    Global_Variable.TIME_SEND_SMS = Integer.valueOf(Gio.getText().toString());
                    Global_Variable.DAY_SEND_SMS = Integer.valueOf(Ngay.getText().toString());
                    Global_Variable.SMS_CONTENT = edt_sms_content.getText().toString();
                    Global_Variable.SMS_SENDTO = edt_sms_sento.getText().toString();
                    Global_Variable.TIME_WAIT_MUNITE = false;
                    Global_Variable.TIME_CONNECT_MUNITE = false;
                    if (rd_wait_munite.isChecked()) {
                        Global_Variable.TIME_WAIT_MUNITE = true;
                    }
                    if (rd_connect_munite.isChecked()) {
                        Global_Variable.TIME_CONNECT_MUNITE = true;
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //test
                    editor.putInt(Global_Variable.TIME_CONNECT_STR, Global_Variable.TIME_CONNECT);
                    editor.putString(Global_Variable.TIME_START_STR, Global_Variable.TIME_START);
                    editor.putString(Global_Variable.TIME_END_STR, Global_Variable.TIME_END);
                    editor.putInt(Global_Variable.TIME_WAITING_STR, Global_Variable.TIME_WAITING);
                    editor.putInt(Global_Variable.TIME_SEND_SMS_STR, Global_Variable.TIME_SEND_SMS);
                    if (Global_Variable.DAY_SEND_SMS != 0) {
                        editor.putInt(Global_Variable.DAY_SEND_SMS_STR, Global_Variable.DAY_SEND_SMS);
                    }
                    editor.putBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR, Global_Variable.TIME_CONNECT_MUNITE);
                    editor.putBoolean(Global_Variable.TIME_WAIT_MUNITE_STR, Global_Variable.TIME_WAIT_MUNITE);
                    editor.putBoolean(Global_Variable.SMS_UNABLE_STR, Global_Variable.SMS_UNABLE);
                    editor.putString(Global_Variable.SMS_CONTENT_STR, Global_Variable.SMS_CONTENT);
                    editor.putString(Global_Variable.SMS_SENDTO_STR, Global_Variable.SMS_SENDTO);
                    editor.apply();
                    setupSetting(false);
                }
                else {
                    button.setText("LƯU");
                    setupSetting(true);
                }
            }
        });
        checkBox_SMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    findViewById(R.id.rl_sms).setVisibility(View.GONE);
                    Global_Variable.SMS_UNABLE = false;
                }
                else {
                    findViewById(R.id.rl_sms).setVisibility(View.VISIBLE);
                    Global_Variable.SMS_UNABLE = true;
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void setup(){
        TGKN.setText(String.valueOf(Global_Variable.TIME_CONNECT));
        TGC.setText(String.valueOf(Global_Variable.TIME_WAITING));
        TGBD.setText(String.valueOf(Global_Variable.TIME_START));
        TGKT.setText(String.valueOf(Global_Variable.TIME_END));
        Gio.setText(String.valueOf(Global_Variable.TIME_SEND_SMS));
        Ngay.setText(String.valueOf(Global_Variable.DAY_SEND_SMS));
        rd_connect_munite.setChecked(Global_Variable.TIME_CONNECT_MUNITE);
        rd_connect_second.setChecked(!Global_Variable.TIME_CONNECT_MUNITE);
        rd_wait_munite.setChecked(Global_Variable.TIME_WAIT_MUNITE);
        rd_wait_second.setChecked(!Global_Variable.TIME_WAIT_MUNITE);
        checkBox_SMS.setChecked(Global_Variable.SMS_UNABLE);
        edt_sms_content.setText(Global_Variable.SMS_CONTENT);
        edt_sms_sento.setText(Global_Variable.SMS_SENDTO);

        if(!checkBox_SMS.isChecked()){
            findViewById(R.id.rl_sms).setVisibility(View.GONE);
        }else {
            findViewById(R.id.rl_sms).setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void setupSetting(boolean unable){
        TGKN.setEnabled(unable);
        TGC.setEnabled(unable);
        TGBD.setEnabled(unable);
        TGKT.setEnabled(unable);
        Gio.setEnabled(unable);
        Ngay.setEnabled(unable);
        rd_connect_munite.setEnabled(unable);
        rd_connect_second.setEnabled(unable);
        rd_wait_munite.setEnabled(unable);
        rd_wait_second.setEnabled(unable);
        edt_sms_content.setEnabled(unable);
        edt_sms_sento.setEnabled(unable);
        checkBox_SMS.setEnabled(unable);
    }
    public int checkCondition(){
        String timeStart = TGBD.getText().toString().trim();
        String timeEnd = TGKT.getText().toString().trim();
        String regexStr = "^[0-9]*$";
        String strTimeStart[] = timeStart.split(":");
        String strTimeEnd[] = timeEnd.split(":");
        if(timeStart.length()!=5 || timeStart.charAt(2)!=':' ||
                !strTimeStart[0].matches(regexStr)||
                !strTimeStart[1].matches(regexStr)||
                Integer.valueOf(strTimeStart[0])>24 ||
                Integer.valueOf(strTimeStart[0])<0||
                Integer.valueOf(strTimeStart[1])>59||
                Integer.valueOf(strTimeStart[1])<0)
        {
            return -1;
        }
        else if(timeEnd.length()!=5 || timeEnd.charAt(2)!=':'||
                !strTimeEnd[0].matches(regexStr)||
                !strTimeEnd[1].matches(regexStr)||
                Integer.valueOf(strTimeEnd[0])>24 ||
                Integer.valueOf(strTimeEnd[0])<0||
                Integer.valueOf(strTimeEnd[1])>59||
                Integer.valueOf(strTimeEnd[1])<0){
            return -2;
        }
        else if (TGKN.getText().toString().length()>5){
            return -7;
        }
        else if(TGKN.getText().toString().length()<1||Integer.valueOf(TGKN.getText().toString())<0){
            return -3;
        }
        else if (TGC.getText().toString().length()>5){
            return -6;
        }
        else if(TGC.getText().toString().length()<1||Integer.valueOf(TGC.getText().toString())<0){
            return -4;
        }
        else if(Integer.valueOf(Gio.getText().toString())<0||Integer.valueOf(Gio.getText().toString())>24||
                Integer.valueOf(Ngay.getText().toString())<2||Integer.valueOf(Ngay.getText().toString())>8){
            return -5;
        }

        return 1;
    }
}
