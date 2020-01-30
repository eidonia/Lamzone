package com.bast.lamzone.db;

import com.bast.lamzone.models.Reunion;

import java.util.List;

public interface ApiServiceReu {

    List<Reunion> getReunion();

    List<Reunion> getReunionFiltered();
}
