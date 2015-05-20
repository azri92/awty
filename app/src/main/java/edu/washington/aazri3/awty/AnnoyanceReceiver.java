package edu.washington.aazri3.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/*
 * Do something when receive a broadcast from the broadcaster (AlarmManager)
 */

public class AnnoyanceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        String phone = intent.getStringExtra("phone");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, msg, null, null);

        Log.d("AnnoyanceReceiver", "sms sent to " + phone);
    }

}
