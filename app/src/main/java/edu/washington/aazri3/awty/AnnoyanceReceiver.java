package edu.washington.aazri3.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/*
 * Do something when receive a broadcast from the broadcaster (AlarmManager)
 */

public class AnnoyanceReceiver extends BroadcastReceiver {

    // display a toast for now
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d("AnnoyanceReceiver", "Received intent: " + intent.toString());

        String msg = intent.getStringExtra("msg");
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
