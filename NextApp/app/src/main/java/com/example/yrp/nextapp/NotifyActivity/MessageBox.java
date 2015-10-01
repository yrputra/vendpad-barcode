package com.example.yrp.nextapp.NotifyActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yrp.nextapp.Notification.MyAlarmService;
import com.example.yrp.nextapp.Notification.NotificationView;
import com.example.yrp.nextapp.R;

import java.util.Calendar;

/**
 * Created by YRP on 24/07/2015.
 */
public class MessageBox extends Activity {
    Button btnClose, btnView;
    PendingIntent pendingIntent;
    MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_box_layout);

        mPlayer = MediaPlayer.create(MessageBox.this, R.raw.ringtone2);
        mPlayer.start();

        btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myIntent = new Intent(MessageBox.this, MyAlarmService.class);
//                pendingIntent = PendingIntent.getService(MessageBox.this, 0, myIntent, 0);
                finish();
                Intent myIntent = new Intent(MessageBox.this, MyAlarmService.class);
                pendingIntent = PendingIntent.getService(MessageBox.this, 0, myIntent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 1);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(MessageBox.this, "Start Alarm", Toast.LENGTH_LONG).show();
            }
        });

        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NotificationView.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }
}
