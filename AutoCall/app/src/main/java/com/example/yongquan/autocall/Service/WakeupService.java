package com.example.yongquan.autocall.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.yongquan.autocall.Global.Global_Function;
import com.example.yongquan.autocall.MainActivity;
import com.example.yongquan.autocall.Receiver.DisconnectCallReceiver;


public class WakeupService extends IntentService {
    public WakeupService() {
        super("WakeupService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // At this point DisconnectCallReceiver is still holding a wake lock
        // for us.  We can do whatever we need to here and then tell it that
        // it can release the wakelock.  This sample just does some slow work,
        // but more complicated implementations could take their own wake
        // lock here before releasing the receiver's.
        //
        // Note that when using this approach you should be aware that if your
        // service gets killed and restarted while in the middle of such work
        // (so the Intent gets re-delivered to perform the work again), it will
        // at that point no longer be holding a wake lock since we are depending
        // on DisconnectCallReceiver to that for us.  If this is a concern, you can
        // acquire a separate wake lock here.
        Log.i("DisconnectCallReceiver", "Completed service @ " + SystemClock.elapsedRealtime());
//        getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        Global_Function.disconnectCall();
        getApplicationContext().startActivity(new Intent(getApplicationContext(), MainActivity.class));
        DisconnectCallReceiver.completeWakefulIntent(intent);

    }
}
