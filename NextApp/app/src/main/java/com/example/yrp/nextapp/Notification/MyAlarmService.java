package com.example.yrp.nextapp.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.yrp.nextapp.R;

/**
 * Created by YRP on 22/07/2015.
 */
public class MyAlarmService extends Service {

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }

//    @SuppressWarnings("static-access")
    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {
        super.onStartCommand(intent,flags, startId);
//        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//
//        View mView = mInflater.inflate(R.layout.score, null);
//
//        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
//                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
///* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
//                PixelFormat.RGBA_8888);
//
//        mWindowManager.addView(mView, mLayoutParams);
//
        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),NotificationView.class);

        Notification notification = new Notification(R.mipmap.ic_vendpad,"You have a new message!", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this.getApplicationContext(), "Vendpad message", "Invoice must be paid right now", pendingNotificationIntent);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmSound == null) {
//            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//            if(alarmSound == null){
//                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            }
//        }
//        notification.sound = alarmSound;
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        mManager.notify(0, notification);
        return flags;
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

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
//    }

}
