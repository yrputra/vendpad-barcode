package com.example.yrp.nextapp.Spam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yrp.nextapp.Notification.MyAlarmService;

/**
 * Created by YRP on 22/07/2015.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent receive = new Intent(context, MyAlarmService.class);
        context.startActivity(receive);
    }
}
