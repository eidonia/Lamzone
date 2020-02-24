package com.bast.lamzone.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.ActivityMainBinding;
import com.bast.lamzone.di.Di;

import java.util.ArrayList;

import static com.bast.lamzone.utils.Constante.INT_VAL;

public class MainActivity extends AppCompatActivity implements ReunionAdapter.ReuPageLoader {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Di.resetApiService();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.listFragment, new MainFragment())
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
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(this, ReunionPage.class);
            intent.putIntegerArrayListExtra(INT_VAL, numAndPosList);
            startActivity(intent);
        }
    }

    public void checkIfDel(int reuID) {
        if (findViewById(R.id.frameReu) != null) {
            ReunionFragment fragment = (ReunionFragment) getSupportFragmentManager().findFragmentById(R.id.frameReu);
            if (fragment != null && fragment.reunion.getReuID() == reuID) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();

            }
        }
    }
}

