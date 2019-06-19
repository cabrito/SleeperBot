package io.github.cabrito.sleeperbot.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import io.github.cabrito.sleeperbot.R;
import io.github.cabrito.sleeperbot.util.Alarm;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmsViewHolder>
{
    private ArrayList<Alarm> alarmsList;

    public static class AlarmsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView alarmTitle;
        public TextView alarmTime;
        public TextView daysActivated;
        public Switch alarmSwitch;

        public AlarmsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.alarmTitle = itemView.findViewById(R.id.textview_activity_alarms_alarmtitle);
            this.alarmTime = itemView.findViewById(R.id.textview_activity_alarms_alarmtime);
            this.daysActivated = itemView.findViewById(R.id.textview_activity_alarms_daysactivated);
            this.alarmSwitch = itemView.findViewById(R.id.switch_activity_alarms_alarmswitch);
        }
    }

    public AlarmsAdapter(ArrayList<Alarm> alarmsList)
    {
        this.alarmsList = alarmsList;
    }

    @NonNull
    @Override
    public AlarmsAdapter.AlarmsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_alarm, viewGroup, false);
        AlarmsViewHolder alarmsViewHolder = new AlarmsViewHolder(view);
        return alarmsViewHolder;
    }

    /**
     * Sets all of the necessary fields on the Alarm in the Card for displaying to the user.
     */
    @Override
    public void onBindViewHolder(@NonNull AlarmsViewHolder alarmsViewHolder, int position)
    {
        Alarm currAlarm = alarmsList.get(position);
        alarmsViewHolder.alarmTitle.setText(currAlarm.getTitle());
        alarmsViewHolder.alarmTime.setText(buildAlarmTime(currAlarm));
        alarmsViewHolder.daysActivated.setText(buildDaysActivated(currAlarm));
        alarmsViewHolder.alarmSwitch.setChecked(currAlarm.isEnabled());
    }

    @Override
    public int getItemCount()
    {
        return alarmsList.size();
    }

    /**
     * Formats the time in the Alarm Card to be human-readable.
     * @return String such as 17:30 or 8:50 AM, depending on user's locale.
     */
    private String buildAlarmTime(Alarm alarm)
    {
        NumberFormat formatter = new DecimalFormat("00");
        String result = alarm.getHour() + ":" + formatter.format(alarm.getMinute());
        // TODO: Add logic that accounts for locales
        return result;
    }

    /**
     * Provides concise, human-readable representation of the days of the week the alarm should fire.
     * @return String with specific short day names, or "Weekdays"/"Weekends" if applicable.
     */
    private String buildDaysActivated(Alarm alarm)
    {
        // Check to see if the alarm is weekdays only, weekends only, or variable
        if (alarm.isWeekdaysOnly())
            return "Weekdays";
        else if (alarm.isWeekendsOnly())
            return "Weekends";
        else
        {
            String[] daysOfWeek = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < daysOfWeek.length; i++)
            {
                if (alarm.getDays()[i])
                {
                    sb.append(daysOfWeek[i]).append(" ");
                }
            }
            return sb.toString();
        }
    }
}
