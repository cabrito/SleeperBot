package io.github.cabrito.sleeperbot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.github.cabrito.sleeperbot.R;
import io.github.cabrito.sleeperbot.adapters.AlarmsAdapter;
import io.github.cabrito.sleeperbot.util.Alarm;

public class AlarmsActivity extends AppCompatActivity
{
    private RecyclerView alarmsRecyclerView;
    private RecyclerView.Adapter alarmsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        alarmsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_activity_alarms);
        alarmsRecyclerView.setHasFixedSize(true);   // Helps to save on performance

        // Define our layout manager
        layoutManager = new LinearLayoutManager(this);
        alarmsRecyclerView.setLayoutManager(layoutManager);

        // TODO: Specify the adapter for the alarms list

        ArrayList<Alarm> alarmsList = new ArrayList<>();
        alarmsList.add(new Alarm("My First Alarm", 3, 30, new boolean[] {false, true, false, false, false, false, false}, true));
        alarmsList.add(new Alarm("My Second Alarm", 4, 20, new boolean[] {true, false, true, false, true, false, true}, false));
        alarmsList.add(new Alarm("Weekdays-Only Alarm", 5, 0, new boolean[] {false, true, true, true, true, true, false}, false));
        alarmsList.add(new Alarm("Weekends-Only Alarm", 23, 15, new boolean[] {true, false, false, false, false, false, true}, false));
        alarmsAdapter = new AlarmsAdapter(alarmsList);
        alarmsRecyclerView.setAdapter(alarmsAdapter);
    }

    private void setAlarm()
    {

    }

}
