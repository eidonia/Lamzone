package com.bast.lamzone.ui;

import android.os.Bundle;

import com.bast.lamzone.R;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.databinding.ActivityMainBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Filterable {

    ActivityMainBinding binding;
    ApiServiceReu apiService;
    List<Reunion> mReunion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFrag();
            }
        });


    }

    private void initList() {
        apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        //mReunion.add(new Reunion(1, "14h", "Bastien", "bastien.rambeaud@hotmail.com"));
        //mReunion.add(new Reunion(2, "14h", "Bastien", "bastien.rambeaud@hotmail.com, bastien.rambeaud@hotmail.com"));

        binding.rvList.setAdapter(new ReunionAdaptater(mReunion, this));
    }


    public void onUpdate() {
        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            showDialogFrag();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Filter getFilter() {
        //TODO
        return null;
    }

    public void showDialogFrag(){
        FragmentManager fm = getSupportFragmentManager();
        CreateReuFrag createReuFrag = new CreateReuFrag();
        createReuFrag.show(fm, "create");
    }

}
