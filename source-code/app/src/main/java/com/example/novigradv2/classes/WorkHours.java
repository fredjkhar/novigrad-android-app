package com.example.novigradv2.classes;

import java.io.Serializable;

public class WorkHours implements Serializable {

    private String day;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public WorkHours(String day, int startHour, int startMinute, int endHour, int endMinute) {
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public WorkHours() {
    }

    public String getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }
}
