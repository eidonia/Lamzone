package com.bast.lamzone.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.bast.lamzone.R;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.models.Time;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateReuFrag extends BottomSheetDialogFragment {

    Spinner spin;
    EditText editHost;
    EditText editParti;
    ApiServiceReu apiService;
    List<Reunion> mReunion;
    List<String> salle = new ArrayList<>();
    TextView textCreate, btnHeure, btnDay;
    int heure;
    int minutes;
    String day;
    int dateDay;
    String month;
    int year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        final Context context = getActivity();
        View view = inflater.inflate(R.layout.createreu_frag, null);

        apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();
        btnHeure = view.findViewById(R.id.btnHeureCrea);
        spin = view.findViewById(R.id.spinSalle);
        editHost = view.findViewById(R.id.editHost);
        editParti = view.findViewById(R.id.editParti);
        textCreate = view.findViewById(R.id.textCreate);
        btnDay = view.findViewById(R.id.btnCreaDate);

        Time time = new Time();
        day = time.getDay();
        dateDay = time.getDateDay();
        month = time.getMonth();
        year = time.getYear();
        heure = time.getHeure();
        minutes = time.getMinutes();
        String heureString = new DecimalFormat("00").format(heure);
        String minutesString = new DecimalFormat("00").format(minutes);
        btnDay.setText(getResources().getString(R.string.textCreaDate, day, dateDay, month, year));
        btnHeure.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
        btnHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogClock();
            }
        });

        createSpinner(context, spin);

        textCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin.getSelectedItem() == "Selectionner une salle"){
                    Toast.makeText(context, "Veuillez sélectionner une salle", Toast.LENGTH_SHORT).show();
                }else {

                    String salleReplace = (String) spin.getSelectedItem();
                    int salle = Integer.parseInt(salleReplace.substring(6));
                    mReunion.add(new Reunion(salle, heure, minutes, String.valueOf(editHost.getText()), String.valueOf(editParti.getText())));
                    Toast.makeText(context, "Réunion créée", Toast.LENGTH_SHORT).show();
                    dismiss();
                    MainActivity act = (MainActivity) getActivity();
                    act.onUpdate();
                }
            }
        });


        return view;

    }


    public void createSpinner(final Context context, final Spinner spin) {
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
                    if (mReunion.get(i).getHeure() == heure && mReunion.get(i).getSalle() == position) {
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

    public void showDialogClock() {
        FragmentManager fm = getFragmentManager();
        Clock clock = new Clock();
        clock.setOnTimeChooser(new Clock.OnTimeChooser() {

            @Override
            public void setOnTimeChooser(int heure, int minutes) {
                String heureString = new DecimalFormat("00").format(heure);
                String minutesString = new DecimalFormat("00").format(minutes);
                btnHeure.setText(getResources().getString(R.string.txtHeure, heureString, minutesString));
                CreateReuFrag.this.heure = heure;
                CreateReuFrag.this.minutes = minutes;
            }
        });
        clock.show(fm, "clock");
    }

}
