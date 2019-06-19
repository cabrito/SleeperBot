package io.github.cabrito.sleeperbot.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import io.github.cabrito.sleeperbot.MainActivity;
import io.github.cabrito.sleeperbot.R;
import io.github.cabrito.sleeperbot.util.AlarmReceiver;

public class DismissAlarmActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismiss_alarm);

        // Keeps the screen on to provide visual cue of alarm.
        focusActivity();

        // Handles dismissal of the alarm when it fires and pushes this activity to the screen.
        Button dismissAlarmButton = (Button) findViewById(R.id.button_activity_dismiss_dismissalarm);
        dismissAlarmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cancelAlarm();
                // Return to the main activity
                Intent mainActivityIntent = new Intent(getBaseContext(), AlarmsActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        });
    }

    /**
     * Pushes alarm dismissal screen to the forefront of the user's attention.
     */
    private void focusActivity()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                             WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                             WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                             WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    /**
     * Sends a message to the Android OS {@link AlarmManager} to cancel the specified alarm.
     */
    private void cancelAlarm()
    {
        // TODO: Are we sure that the alarm we cancel is ours?
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent dismissAlarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, dismissAlarmIntent, 0);

        alarmManager.cancel(pendingIntent);
    }
}
