package io.github.cabrito.sleeperbot.util;

/**
 * Embodies the concept of a device alarm
 */
public class Alarm
{
    private String title;
    private byte hour;
    private byte minute;
    private boolean[] days;
    private boolean enabled;
    // Constants
    private final int NUMBER_OF_DAYS_OF_WEEK = 7;

    public Alarm(String title, int hour, int minute, boolean[] days, boolean enabled)
    {
        this.title = title;
        this.hour = (byte) hour;
        this.minute = (byte) minute;
        this.days = days;
        this.enabled = enabled;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public byte getHour()
    {
        return hour;
    }

    public void setHour(byte hour)
    {
        this.hour = hour;
    }

    public byte getMinute()
    {
        return minute;
    }

    public void setMinute(byte minute)
    {
        this.minute = minute;
    }

    public boolean[] getDays()
    {
        return days;
    }

    public void setDays(boolean[] days)
    {
        this.days = days;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isWeekdaysOnly()
    {
        // If either Sun or Sat are activated, it's not weekdays only
        if (days[0] || days[NUMBER_OF_DAYS_OF_WEEK - 1])
            return false;
        // Otherwise, scroll through Monday to Friday. If any are not activated, it's not weekdays only
        else
        {
            for (int i = 1; i < NUMBER_OF_DAYS_OF_WEEK - 1; i++)
            {
                if(!days[i])
                    return false;
            }
        }
        return true;
    }

    public boolean isWeekendsOnly()
    {
        // If neither Sun nor Sat are activated, it's not weekends only.
        if (!days[0] || !days[NUMBER_OF_DAYS_OF_WEEK - 1])
            return false;
        // Otherwise, scroll through Monday to Friday. If any are activated, it's not weekends only.
        else
        {
            for (int i = 1; i < NUMBER_OF_DAYS_OF_WEEK - 1; i++)
            {
                if(days[i])
                    return false;
            }
        }
        return true;
    }
}
