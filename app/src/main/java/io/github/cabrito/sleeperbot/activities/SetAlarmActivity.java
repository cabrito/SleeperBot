package io.github.cabrito.sleeperbot.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import io.github.cabrito.sleeperbot.R;
import io.github.cabrito.sleeperbot.util.AlarmReceiver;

public class SetAlarmActivity extends AppCompatActivity
{
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        // TODO: Cater to the user's locale if they're using AM or PM
        Button setAlarmButton = (Button) findViewById(R.id.button_activity_setalarm_set);
        timePicker = findViewById(R.id.timepicker_activity_setalarm);
        timePicker.setIs24HourView(true);

        setAlarmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: Have a more dynamic way to set the time the alarm will go off
                Calendar c = Calendar.getInstance();
                byte currHour, currMinute;

                // Depending on the version of Android on the device, we must access TimePicker data differently.
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    currHour = (byte)timePicker.getHour();
                    currMinute = (byte)timePicker.getMinute();
                } else
                {
                    currHour = (byte)timePicker.getCurrentHour().intValue();
                    currMinute = (byte)timePicker.getCurrentMinute().intValue();
                }

                // Use information from the TimePicker to set the user's alarm.
                c.set(Calendar.HOUR_OF_DAY, currHour);
                c.set(Calendar.MINUTE, currMinute);
                c.set(Calendar.SECOND, 0);

                startAlarm(c);
            }
        });
    }

    /**
     * Asks the Android AlarmManager to start the DismissAlarm activity at the specified time.
     * @param c A Calendar object containing specific time information for the alarm.
     */
    private void startAlarm(Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        // If the desired time is before now, assume the user wants the alarm tomorrow.
        if (c.before(Calendar.getInstance()))
        {
            c.add(Calendar.DATE, 1);
        }

        // Lastly, actually set the alarm manager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        // Debug
        Toast.makeText(this,"Alarm Started",Toast.LENGTH_LONG).show();
    }
}
