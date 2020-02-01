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

import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ApiServiceReu apiService;
    String day;
    int dateDay;
    String month;
    int year;
    private ActivityMainBinding binding;
    private List<Reunion> mReunion;
    private List<Reunion> mFilteredReunion;
    private CompoundButton.OnCheckedChangeListener ChangedChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String salle = (String) buttonView.getText();
            String salle2 = salle.substring(6);
            int salleInt = Integer.parseInt(salle2);

            if (isChecked) {
                for (int i = 0; i < mReunion.size(); i++) {
                    if (mReunion.get(i).getSalle() == salleInt) {
                        mFilteredReunion.add(mReunion.get(i));
                    }
                }
                onUpdate();
            } else {
                for (int i = mFilteredReunion.size() - 1; i >= 0; i--) {
                    if (mFilteredReunion.get(i).getSalle() == salleInt) {
                        mFilteredReunion.remove(mFilteredReunion.get(i));
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

        Time time = new Time();
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();

        binding.radioRoom.setOnCheckedChangeListener(this);
        binding.radioDate.setOnCheckedChangeListener(this);

        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initList();
        binding.radioRoom.setChecked(true);
        binding.btnFilterDate.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));
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
            binding.rvList.setAdapter(new ReunionAdaptater(mFilteredReunion, 1, this));
        } else {
            binding.rvList.setAdapter(new ReunionAdaptater(mReunion, 0, this));
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
                RaZFilter();
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

    private void showDialogDate() {
        FragmentManager fm = getSupportFragmentManager();
        Date date = new Date();
        date.setOnDateChooser(new Date.OnDateChooser() {

            @Override
            public void setOnDateChooser(int day, int dateDay, int month, int year) {
                Time time = new Time();
                String dayString = time.getDayInt(day);
                MainActivity.this.day = dayString;
                MainActivity.this.dateDay = dateDay;
                String monthString = time.getMonthInt(month);
                MainActivity.this.month = monthString;
                MainActivity.this.year = year;
                binding.btnFilterDate.setText(getResources().getString(R.string.textCreaDate, dayString, dateDay, monthString, year));
            }
        });
        date.show(fm, "date");
    }

    private void RaZFilter() {
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

        Time time = new Time();
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();
        binding.btnFilterDate.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));
    }

    private void FilterDate(String month) {
        for (int i = 0; i < mReunion.size(); i++) {
            String monthUp = mReunion.get(i).getMonth().substring(0, 1).toUpperCase() + mReunion.get(i).getMonth().substring(1);
            if (mReunion.get(i).getDateDay() == dateDay && monthUp.equals(month)) {
                mFilteredReunion.add(mReunion.get(i));
            }
        }
        onUpdate();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.radioRoom) {
                mFilteredReunion.clear();
                ClickableRoom(0);
                binding.radioDate.setChecked(false);
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
            }
            if (buttonView.getId() == R.id.radioDate) {
                mFilteredReunion.clear();
                ClickableRoom(1);
                binding.radioRoom.setChecked(false);

                binding.btnFilterDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogDate();
                    }
                });

                FilterDate(month);
                onUpdate();
            }
        }
    }

    public void ClickableRoom(int i) {
        if (i == 0) {
            binding.salle1.setClickable(true);
            binding.salle2.setClickable(true);
            binding.salle3.setClickable(true);
            binding.salle4.setClickable(true);
            binding.salle5.setClickable(true);
            binding.salle6.setClickable(true);
            binding.salle7.setClickable(true);
            binding.salle8.setClickable(true);
            binding.salle9.setClickable(true);
            binding.salle10.setClickable(true);

        } else {
            binding.salle1.setClickable(false);
            binding.salle2.setClickable(false);
            binding.salle3.setClickable(false);
            binding.salle4.setClickable(false);
            binding.salle5.setClickable(false);
            binding.salle6.setClickable(false);
            binding.salle7.setClickable(false);
            binding.salle8.setClickable(false);
            binding.salle9.setClickable(false);
            binding.salle10.setClickable(false);
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
}
