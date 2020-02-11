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

    @Override
    public void deleteReunion(Reunion reunion) {
        this.reunion.remove(reunion);
    }

    @Override
    public void deleteReunionFilt(Reunion reunion) {
        reunionFiltered.remove(reunion);
    }

}
