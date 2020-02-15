package com.bast.lamzone.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ReunionAdaptater.ReuPageLoader {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.listFragment, new MainFragment())
                .addToBackStack(null)
                .commit()
        ;

    }

    @Override
    public void load(ArrayList<Integer> numAndPosList) {
        if (findViewById(R.id.frameReu) != null) {
            ReunionFragment fragment = ReunionFragment.newInstance(numAndPosList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameReu, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, ReunionPage.class);
            intent.putIntegerArrayListExtra("POSREU", numAndPosList);
            startActivity(intent);
        }
    }

    public void checkIfDel(int pos) {
        if (findViewById(R.id.frameReu) != null) {
            ReunionFragment fragment = new ReunionFragment();
            if (fragment.itemPos == pos) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(getSupportFragmentManager().findFragmentById(R.id.frameReu))
                        .commit();
            }
        }
    }
}
