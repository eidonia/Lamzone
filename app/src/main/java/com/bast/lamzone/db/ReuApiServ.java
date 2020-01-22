package com.bast.lamzone.db;

import com.bast.lamzone.models.Reunion;

import java.util.List;

public class ReuApiServ implements ApiServiceReu{

    private List<Reunion> reunion = Database.generateReunion();

    @Override
    public List<Reunion> getReunion() {
        return reunion;
    }
}
