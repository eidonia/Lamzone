package com.bast.lamzone.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.CreatereuFragBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.models.Time;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bast.lamzone.utils.Constante.DEC_FOR;

public class CreateReuFrag extends BottomSheetDialogFragment {

    private List<Reunion> mReunion;
    private List<String> mParticipants = new ArrayList<>();
    private List<String> salle = new ArrayList<>();
    private int heure, minutes, year, dateDay, heureF, minutesF;
    private String day, month;
    BottomSheetBehavior bottomSheetBehavior;
    CreatereuFragBinding binding;
    Time time = new Time();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        binding = CreatereuFragBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final Context context = getActivity();
        ApiServiceReu apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        createTime();
        binding.textHeureDebut.setText(getResources().getString(R.string.textHeureDebut));
        binding.textHeureFin.setText(getResources().getString(R.string.textHeureFin));
        String heureString = new DecimalFormat(DEC_FOR).format(heure);
        String minutesString = new DecimalFormat(DEC_FOR).format(minutes);
        binding.btnCreaDate.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));
        binding.btnHeureCreaDebut.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
        binding.btnHeureCreaDebut.setOnClickListener(v -> showDialogClock(0));
        String heureStringF = new DecimalFormat(DEC_FOR).format(heureF);
        String minutesStringF = new DecimalFormat(DEC_FOR).format(minutesF);
        binding.btnHeureCreaFin.setText(getResources().getString(R.string.txtHeure, heureStringF, minutesStringF));
        binding.btnHeureCreaFin.setOnClickListener(v -> showDialogClock(1));
        binding.btnCreaDate.setOnClickListener(v -> showDialogDate());
        binding.rvListParti.setLayoutManager(new LinearLayoutManager(getContext()));

        createSpinner(context, binding.spinSalle);
        initList();

        binding.btnAddParti.setOnClickListener(v -> addParti());

        binding.btnCreate.setOnClickListener(v -> {
            if (heureF < heure) {
                Toast.makeText(context, R.string.ToastHeure, Toast.LENGTH_SHORT).show();
            } else if (binding.spinSalle.getSelectedItem() == "Selectionner une salle" || binding.editHost.getText().toString().equals("") || mParticipants.size() == 0) {
                Toast.makeText(context, R.string.ToastChampVide, Toast.LENGTH_SHORT).show();
            } else {
                String salleReplace = (String) binding.spinSalle.getSelectedItem();
                int salle = Integer.parseInt(salleReplace.substring(6));
                mReunion.add(new Reunion(salle, heure, minutes, heureF, minutesF, day, dateDay, month, year, String.valueOf(binding.editHost.getText()), mParticipants));
                Toast.makeText(context, R.string.ToastCreation, Toast.LENGTH_SHORT).show();
                dismiss();
                MainFragment.getInstance().onUpdate();
            }
        });

        binding.editParti.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addParti();
                handled = true;
            }
            return handled;
        });
    }


    private void createSpinner(final Context context, final Spinner spin) {
        salle.add("Selectionner une salle");
        salle.add("Salle 1");
        salle.add("Salle 2");
        salle.add("Salle 3");
        salle.add("Salle 4");
        salle.add("Salle 5");
        salle.add("Salle 6");
        salle.add("Salle 7");
        salle.add("Salle 8");
        salle.add("Salle 9");
        salle.add("Salle 10");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, salle) {
            @Override
            public boolean isEnabled(int position) {
                for (int i = 0; i < mReunion.size(); i++) {
                    if (((mReunion.get(i).getHeure() <= heure && mReunion.get(i).getMinute() <= minutesF && mReunion.get(i).getHeureF() >= heure && mReunion.get(i).getMinuteF() >= minutes) || (mReunion.get(i).getHeure() <= heureF && mReunion.get(i).getHeureF() >= heureF)) && mReunion.get(i).getDateDay() == dateDay && mReunion.get(i).getMonth() == month && mReunion.get(i).getSalle() == position) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mText = (TextView) mView;
                boolean disabled = !isEnabled(position);
                if (disabled) {
                    mText.setTextColor(Color.GRAY);
                } else {
                    mText.setTextColor(Color.BLACK);
                }
                return mView;
            }

        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

    }

    private void showDialogClock(int i) {
        FragmentManager fm = getFragmentManager();
        Clock clock = new Clock();
        clock.setOnTimeChooser((heure, minutes) -> {
            if (i == 0) {
                int heureNow = time.getHeure();
                int minuteNow = time.getMinutes();
                String heureString = new DecimalFormat(DEC_FOR).format(heure);
                String minutesString = new DecimalFormat(DEC_FOR).format(minutes);
                if (heure == heureNow && minuteNow > minutes || heureNow > heure) {
                    Toast.makeText(getContext(), R.string.ToastHeureDebut, Toast.LENGTH_SHORT).show();
                    showDialogClock(0);
                } else {
                    binding.btnHeureCreaDebut.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
                    CreateReuFrag.this.heure = heure;
                    CreateReuFrag.this.minutes = minutes;
                }
            } else {
                String heureString = new DecimalFormat(DEC_FOR).format(heure);
                String minutesString = new DecimalFormat(DEC_FOR).format(minutes);
                if (heure < CreateReuFrag.this.heure || (heure == CreateReuFrag.this.heure && minutes < CreateReuFrag.this.minutes)) {
                    Toast.makeText(getContext(), R.string.ToastHeure, Toast.LENGTH_SHORT).show();
                    showDialogClock(1);
                } else {
                    binding.btnHeureCreaFin.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
                    CreateReuFrag.this.heureF = heure;
                    CreateReuFrag.this.minutesF = minutes;
                }
            }
        });
        clock.show(fm, "clock");
    }

    private void showDialogDate() {
        FragmentManager fm = getFragmentManager();
        Date date = new Date();
        date.setOnDateChooser((day, dateDay, month, year) -> {
            Time time = new Time();
            String dayString = time.getDayInt(day);
            CreateReuFrag.this.day = dayString;
            CreateReuFrag.this.dateDay = dateDay;
            String monthString = time.getMonthInt(month);
            CreateReuFrag.this.month = monthString;
            CreateReuFrag.this.year = year;
            binding.btnCreaDate.setText(getResources().getString(R.string.textCreaDate, dayString, dateDay, monthString, year));

        });
        date.show(fm, "date");
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void initList() {
        binding.rvListParti.setAdapter(new ListPartiAdapter(mParticipants));
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior = BottomSheetBehavior.from((View) getView().getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void createTime() {
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();
        heure = time.getHeure();
        minutes = time.getMinutes();
        heureF = time.getHeure() + 1;
        minutesF = time.getMinutes();
    }

    public void addParti() {
        final InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!isEmailValid(binding.editParti.getText().toString())) {
            binding.textAddParti.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            binding.textAddParti.setText(R.string.textAddParti);
            inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        } else {
            binding.textAddParti.setTextColor(getResources().getColor(R.color.textFragColor));
            mParticipants.add(binding.editParti.getText().toString());
            inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            binding.textAddParti.setText(R.string.textPartiAdd);
            binding.editParti.setText("");
            initList();
        }
    }
}
