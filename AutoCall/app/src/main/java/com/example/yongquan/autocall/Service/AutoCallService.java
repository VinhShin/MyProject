package com.example.yongquan.autocall.Service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.yongquan.autocall.Global.Global_Variable;
import com.example.yongquan.autocall.Model.Contact;
import com.example.yongquan.autocall.MyAsyncTask;

import java.util.ArrayList;

public class AutoCallService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Global_Variable.myAsyncTask = new MyAsyncTask(this);
        Global_Variable.myAsyncTask.execute();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Tắt Chương Trình", Toast.LENGTH_LONG).show();
        if(Global_Variable.myAsyncTask!=null){
            Global_Variable.myAsyncTask.cancel(true);
        }
    }

}