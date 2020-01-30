package com.bast.lamzone.filter;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ListFilter {
    CheckBox salle1, salle2, salle3, salle4, salle5, salle6, salle7, salle8, salle9, salle10;
    TextView btnFilterDate, btnFilterHour;
    EditText editHost;

    public ListFilter(CheckBox salle1, CheckBox salle2, CheckBox salle3, CheckBox salle4, CheckBox salle5, CheckBox salle6, CheckBox salle7, CheckBox salle8, CheckBox salle9, CheckBox salle10, TextView btnFilterDate, TextView btnFilterHour, EditText editHost) {
        this.salle1 = salle1;
        this.salle2 = salle2;
        this.salle3 = salle3;
        this.salle4 = salle4;
        this.salle5 = salle5;
        this.salle6 = salle6;
        this.salle7 = salle7;
        this.salle8 = salle8;
        this.salle9 = salle9;
        this.salle10 = salle10;
        this.btnFilterDate = btnFilterDate;
        this.btnFilterHour = btnFilterHour;
        this.editHost = editHost;
    }


}
