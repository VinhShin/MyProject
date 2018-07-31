package com.example.yongquan.autocall;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.yongquan.autocall.Global.Global_Variable;

public class SettingActivity extends AppCompatActivity {

    EditText TGKN,TGBD,TGKT,TGC,Gio,Ngay;
    Button button;
    CheckBox checkBox;
    RadioButton rd_connect_munite,rd_connect_second, rd_wait_munite, rd_wait_second;
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
        checkBox = (CheckBox)findViewById(R.id.radio_sms);
        rd_connect_munite = (RadioButton) findViewById(R.id.rd_time_connect_munite);
        rd_connect_second = (RadioButton) findViewById(R.id.rd_time_connect_second);
        rd_wait_munite = (RadioButton) findViewById(R.id.rd_time_wait_munite);
        rd_wait_second = (RadioButton) findViewById(R.id.rd_time_wait_second);

        setup();
        button = (Button) findViewById(R.id.btn_thaydoi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global_Variable.TIME_CONNECT = Integer.valueOf(TGKN.getText().toString());
                Global_Variable.TIME_START = Integer.valueOf(TGBD.getText().toString());
                Global_Variable.TIME_END = Integer.valueOf(TGKT.getText().toString());
                Global_Variable.TIME_WAITING = Integer.valueOf(TGC.getText().toString());
                Global_Variable.TIME_SEND_SMS = Integer.valueOf(Gio.getText().toString());
                Global_Variable.DAY_SEND_SMS = Integer.valueOf(Ngay.getText().toString());

                Global_Variable.TIME_WAIT_MUNITE = false;
                Global_Variable.TIME_CONNECT_MUNITE = false;
                if(rd_wait_munite.isChecked()){
                    Global_Variable.TIME_WAIT_MUNITE = true;
                }
                if(rd_connect_munite.isChecked()){
                    Global_Variable.TIME_CONNECT_MUNITE = true;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("YongQuan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //test
                editor.putInt(Global_Variable.TIME_CONNECT_STR,Global_Variable.TIME_CONNECT);
                editor.putInt(Global_Variable.TIME_START_STR,Global_Variable.TIME_START);
                editor.putInt(Global_Variable.TIME_END_STR,Global_Variable.TIME_END);
                editor.putInt(Global_Variable.TIME_WAITING_STR,Global_Variable.TIME_WAITING);
                editor.putInt(Global_Variable.TIME_SEND_SMS_STR,Global_Variable.TIME_SEND_SMS);
                if(Global_Variable.DAY_SEND_SMS!=0){
                    editor.putInt(Global_Variable.DAY_SEND_SMS_STR,Global_Variable.DAY_SEND_SMS);
                }
                editor.putBoolean(Global_Variable.TIME_CONNECT_MUNITE_STR,Global_Variable.TIME_CONNECT_MUNITE);
                editor.putBoolean(Global_Variable.TIME_WAIT_MUNITE_STR,Global_Variable.TIME_WAIT_MUNITE);
                editor.apply();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    findViewById(R.id.rl_sms).setVisibility(View.GONE);
                }
                else {
                    findViewById(R.id.rl_sms).setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void setup(){
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
    }
}
