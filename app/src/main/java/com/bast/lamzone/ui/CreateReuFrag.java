package com.bast.lamzone.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bast.lamzone.R;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.models.Time;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateReuFrag extends BottomSheetDialogFragment {

    private Spinner spin;
    private EditText editHost, editParti;
    private List<Reunion> mReunion;
    private List<String> mParticipants = new ArrayList<>();
    private List<String> salle = new ArrayList<>();
    private TextView btnHeureD, btnHeureF, btnDay, textAddParti, textReuD, textReuF;
    private MaterialButton buttonCreate, btnAddParti;
    private int heure, minutes, year, dateDay, heureF, minutesF;
    private String day, month;
    private RecyclerView rvListParti;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        final Context context = getActivity();
        View view = inflater.inflate(R.layout.createreu_frag, null);

        ApiServiceReu apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        textReuD = view.findViewById(R.id.textHeureDebut);
        textReuD.setText(getResources().getString(R.string.textHeureDebut));
        textReuF = view.findViewById(R.id.textHeureFin);
        textReuF.setText(getResources().getString(R.string.textHeureFin));
        btnHeureD = view.findViewById(R.id.btnHeureCreaDebut);
        btnHeureF = view.findViewById(R.id.btnHeureCreaFin);
        spin = view.findViewById(R.id.spinSalle);
        editHost = view.findViewById(R.id.editHost);
        editParti = view.findViewById(R.id.editParti);
        buttonCreate = view.findViewById(R.id.textCreate);
        btnDay = view.findViewById(R.id.btnCreaDate);
        textAddParti = view.findViewById(R.id.textAddParti);
        final InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        Time time = new Time();
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();
        heure = time.getHeure();
        minutes = time.getMinutes();
        heureF = time.getHeure() + 1;
        minutesF = time.getMinutes();
        String heureString = new DecimalFormat("00").format(heure);
        String minutesString = new DecimalFormat("00").format(minutes);
        btnDay.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));
        btnHeureD.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
        btnHeureD.setOnClickListener(v -> showDialogClock(0));
        String heureStringF = new DecimalFormat("00").format(heureF);
        String minutesStringF = new DecimalFormat("00").format(minutesF);
        btnHeureF.setText(getResources().getString(R.string.txtHeure, heureStringF, minutesStringF));
        btnHeureF.setOnClickListener(v -> showDialogClock(1));
        btnDay.setOnClickListener(v -> showDialogDate());
        rvListParti = view.findViewById(R.id.rvListParti);

        rvListParti.setLayoutManager(new LinearLayoutManager(getContext()));


        createSpinner(context, spin);
        initList();

        btnAddParti = view.findViewById(R.id.btnAddParti);
        btnAddParti.setOnClickListener(v -> {
            if (!isEmailValid(editParti.getText().toString())) {
                textAddParti.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                textAddParti.setText(R.string.textAddParti);
                inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            } else {
                textAddParti.setTextColor(getResources().getColor(android.R.color.black));
                mParticipants.add(editParti.getText().toString());
                inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                textAddParti.setText(R.string.textPartiAdd);
                editParti.setText("");
                initList();
            }
        });


        buttonCreate.setOnClickListener(v -> {
            if (spin.getSelectedItem() == "Selectionner une salle") {
                Toast.makeText(context, "Veuillez sélectionner une salle", Toast.LENGTH_SHORT).show();
            } else {

                String salleReplace = (String) spin.getSelectedItem();
                int salle = Integer.parseInt(salleReplace.substring(6));
                mReunion.add(new Reunion(salle, heure, minutes, heureF, minutesF, day, dateDay, month, year, String.valueOf(editHost.getText()), mParticipants));
                Toast.makeText(context, "Réunion créée", Toast.LENGTH_SHORT).show();
                dismiss();
                MainFragment.getInstance().onUpdate();
            }
        });


        return view;

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
                for(int i = 0; i <mReunion.size(); i++) {
                    if (((mReunion.get(i).getHeure() <= heure && mReunion.get(i).getMinute() <= minutesF && mReunion.get(i).getHeureF() >= heure && mReunion.get(i).getMinuteF() >= minutes) || (mReunion.get(i).getHeure() <= heureF && mReunion.get(i).getHeureF() >= heureF)) && mReunion.get(i).getDateDay() == dateDay && mReunion.get(i).getMonth() == month && mReunion.get(i).getSalle() == position) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
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
                String heureString = new DecimalFormat("00").format(heure);
                String minutesString = new DecimalFormat("00").format(minutes);
                btnHeureD.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
                CreateReuFrag.this.heure = heure;
                CreateReuFrag.this.minutes = minutes;
            } else {
                String heureString = new DecimalFormat("00").format(heure);
                String minutesString = new DecimalFormat("00").format(minutes);
                btnHeureF.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
                CreateReuFrag.this.heureF = heure;
                CreateReuFrag.this.minutesF = minutes;
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
            btnDay.setText(getResources().getString(R.string.textCreaDate, dayString, dateDay, monthString, year));

        });
        date.show(fm, "date");
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void initList() {
        rvListParti.setAdapter(new ListPartiAdapter(mParticipants));
    }

}
