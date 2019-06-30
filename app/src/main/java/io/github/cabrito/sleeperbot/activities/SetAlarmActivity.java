package io.github.cabrito.sleeperbot.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
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

public class SetAlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DaysOfWeekDialog.DaysOfWeekDialogListener
{
    Calendar calendar;
    TextView timeTextView;
    TextView daysTextView;
    EditText alarmTitleField;
    boolean[] daysSet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        timeTextView = (TextView) findViewById(R.id.textview_activity_setalarm_time);
        daysTextView = (TextView) findViewById(R.id.textview_activity_setalarm_daysofweek);
        alarmTitleField = (EditText) findViewById(R.id.edittext_activity_setalarm_alarmtitle);
        Button daysOfWeekButton = (Button) findViewById(R.id.button_activity_setalarm_setdaysofweek);
        Button button = (Button) findViewById(R.id.button_activity_setalarm_setalarm);

        // Work to accomplish if this is the first time the screen is being loaded.
        if(savedInstanceState == null)
        {
            calendar = Calendar.getInstance();
            timeTextView.setText(formatTime(calendar));
        }

        // TODO: Make method to detect if all days are false
        if (areNoDaysSet(daysSet))
            daysTextView.setText(R.string.activity_setalarm_none);

        // Set the appropriate listeners
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startAlarm(calendar);
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
        timeTextView.setText(formatTime(calendar));
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, savedInstanceState.getInt("hour"));
        calendar.set(Calendar.MINUTE, savedInstanceState.getInt("minute"));

        // Put the locale-aware text in the view
        timeTextView.setText(formatTime(calendar));
        formatDaysText();
    }

    /**
     * Asks the Android AlarmManager to start the DismissAlarm activity at the specified time.
     * @param c A Calendar object containing specific time information for the alarm.
     */
    private void startAlarm(Calendar c)
    {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 100, intent, 0);

        // If the desired time is before now, assume the user wants the alarm tomorrow.
        if (c.before(Calendar.getInstance()))
        {
            c.add(Calendar.DATE, 1);
        }

        // Place this alarm in the preferences
        Gson gson = new Gson();
        Alarm alarm = new Alarm(
                alarmTitleField.getText().toString(),
                c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                daysSet,
                true);

        SharedPreferences alarmsPreferences = getSharedPreferences(
                getString(R.string.alarms_filename), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = alarmsPreferences.edit();
        editor.putString(String.valueOf(System.currentTimeMillis()), gson.toJson(alarm));
        editor.apply();

        // Lastly, actually set the alarm manager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        // Debug
        Toast.makeText(this, "Alarm Started", Toast.LENGTH_LONG).show();
        // TODO: Bring us back to the alarms list with the updated list
        finish();
    }

    /**
     * Formats the time specific to the device's locale
     * @param c Calendar containing a specific time.
     * @return  Formatted time as a String, such as "22:04" or "10:04 PM".
     */
    private String formatTime(Calendar c)
    {
        return DateFormat.getTimeFormat(this).format(c.getTime());
    }

    private void openDaysOfWeekDialog()
    {
        DaysOfWeekDialog dialog = new DaysOfWeekDialog();
        dialog.show(getSupportFragmentManager(), "Days of Week Dialog");
    }

    @Override
    public void checkDays(boolean[] daysOfWeek)
    {
        this.daysSet = daysOfWeek;
        formatDaysText();
    }

    private void formatDaysText()
    {
        // TODO: Avoid hard-coding the language here.
        String[] daysOfWeek = new String[]{
                "Sun",
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < daysOfWeek.length; i++)
        {
            if (daysSet[i])
            {
                sb.append(daysOfWeek[i]).append(" ");
            }
        }
        daysTextView.setText(sb.toString());
    }

    private boolean areNoDaysSet(boolean[] days)
    {
        if (days == null)
            return true;

        for (boolean b : days)
        {
            if (b)
                return false;
        }
        return true;
    }
}
