package com.example.yrp.nextapp.NotifyActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yrp.nextapp.R;

/**
 * Created by YRP on 24/07/2015.
 */
public class MessageMain extends Activity implements View.OnClickListener {

    private EditText editText;
    private Button btnSet;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_main_layout);
        editText = (EditText) findViewById(R.id.editText);
        btnSet = (Button) findViewById(R.id.btnSet);
        alarmManager =(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        btnSet.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int x =Integer.parseInt(editText.getText().toString());
        Intent intent = new Intent(this, ScheduleReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),234324243, intent,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+(x*1000), pendingIntent);


        Toast.makeText(this, "set alarm in "+x+" seconds",Toast.LENGTH_SHORT).show();
    }
}
