package io.github.cabrito.sleeperbot.util;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Calendar;

import io.github.cabrito.sleeperbot.R;

public class UtilTime
{
    private static final int NUMBER_OF_DAYS_OF_WEEK = 7;

    public static String daysActivated(Context context, boolean[] daysArray)
    {
        // Failsafe
        if (isNoDaySet(daysArray))
            return context.getResources().getString(R.string.activity_setalarm_none);

        if (isWeekendsOnly(daysArray))
            return context.getResources().getString(R.string.weekends);
        else if (isWeekdaysOnly(daysArray))
            return context.getResources().getString(R.string.weekdays);
        else
        {
            // Build the String indicating the days the alarm will go off.
            String[] daysOfWeek = new String[]{
                    context.getResources().getString(R.string.short_sunday),
                    context.getResources().getString(R.string.short_monday),
                    context.getResources().getString(R.string.short_tuesday),
                    context.getResources().getString(R.string.short_wednesday),
                    context.getResources().getString(R.string.short_thursday),
                    context.getResources().getString(R.string.short_friday),
                    context.getResources().getString(R.string.short_saturday)};
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < daysOfWeek.length; i++)
            {
                if (daysArray[i])
                {
                    sb.append(daysOfWeek[i]).append(" ");
                }
            }
            return sb.toString();
        }
    }

    public static String formatTime(Context context, Calendar c)
    {
        return DateFormat.getTimeFormat(context).format(c.getTime());
    }

    private static boolean isWeekdaysOnly(boolean[] daysArray)
    {
        // If either Sun or Sat are activated, it's not weekdays only
        if (daysArray[0] || daysArray[NUMBER_OF_DAYS_OF_WEEK - 1])
            return false;
            // Otherwise, scroll through Monday to Friday. If any are not activated, it's not weekdays only
        else
        {
            for (int i = 1; i < NUMBER_OF_DAYS_OF_WEEK - 1; i++)
            {
                if(!daysArray[i])
                    return false;
            }
        }
        return true;
    }

    private static boolean isWeekendsOnly(boolean[] daysArray)
    {
        // If neither Sun nor Sat are activated, it's not weekends only.
        if (!daysArray[0] || !daysArray[NUMBER_OF_DAYS_OF_WEEK - 1])
            return false;
            // Otherwise, scroll through Monday to Friday. If any are activated, it's not weekends only.
        else
        {
            for (int i = 1; i < NUMBER_OF_DAYS_OF_WEEK - 1; i++)
            {
                if(daysArray[i])
                    return false;
            }
        }
        return true;
    }

    private static boolean isNoDaySet(boolean[] daysArray)
    {
        if (daysArray == null)
            return true;
        for (boolean b : daysArray)
        {
            if (b)
                return false;
        }
        return true;
    }
}
