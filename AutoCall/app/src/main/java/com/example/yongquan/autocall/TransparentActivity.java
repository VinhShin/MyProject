package com.example.yongquan.autocall;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class TransparentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        Log.d("YongQuanQuan","Yo");
        int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);

//        finish();
    }

    @Override
    protected void onStart() {
        try {
            Thread.sleep(1000);
//            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        finish();
        super.onStart();
    }

    @Override
    protected void onResume() {
//        finish();
        super.onResume();
    }
}
