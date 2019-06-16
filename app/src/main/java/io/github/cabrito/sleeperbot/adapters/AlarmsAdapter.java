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

    private String buildAlarmTime(Alarm alarm)
    {
        NumberFormat formatter = new DecimalFormat("00");
        String result = alarm.getHour() + ":" + formatter.format(alarm.getMinute());
        // TODO: Add logic that accounts for locales
        return result;
    }

    private String buildDaysActivated(Alarm alarm)
    {
        // Check to see if the alarm is weekdays only, weekends only, or variable
        if (alarm.isWeekdaysOnly())
            return "Weekdays";
        else if (alarm.isWeekendsOnly())
            return "Weekends";
        else
        {
            String[] daysOfWeek = new String[]{"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
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
