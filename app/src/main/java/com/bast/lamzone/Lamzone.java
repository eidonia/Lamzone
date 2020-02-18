package com.bast.lamzone;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class Lamzone extends Application {

    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
