package com.bast.lamzone.db;

import com.bast.lamzone.models.Reunion;

import java.util.List;

public class ReuApiServ implements ApiServiceReu{

    private List<Reunion> reunion = Database.generateReunion();
    private List<Reunion> reunionFiltered = Database.generateReunionFiltered();

    @Override
    public List<Reunion> getReunion() {
        return reunion;
    }

    @Override
    public List<Reunion> getReunionFiltered() {
        return reunionFiltered;
    }

}
