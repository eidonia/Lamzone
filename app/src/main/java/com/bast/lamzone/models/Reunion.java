package com.bast.lamzone.models;

public class Reunion {
     int salle;
     int heure;
     int minute;
     String host;
     String participants;

     public Reunion(int salle, int heure, int minute, String host, String participants) {
         this.salle = salle;
         this.heure = heure;
         this.minute = minute;
         this.host = host;
         this.participants = participants;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}
