package com.bast.lamzone.models;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Time {

    int heure;
    int minutes;
    String day;
    int dateDay;
    String month;
    int year;
    Calendar date = Calendar.getInstance();

    public int getHeure(){
        return date.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinutes(){
       return date.get(Calendar.MINUTE);
    }

    public String getDay(){
        return getDay(date.get(Calendar.DAY_OF_WEEK)) ;

    }

    public int getDateDay(){
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public String getMonth(){
        return getMonth(date.get(Calendar.MONTH));
    }

    public int getYear(){
        return date.get(Calendar.YEAR);
    }



    public String getDay(int dayInt){

        String day="";
        switch (dayInt) {
            case 1:
                day = "Dimanche";
                break;
            case 2:
                day = "Lundi";
                break;
            case 3:
                day = "Mardi";
                break;
            case 4:
                day = "Mercredi";
                break;
            case 5:
                day = "Jeudi";
                break;
            case 6:
                day = "Vendredi";
                break;
            default:
                day = "Samedi";
                break;
        }
        return day;

    }
    public String getMonth(int monthInt) {
        String month = "";
        switch (monthInt) {
            case 0:
                month = "Janvier";
                break;
            case 1:
                month = "Février";
                break;
            case 2:
                month = "Mars";
                break;
            case 3:
                month = "Avril";
                break;
            case 4:
                month = "Mai";
                break;
            case 5:
                month = "Juin";
                break;
            case 6:
                month = "Juillet";
                break;
            case 7:
                month = "Aout";
                break;
            case 8:
                month = "Septembre";
                break;
            case 9:
                month = "Octobre";
                break;
            case 10:
                month = "Novembre";
                break;
            default:
                month = "Décembre";
                break;
        }

        return month;

    }
}
