package com.bast.lamzone.models;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Reunion {

    private static final AtomicInteger counter = new AtomicInteger(0);
    private int reuID;
    private int salle;
    private int heure;
    private int minute;
    private int heureF;
    private int minuteF;
    private String host;
    private List<String> participants;
    private int minutes;
    private String day;
    private int dateDay;
    private String month;
    private int year;

    public Reunion(int salle, int heure, int minute, int heureF, int minuteF, String day, int dateDay, String month, int year, String host, List<String> participants) {
        this.reuID = counter.incrementAndGet();
        this.salle = salle;
        this.heure = heure;
        this.minute = minute;
        this.heureF = heureF;
        this.minuteF = minuteF;
        this.day = day;
        this.dateDay = dateDay;
        this.month = month;
        this.year = year;
        this.host = host;
        this.participants = participants;
     }


    public int getReuID() {
        return reuID;
    }

    public void setReuID(int reuID) {
        this.reuID = reuID;
    }

    public int getSalle() {
        return salle;
    }

    public void setSalle(int salle) {
        this.salle = salle;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHeureF() {
        return heureF;
    }

    public void setHeureF(int heureF) {
        this.heureF = heureF;
    }

    public int getMinuteF() {
        return minuteF;
    }

    public void setMinuteF(int minuteF) {
        this.minuteF = minuteF;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
