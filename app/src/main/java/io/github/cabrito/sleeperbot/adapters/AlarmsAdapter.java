package io.github.cabrito.sleeperbot.adapters;

import android.content.Context;
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
import java.util.Calendar;

import io.github.cabrito.sleeperbot.R;
import io.github.cabrito.sleeperbot.util.Alarm;
import io.github.cabrito.sleeperbot.util.UtilTime;

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
        alarmsViewHolder.alarmTime.setText(
                UtilTime.formatTime(alarmsViewHolder.itemView.getContext(), getCalendar(currAlarm)));
        alarmsViewHolder.daysActivated.setText(
                UtilTime.daysActivated(alarmsViewHolder.itemView.getContext(), currAlarm.getDays()));
        alarmsViewHolder.alarmSwitch.setChecked(currAlarm.isEnabled());
    }

    @Override
    public int getItemCount()
    {
        return alarmsList.size();
    }

    private Calendar getCalendar(Alarm alarm)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        c.set(Calendar.MINUTE, alarm.getMinute());
        c.set(Calendar.SECOND, 0);

        return c;
    }


}
