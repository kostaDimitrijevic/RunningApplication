package com.example.runningapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

//predstavlja tabelu, entitet
@Entity
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private Date date;
    private String label;
    private double distance;
    private double duration;

    public Workout(long id, Date date, String label, double distance, double duration) {
        this.id = id;
        this.date = date;
        this.label = label;
        this.distance = distance;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getLabel() {
        return label;
    }

    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
