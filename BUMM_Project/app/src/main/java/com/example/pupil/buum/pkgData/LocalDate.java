package com.example.pupil.buum.pkgData;

import java.io.Serializable;

public class LocalDate implements Serializable{
    private int day,month,year;

    public LocalDate() {
    }

    public LocalDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    @Override
    public String toString() {
        return ""+day +"."+month+"."+year;
    }
}

