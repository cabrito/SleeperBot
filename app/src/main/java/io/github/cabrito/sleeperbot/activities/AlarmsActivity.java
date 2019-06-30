package io.github.cabrito.sleeperbot.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

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

        ArrayList<Alarm> alarmsList = getAlarmsList();
        alarmsRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_activity_alarms);
        alarmsRecyclerView.setHasFixedSize(true);   // Resizing of list layout not expected, so this saves on performance

        // Define our layout manager
        layoutManager = new LinearLayoutManager(this);
        alarmsRecyclerView.setLayoutManager(layoutManager);

        // Make some fake alarms
        /*alarmsList.add(new Alarm("My First Alarm", 3, 30, new boolean[] {false, true, false, false, false, false, false}, true));
        alarmsList.add(new Alarm("My Second Alarm", 4, 20, new boolean[] {true, false, true, false, true, false, true}, false));
        alarmsList.add(new Alarm("Weekdays-Only Alarm", 5, 0, new boolean[] {false, true, true, true, true, true, false}, false));
        alarmsList.add(new Alarm("Weekends-Only Alarm", 23, 15, new boolean[] {true, false, false, false, false, false, true}, false));*/

        // Set the adapter for the RecyclerView and the list of alarms.
        alarmsAdapter = new AlarmsAdapter(alarmsList);
        alarmsRecyclerView.setAdapter(alarmsAdapter);
    }

    private void toggleAlarm()
    {

    }

    private ArrayList<Alarm> getAlarmsList()
    {
        ArrayList<Alarm> alarmsList = new ArrayList<>();
        Gson gson = new Gson();

        // Grab the list of all the alarms
        SharedPreferences alarmsPreferences = getSharedPreferences(
                getString(R.string.alarms_filename), Context.MODE_PRIVATE);
        // Get ALL the alarms listed
        Map<String, ?> alarmsCollection = alarmsPreferences.getAll();

        // Add each one to the alarms list
        for (Map.Entry<String, ?> entry : alarmsCollection.entrySet())
        {
            String json = entry.getValue().toString();
            Alarm alarm = gson.fromJson(json, Alarm.class);
            alarmsList.add(alarm);
        }
        return alarmsList;
    }

    /**
     * A test function that demonstrates how to use Gson for producing a JSONArray of the alarms,
     * as well as how to make the JSON an ArrayList again.
     * @param alarms An ArrayList of Alarms
     * @return The same, but completely different ArrayList of Alarms
     */
    private ArrayList<Alarm> convertAndReconvert(ArrayList<Alarm> alarms)
    {
        // How to output the ArrayList to a JSONArray
        Gson gson = new Gson();
        String jsonObject = gson.toJson(alarms);
        System.out.println(jsonObject);

        // How to convert the JSONArray text to an ArrayList of Alarms.
        Type alarmsListType = new TypeToken<ArrayList<Alarm>>() {}.getType();
        return gson.fromJson(jsonObject, alarmsListType);
    }
}
