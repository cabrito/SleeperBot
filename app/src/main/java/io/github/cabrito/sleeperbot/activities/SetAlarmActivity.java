package io.github.cabrito.sleeperbot.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

import io.github.cabrito.sleeperbot.R;
import io.github.cabrito.sleeperbot.fragments.DaysOfWeekDialog;
import io.github.cabrito.sleeperbot.fragments.TimePickerFragment;
import io.github.cabrito.sleeperbot.util.Alarm;
import io.github.cabrito.sleeperbot.util.AlarmReceiver;
import io.github.cabrito.sleeperbot.util.UtilTime;

public class SetAlarmActivity extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener,
                                                                    DaysOfWeekDialog.DaysOfWeekDialogListener
{
    Calendar calendar;
    TextView timeTextView;
    TextView daysTextView;
    EditText alarmTitleField;
    boolean[] daysActivated;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        // TODO: Reuse this screen for existing alarms by grabbing an Intent with args
        timeTextView = (TextView) findViewById(R.id.textview_activity_setalarm_time);
        daysTextView = (TextView) findViewById(R.id.textview_activity_setalarm_daysofweek);
        alarmTitleField = (EditText) findViewById(R.id.edittext_activity_setalarm_alarmtitle);
        Button daysOfWeekButton = (Button) findViewById(R.id.button_activity_setalarm_setdaysofweek);
        Button button = (Button) findViewById(R.id.button_activity_setalarm_setalarm);

        // Work to accomplish if this is the first time the screen is being loaded.
        if(savedInstanceState == null)
        {
            calendar = Calendar.getInstance();
            timeTextView.setText(UtilTime.formatTime(this, calendar));
        }

        // Go ahead and put the days the alarm should be activating
        daysTextView.setText(UtilTime.daysActivated(this, daysActivated));

        // Set the appropriate listeners
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAlarm(calendar);
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bundle timeInfo = new Bundle();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timeInfo.putInt("hour", hour);
                timeInfo.putInt("minute", minute);
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setArguments(timeInfo);
                timePickerFragment.show(getSupportFragmentManager(), "Time Picker Dialog");
            }
        });

        daysOfWeekButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDaysOfWeekDialog();
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        // Set the calendar with the new date
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Put the locale-aware text in the view
        timeTextView.setText(UtilTime.formatTime(this, calendar));
    }

    @Override
    public void checkDays(boolean[] daysOfWeek)
    {
        this.daysActivated = daysOfWeek;
        daysTextView.setText(UtilTime.daysActivated(this, daysActivated));
    }

    // NOTE: Android calls onCreate(), onSaveInstanceState(), and onRestoreInstanceState() in that order!
    /**
     * Used to restore information on the screen if the device encounters a change like rotation.
     * @param outState A Bundle containing important information to restore.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("hour", calendar.get(Calendar.HOUR_OF_DAY));
        outState.putInt("minute", calendar.get(Calendar.MINUTE));
        outState.putBooleanArray("daysActivated", daysActivated);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, savedInstanceState.getInt("hour"));
        calendar.set(Calendar.MINUTE, savedInstanceState.getInt("minute"));
        daysActivated = savedInstanceState.getBooleanArray("daysActivated");

        // Put the locale-aware text in the views
        timeTextView.setText(UtilTime.formatTime(this, calendar));
        daysTextView.setText(UtilTime.daysActivated(this, daysActivated));

    }

    /**
     * Asks the Android AlarmManager to start the DismissAlarm activity at the specified time.
     * @param c A Calendar object containing specific time information for the alarm.
     */
    private void setAlarm(Calendar c)
    {
        // If the user didn't choose a day to set the alarm for, then just do it for the next day.
        if (UtilTime.isNoDaySet(daysActivated))
        {
            // If the desired time is before now, assume the user wants the alarm tomorrow.
            if (c.before(Calendar.getInstance()))
            {
                c.add(Calendar.DATE, 1);
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        } else
        {
            // Put some PendingIntents for each day of the week that the alarm should fire.
            for (int i = 0; i < daysActivated.length; i++)
            {
                if (daysActivated[i])
                {
                    scheduleAlarms(i);
                }
            }
        }

        // Place the main Alarm data in the preferences for later
        Gson gson = new Gson();
        Alarm alarm = new Alarm(
                alarmTitleField.getText().toString(),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                daysActivated,
                true);
        SharedPreferences alarmsPreferences = getSharedPreferences(
                getString(R.string.alarms_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = alarmsPreferences.edit();
        editor.putString(String.valueOf(alarm.getId()), gson.toJson(alarm));
        editor.apply();

        finish();
    }

    /**
     * Helper function for scheduling repeating alarms
     * @param dayOfWeek
     */
    private void scheduleAlarms(int dayOfWeek)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, 0);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        // If the alarm is scheduled to be before now, place a PendingIntent a week into the future
        if (c.before(Calendar.getInstance()))
            c.add(Calendar.DAY_OF_YEAR, UtilTime.NUMBER_OF_DAYS_OF_WEEK);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * UtilTime.NUMBER_OF_DAYS_OF_WEEK,
                pendingIntent);

        // Debug
        Toast.makeText(this, "Alarm Started", Toast.LENGTH_LONG).show();
    }

    private void openDaysOfWeekDialog()
    {
        Bundle args = new Bundle();
        if (daysActivated != null)
            args.putBooleanArray("daysActivated", daysActivated);
        else
            args.putBooleanArray("daysActivated", new boolean[UtilTime.NUMBER_OF_DAYS_OF_WEEK]);
        DaysOfWeekDialog dialog = new DaysOfWeekDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "Days of Week Dialog");
    }
}
