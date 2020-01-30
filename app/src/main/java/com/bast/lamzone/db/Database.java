package com.bast.lamzone.db;

import com.bast.lamzone.models.Reunion;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public static List<Reunion> REUNIONCREATED = new ArrayList<>();
    public static List<Reunion> REUNIONFILTERED = new ArrayList<>();

    static List<Reunion> generateReunion(){
        return new ArrayList<>(REUNIONCREATED);
    }

    static List<Reunion> generateReunionFiltered() {
        return new ArrayList<>(REUNIONFILTERED);
    }
}
