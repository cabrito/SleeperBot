package io.github.cabrito.sleeperbot.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
            }
        });
    }

    private void cancelAlarm()
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent dismissAlarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, dismissAlarmIntent, 0);

        alarmManager.cancel(pendingIntent);
    }
}
