package io.github.ionutgrosu.and_habittracker;

import java.util.Date;

public class Habit {

    private String name;
    private Date startDate;
    private int duration;

    public Habit(String name, Date startDate, int duration) {
        this.name = name;
        this.startDate = startDate;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", duration=" + duration +
                '}';
    }
}
