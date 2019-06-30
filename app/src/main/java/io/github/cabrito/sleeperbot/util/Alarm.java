package io.github.cabrito.sleeperbot.util;

/**
 * Embodies the concept of a device alarm
 */
public class Alarm
{
    // Members
    private final long id;
    private String title;
    private byte hour;
    private byte minute;
    private boolean[] days;
    private boolean enabled;

    public Alarm(String title, int hour, int minute, boolean[] days, boolean enabled)
    {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.hour = (byte) hour;
        this.minute = (byte) minute;
        this.days = days;
        this.enabled = enabled;
    }

    public long getId()
    {
        return id;
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
}
