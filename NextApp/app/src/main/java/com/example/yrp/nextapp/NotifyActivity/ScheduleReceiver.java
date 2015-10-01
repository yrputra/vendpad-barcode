package com.example.yrp.nextapp.NotifyActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by YRP on 24/07/2015.
 */
public class ScheduleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent scheduleIntent = new Intent(context, MessageBox.class);
        scheduleIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        scheduleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scheduleIntent);
    }
}
