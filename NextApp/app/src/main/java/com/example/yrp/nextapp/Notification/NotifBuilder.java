package com.example.yrp.nextapp.Notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.app.NotificationCompat;
//import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yrp.nextapp.R;

import java.util.Calendar;

/**
 * Created by YRP on 22/07/2015.
 */
public class NotifBuilder extends Activity {

    Button btnCheck, btnSetTime;
    TimePicker timePicker;
    Calendar calendar;
    private int jam, menit;
    private NotificationManager myNotificationManager;
    private PendingIntent pendingIntent;

    private int notificationIdOne = 111;
    private int numMessagesOne = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_notif_builder);

        btnCheck = (Button) findViewById(R.id.btnCheck);

//                calendar = Calendar.getInstance();
//
//        calendar.set(Calendar.MONTH, 7);
//        calendar.set(Calendar.YEAR, 2015);
//        calendar.set(Calendar.DAY_OF_MONTH, 23);
//
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 51);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.PM);
        Intent myIntent = new Intent(NotifBuilder.this, MyAlarmService.class);
        pendingIntent = PendingIntent.getService(NotifBuilder.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(NotifBuilder.this, "Start Alarm", Toast.LENGTH_LONG).show();

//                Intent myIntent = new Intent(NotifBuilder.this, MyReceiver.class);
//                pendingIntent = PendingIntent.getBroadcast(NotifBuilder.this, 0, myIntent, 0);

//                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


        //end onCreate

////        btnCheck= (Button) findViewById(R.id.btnCheck);
////        btnCheck.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                showNotif();
////            }
////        });
//        timePicker = (TimePicker) findViewById(R.id.timePicker);
//        btnSetTime = (Button) findViewById(R.id.btnSetTime);
//        calendar = Calendar.getInstance();
//        btnSetTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jam = timePicker.getCurrentHour();
//                menit = timePicker.getCurrentMinute();
//                calendar.set(Calendar.HOUR,jam);
//                calendar.set(Calendar.MINUTE,menit);
//            }
//        });
//
//
////        calendar.set(Calendar.MONTH, 7);
////        calendar.set(Calendar.YEAR, 2015);
////        calendar.set(Calendar.DAY_OF_MONTH, 22);
////        calendar.set(Calendar.AM_PM, Calendar.PM);
//
//        Intent myIntent = new Intent(NotifBuilder.this, MyReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(NotifBuilder.this, 0, myIntent, 0);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    private void showNotif() {
//        // Invoking the default notification service
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//
//        mBuilder.setContentTitle("Invoice must be paid right now");
//        mBuilder.setContentText("New message from Vendpad received");
//        mBuilder.setTicker("Vendpad: New Message Received!");
//        mBuilder.setSmallIcon(R.mipmap.ic_vendpad);
//
//        // Increase notification number every time a new notification arrives
//        mBuilder.setNumber(++numMessagesOne);
//
//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, NotificationView.class);
//        resultIntent.putExtra("notificationId", notificationIdOne);
//
//        //This ensures that navigating backward from the Activity leads out of the app to Home page
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//
//        // Adds the back stack for the Intent
//        stackBuilder.addParentStack(NotificationView.class);
//
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_ONE_SHOT //can only be used once
//                );
//        // start the activity when the user clicks the notification text
//        mBuilder.setContentIntent(resultPendingIntent);
//
//        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // pass the Notification object to the system
//        myNotificationManager.notify(notificationIdOne, mBuilder.build());
//
//
////        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////        @SuppressWarnings("deprecation")
////
////        Notification notification = new Notification(R.mipmap.ic_vendpad,"New Message", System.currentTimeMillis());
////        Intent notificationIntent = new Intent(this,NotificationView.class);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
////
////        notification.setLatestEventInfo(NotifBuilder.this, notificationTitle,notificationMessage, pendingIntent);
////        notificationManager.notify(9999, notification);
//    }


}
