package io.github.cabrito.sleeperbot.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragment extends AppCompatDialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Context activity = getActivity();

        // We set hour and minute into the Bundle outside in the activities, and request them here.
        int hour = savedInstanceState.getInt("hour");
        int minute = savedInstanceState.getInt("minute");

        // Provide the user the specially crafted TimePickerDialog.
        return new TimePickerDialog(activity,
                                    (TimePickerDialog.OnTimeSetListener) activity,
                                    hour,
                                    minute,
                                    DateFormat.is24HourFormat(activity));
    }
}
