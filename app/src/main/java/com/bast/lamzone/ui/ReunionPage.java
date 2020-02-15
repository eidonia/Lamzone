package com.bast.lamzone.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bast.lamzone.R;

public class ReunionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion_page);

        ReunionFragment fragment = ReunionFragment.newInstance(getIntent().getIntegerArrayListExtra("POSREU"));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.listFragmentReu, fragment)
                .commit()
        ;
    }
}
