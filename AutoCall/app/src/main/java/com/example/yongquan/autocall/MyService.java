package com.example.yongquan.autocall;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.yongquan.autocall.Model.Contact;

import java.util.ArrayList;

public class MyService extends Service {

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
        new MyAsyncTask(this).execute();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Tắt Chương Trình", Toast.LENGTH_LONG).show();
    }

}