package io.github.cabrito.sleeperbot.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.github.cabrito.sleeperbot.activities.DismissAlarmActivity;

public class AlarmReceiver extends BroadcastReceiver
{
    /**
     * Serves as the entry point for the Android OS {@link android.app.AlarmManager} for what to do
     * when the alarm is intended to go off.
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent dismissAlarmIntent = new Intent(context, DismissAlarmActivity.class);
        dismissAlarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Necessary to launch activity.
        context.startActivity(dismissAlarmIntent);
    }
}
