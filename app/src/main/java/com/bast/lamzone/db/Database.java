package com.bast.lamzone.db;

import com.bast.lamzone.models.Reunion;

import java.util.ArrayList;
import java.util.List;

class Database {

    private static List<Reunion> REUNIONCREATED = new ArrayList<>();/*Arrays.asList(
            new Reunion(1, 14, 00, "vendredi", 31, "janvier", 2020, "Bast", "Bast"),
            new Reunion(1, 15, 00, "lundi", 3, "février", 2020, "Julie", "Bast"),
            new Reunion(2, 14, 00, "vendredi", 31, "janvier", 2020, "Marco", "Bast"),
            new Reunion(2, 15, 00, "mercredi", 4, "mars", 2020, "Loulou", "Bast"),
            new Reunion(3, 14, 00, "lundi", 3, "février", 2020, "Marius", "Bast"),
            new Reunion(3, 15, 00, "dimanche", 16, "février", 2020, "Thibault", "Bast"),
            new Reunion(4, 18, 00, "dimanche", 1, "février", 2020, "Max", "Bast"),
            new Reunion(5, 19, 00, "mercredi", 4, "mars", 2020, "Flavie", "Bast")
    );*/
    private static List<Reunion> REUNIONFILTERED = new ArrayList<>();
    private static List<Reunion> ROOMFILTERED = new ArrayList<>();
    private static List<Reunion> DATEFILTERED = new ArrayList<>();

    static List<Reunion> generateReunion(){
        return new ArrayList<>(REUNIONCREATED);
    }

    static List<Reunion> generateReunionFiltered() {
        return new ArrayList<>(REUNIONFILTERED);
    }

    static List<Reunion> generateDateFiltered() {
        return new ArrayList<>(DATEFILTERED);
    }

    static List<Reunion> generateRoomFiltered() {
        return new ArrayList<>(ROOMFILTERED);
    }
}
