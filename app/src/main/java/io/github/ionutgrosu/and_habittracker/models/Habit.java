package io.github.ionutgrosu.and_habittracker.models;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import io.github.ionutgrosu.and_habittracker.utils.Utils;

public class Habit {

    private String id;
    private String name;
    private Date startDate;
    private Date lastCheckDate;
    private int duration;
    private int progress;


    public Habit() {
    }

    public Habit(String name, Date startDate, int duration) {
        this.name = name;
        this.startDate = startDate;
        lastCheckDate = Utils.yesterday();
        this.duration = duration;
        progress = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void increaseProgress(){
        progress++;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", lastCheckDate=" + lastCheckDate +
                ", duration=" + duration +
                ", progress=" + progress +
                '}';
    }
}
