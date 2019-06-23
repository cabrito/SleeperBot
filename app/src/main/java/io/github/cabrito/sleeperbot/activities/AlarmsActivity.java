package io.github.cabrito.sleeperbot.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

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

        // For dev-purposes, have the New Alarm button take the user to set an alarm
        Button newAlarmButton = findViewById(R.id.button_new_alarm);
        newAlarmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getBaseContext(), SetAlarmActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Alarm> alarmsList = new ArrayList<>();
        alarmsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_activity_alarms);
        alarmsRecyclerView.setHasFixedSize(true);   // Resizing of list layout not expected, so this saves on performance

        // Define our layout manager
        layoutManager = new LinearLayoutManager(this);
        alarmsRecyclerView.setLayoutManager(layoutManager);

        // Make some fake alarms
        alarmsList.add(new Alarm("My First Alarm", 3, 30, new boolean[] {false, true, false, false, false, false, false}, true));
        alarmsList.add(new Alarm("My Second Alarm", 4, 20, new boolean[] {true, false, true, false, true, false, true}, false));
        alarmsList.add(new Alarm("Weekdays-Only Alarm", 5, 0, new boolean[] {false, true, true, true, true, true, false}, false));
        alarmsList.add(new Alarm("Weekends-Only Alarm", 23, 15, new boolean[] {true, false, false, false, false, false, true}, false));

        // Set the adapter for the RecyclerView and the list of alarms.
        alarmsAdapter = new AlarmsAdapter(alarmsList);
        alarmsRecyclerView.setAdapter(alarmsAdapter);
    }

    private void setAlarm()
    {

    }

    private void displayJSON(ArrayList<Alarm> alarms)
    {

    }
}
