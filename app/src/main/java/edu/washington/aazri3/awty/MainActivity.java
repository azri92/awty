package edu.washington.aazri3.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText editMsg;
    private EditText editPhone;
    private EditText editInterval;
    private Button btnAction;
    private Boolean isAnnoying; // if alarm is on or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMsg = (EditText) findViewById(R.id.editMsg);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editInterval = (EditText) findViewById(R.id.editInterval);
        btnAction = (Button) findViewById(R.id.btnAction);

        Intent intent = new Intent(MainActivity.this, AnnoyanceReceiver.class);
        intent.setAction("edu.washington.aazri3.awty.annoy");
        isAnnoying = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_NO_CREATE) != null);
        if (isAnnoying)
            btnAction.setText("Stop");

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editMsg.getText().toString().trim();
                String phone = editPhone.getText().toString();
                String intervalString = editInterval.getText().toString();
                if (!isAnnoying) {
                    // message can't be empty
                    // phone number must be 10 characters (US)
                    // interval must be more than 0 minutes
                    if (msg.length() > 0 && phone.length() == 10 && intervalString.length() > 0) {
                        // start service
                        int interval = Integer.parseInt(intervalString);

                        String formattedPhone = "(" + phone.substring(0,3) + ") " +
                                phone.substring(3,6) + "-" + phone.substring(6);

                        Intent intent = new Intent(MainActivity.this, AnnoyanceReceiver.class);
                        intent.setAction("edu.washington.aazri3.awty.annoy");
                        intent.putExtra("msg", formattedPhone + ": " + msg);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        startAnnoying(interval, pendingIntent);
                        updateStatus("Stop", true, "You've started annoying");
                    }
                } else {
                    // stop service
                    Intent intent = new Intent(MainActivity.this, AnnoyanceReceiver.class);
                    intent.setAction("edu.washington.aazri3.awty.annoy");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                            0, intent, PendingIntent.FLAG_NO_CREATE);
                    stopAnnoying(pendingIntent);
                    updateStatus("Start", false, "You've stopped annoying");
                }
            }
        });
    }

    private void startAnnoying(int interval, PendingIntent pendingIntent) {
        int intervalInMillis = interval * 1000 * 60;

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + intervalInMillis, intervalInMillis, pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, 3000, pendingIntent); // for testing
    }

    private void stopAnnoying(PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private void updateStatus(String btnText, Boolean isAnnoying, String update) {
        btnAction.setText(btnText);
        this.isAnnoying = isAnnoying;
        Toast.makeText(getApplicationContext(), update, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
