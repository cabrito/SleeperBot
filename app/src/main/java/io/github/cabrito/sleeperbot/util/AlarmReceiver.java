package io.github.cabrito.sleeperbot.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.github.cabrito.sleeperbot.activities.DismissAlarmActivity;

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent dismissAlarmIntent = new Intent(context, DismissAlarmActivity.class);
        dismissAlarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(dismissAlarmIntent);
    }
}
