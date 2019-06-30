package io.github.cabrito.sleeperbot.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import io.github.cabrito.sleeperbot.R;

public class DaysOfWeekDialog extends AppCompatDialogFragment
{
    private CheckBox    sunday,
                        monday,
                        tuesday,
                        wednesday,
                        thursday,
                        friday,
                        saturday;
    private DaysOfWeekDialogListener listener;
    private final int NUMBER_OF_DAYS_IN_WEEK = 7;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_dialog_daysofweek, null);

        // Find all the checkboxes
        sunday = view.findViewById(R.id.checkbox_dialog_daysofweek_sunday);
        monday = view.findViewById(R.id.checkbox_dialog_daysofweek_monday);
        tuesday = view.findViewById(R.id.checkbox_dialog_daysofweek_tuesday);
        wednesday = view.findViewById(R.id.checkbox_dialog_daysofweek_wednesday);
        thursday = view.findViewById(R.id.checkbox_dialog_daysofweek_thursday);
        friday = view.findViewById(R.id.checkbox_dialog_daysofweek_friday);
        saturday = view.findViewById(R.id.checkbox_dialog_daysofweek_saturday);

        builder.setView(view)
                .setTitle(R.string.dialog_set_daysofweek_title)
                .setPositiveButton(R.string.dialog_set_daysofweek_positive, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        boolean[] days = new boolean[NUMBER_OF_DAYS_IN_WEEK];

                        days[0] = sunday.isChecked();
                        days[1] = monday.isChecked();
                        days[2] = tuesday.isChecked();
                        days[3] = wednesday.isChecked();
                        days[4] = thursday.isChecked();
                        days[5] = friday.isChecked();
                        days[6] = saturday.isChecked();

                        listener.checkDays(days);
                    }
                })
                .setNegativeButton(R.string.dialog_set_daysofweek_negative, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (DaysOfWeekDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " needs to implement DaysOfWeekDialogListener.");
        }
    }

    public interface DaysOfWeekDialogListener
    {
        void checkDays(boolean[] daysOfWeek);
    }
}
