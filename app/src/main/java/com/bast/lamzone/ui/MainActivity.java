package com.bast.lamzone.ui;

import android.os.Bundle;

import com.bast.lamzone.R;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.databinding.ActivityMainBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Time;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Filterable {

    ActivityMainBinding binding;
    ApiServiceReu apiService;
    List<Reunion> mReunion;
    int heure;
    int minutes;
    String day;
    int dateDay;
    String month;
    int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initList();

        Time time = new Time();
        heure = time.getHeure();
        minutes = time.getMinutes();
        String heureString = new DecimalFormat("00").format(heure);
        String minutesString = new DecimalFormat("00").format(minutes);
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();

        binding.btnFilterDate.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));
        binding.btnFilterHour.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
        binding.btnFilterHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogClock();
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.boxfilter.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    ViewGroup.LayoutParams params = binding.boxfilter.getLayoutParams();
                    params.height = 1;
                    binding.boxfilter.setLayoutParams(params);
                }
                showDialogFrag();
            }
        });


    }

    private void initList() {
        apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        //mReunion.add(new Reunion(10, 14, 30, "Julie", "bastien.rambeaud@hotmail.com, bastien.rambeaud@hotmail.com, bastien.rambeaud@hotmail.com"));
        //mReunion.add(new Reunion(2, 15, 00, "Bastien", "bastien.rambeaud@hotmail.com, bastien.rambeaud@hotmail.com"));



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
            if(binding.boxfilter.getLayoutParams().height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                ViewGroup.LayoutParams params = binding.boxfilter.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                binding.boxfilter.setLayoutParams(params);
            }else{
                ViewGroup.LayoutParams params2 = binding.boxfilter.getLayoutParams();
                params2.height = 1;
                binding.boxfilter.setLayoutParams(params2);
            }
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

    public void showDialogClock() {
        FragmentManager fm = getSupportFragmentManager();
        Clock clock = new Clock();
        clock.setOnTimeChooser(new Clock.OnTimeChooser() {

            @Override
            public void setOnTimeChooser(int heure, int minutes) {
                String heureString = new DecimalFormat("00").format(heure);
                String minutesString = new DecimalFormat("00").format(minutes);
                binding.btnFilterHour.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
                MainActivity.this.heure = heure;
                MainActivity.this.minutes = minutes;
            }
        });
        clock.show(fm, "clock");
    }

}
