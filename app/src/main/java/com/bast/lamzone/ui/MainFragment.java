package com.bast.lamzone.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.FragmentMainBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.models.Time;

import java.util.List;

public class MainFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private static MainFragment instance;
    ApiServiceReu apiService;
    String day, month;
    int dateDay, year, checkMenu = 0;
    private FragmentMainBinding binding;
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

    public MainFragment() {
    }

    public static MainFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.slider.setToHide(binding.boxfilter);
        binding.slider.toggle();
        instance = this;
        Time time = new Time();
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();

        binding.radioRoom.setOnCheckedChangeListener(this);
        binding.radioDate.setOnCheckedChangeListener(this);


        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initList();

        binding.radioRoom.setChecked(true);
        binding.btnFilterDate.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));

        binding.fab.setOnClickListener(itemView -> {
            if (binding.slider.toggle()) {
                binding.slider.toggle();
            }
            showDialogFrag();
        });
    }

    private void initList() {
        apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        mFilteredReunion = apiService.getReunionFiltered();

        if (checkMenu == 1) {
            binding.rvList.setAdapter(new ReunionAdapter(mFilteredReunion, 1, getContext(), this, (MainActivity) getActivity(), (ReunionAdapter.ReuPageLoader) getActivity()));
        } else {
            binding.rvList.setAdapter(new ReunionAdapter(mReunion, 0, getContext(), this, (MainActivity) getActivity(), (ReunionAdapter.ReuPageLoader) getActivity()));
        }
    }

    public void onUpdate() {
        initList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            if (binding.slider.toggle()) {
                this.checkMenu = 1;
                if (mFilteredReunion != null)
                    mFilteredReunion.clear();
                RaZFilter();
                onUpdate();
            } else {
                this.checkMenu = 0;
                onUpdate();
            }
        } else if (id == R.id.darkmode) {
            switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                menu.findItem(R.id.darkmode).setIcon(R.drawable.ic_brightness_7_black_24dp);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                menu.findItem(R.id.darkmode).setIcon(R.drawable.ic_brightness_3_black_24dp);
                break;
        }
    }

    public void showDialogFrag() {
        FragmentManager fm = getFragmentManager();
        CreateReuFrag createReuFrag = new CreateReuFrag();
        createReuFrag.show(fm, "create");
    }

    private void showDialogDate() {
        FragmentManager fm = getFragmentManager();
        Date date = new Date();
        date.setOnDateChooser((day, dateDay, month, year) -> {
            Time time = new Time();
            String dayString = time.getDayInt(day);
            MainFragment.this.day = dayString;
            MainFragment.this.dateDay = dateDay;
            String monthString = time.getMonthInt(month);
            MainFragment.this.month = monthString;
            MainFragment.this.year = year;
            binding.btnFilterDate.setText(getResources().getString(R.string.textCreaDate, dayString, dateDay, monthString, year));
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

                binding.btnFilterDate.setOnClickListener(v -> showDialogDate());

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

    public void DltReu(Reunion reunion, int i) {
        Log.e("dltReu", "suppression " + i);
        if (i == 0) {
            apiService.deleteReunion(reunion);
        } else {
            apiService.deleteReunion(reunion);
            apiService.deleteReunionFilt(reunion);
        }
        onUpdate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
