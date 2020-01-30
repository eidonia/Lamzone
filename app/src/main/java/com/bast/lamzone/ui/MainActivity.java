package com.bast.lamzone.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.ActivityMainBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.models.Time;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ApiServiceReu apiService;
    List<Reunion> mReunion;
    List<Reunion> mFilteredReunion;
    int heure;
    int minutes;
    String day;
    int dateDay;
    String month;
    int year;
    private CompoundButton.OnCheckedChangeListener ChangedChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String salle = (String) buttonView.getText();
            String salle2 = salle.substring(6);
            int salleInt = Integer.parseInt(salle2);

            if (isChecked) {
                for (int i = 0; i < mReunion.size(); i++) {
                    if (salleInt == mReunion.get(i).getSalle()) {
                        mFilteredReunion.add(mReunion.get(i));
                    }
                }
                onUpdate();
            } else {
                for (int x = mFilteredReunion.size() - 1; x >= 0; x--) {
                    if (salleInt == mFilteredReunion.get(x).getSalle()) {
                        mFilteredReunion.remove(mFilteredReunion.get(x));
                    }
                }
                onUpdate();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.salle1.setOnCheckedChangeListener(ChangedChecked);
        binding.salle2.setOnCheckedChangeListener(ChangedChecked);
        binding.salle3.setOnCheckedChangeListener(ChangedChecked);
        binding.salle4.setOnCheckedChangeListener(ChangedChecked);
        binding.salle5.setOnCheckedChangeListener(ChangedChecked);
        binding.salle6.setOnCheckedChangeListener(ChangedChecked);
        binding.salle7.setOnCheckedChangeListener(ChangedChecked);
        binding.salle8.setOnCheckedChangeListener(ChangedChecked);
        binding.salle9.setOnCheckedChangeListener(ChangedChecked);
        binding.salle10.setOnCheckedChangeListener(ChangedChecked);

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
                if (binding.boxfilter.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    ViewGroup.LayoutParams params = binding.boxfilter.getLayoutParams();
                    params.height = 1;
                    binding.boxfilter.setLayoutParams(params);
                }
                onUpdate();
                showDialogFrag();
            }
        });
    }

    private void initList() {
        apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        mFilteredReunion = apiService.getReunionFiltered();

        if (binding.boxfilter.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            binding.rvList.setAdapter(new ReunionAdaptater(mFilteredReunion, this));
        } else {
            binding.rvList.setAdapter(new ReunionAdaptater(mReunion, this));
        }
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
            if (binding.boxfilter.getLayoutParams().height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                ViewGroup.LayoutParams params = binding.boxfilter.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                binding.boxfilter.setLayoutParams(params);
                onUpdate();
            } else {
                ViewGroup.LayoutParams params2 = binding.boxfilter.getLayoutParams();
                params2.height = 1;
                binding.boxfilter.setLayoutParams(params2);
                if (mFilteredReunion != null)
                    mFilteredReunion.clear();
                uncheckRoom();
                onUpdate();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialogFrag() {
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

    private void uncheckRoom() {
        binding.salle1.setChecked(false);
        binding.salle2.setChecked(false);
        binding.salle3.setChecked(false);
        binding.salle4.setChecked(false);
        binding.salle5.setChecked(false);
        binding.salle6.setChecked(false);
        binding.salle7.setChecked(false);
        binding.salle8.setChecked(false);
        binding.salle9.setChecked(false);
        binding.salle10.setChecked(false);
    }
}
