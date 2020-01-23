package com.bast.lamzone.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bast.lamzone.R;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;

import java.util.ArrayList;
import java.util.List;

public class CreateReuFrag extends DialogFragment {

    TimePicker picker;
    Spinner spin;
    EditText editHost;
    EditText editParti;
    ApiServiceReu apiService;
    List<Reunion> mReunion;
    List<Integer> salle = new ArrayList<>();
    TextView textCreate;


    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Context context = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.createreu_frag, null);
        builder.setView(view);

        apiService = Di.getApiServiceReu();
        mReunion = apiService.getReunion();

        spin = view.findViewById(R.id.spinSalle);
        picker = view.findViewById(R.id.timePicker);
        editHost = view.findViewById(R.id.editHost);
        editParti = view.findViewById(R.id.editParti);
        textCreate = view.findViewById(R.id.textCreate);

        picker.setIs24HourView(true);

        final int heure;
        final int minutes;

        if(Build.VERSION.SDK_INT < 23){
            heure = picker.getCurrentHour();
            minutes = picker.getCurrentMinute();
        }else{
            heure = picker.getHour();
            minutes = picker.getMinute();
        }
        createSpinner(context, spin);

        if(mReunion.size() > 0){
            int position;
            for (position = 0; position < mReunion.size(); position++){
                if(mReunion.get(position).getHeure() == heure){
                    salle.remove(mReunion.get(position).getSalle()-1);
                }
            }
        }

        textCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReunion.add(new Reunion((Integer) spin.getSelectedItem(), heure, minutes, String.valueOf(editHost.getText()), String.valueOf(editParti.getText())));
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                dismiss();
                MainActivity act = (MainActivity) getActivity();
                act.onUpdate();
            }
        });


        return builder.create();

    }


    public void createSpinner(Context context, Spinner spin) {
        salle.add(1);
        salle.add(2);
        salle.add(3);
        salle.add(4);
        salle.add(5);
        salle.add(6);
        salle.add(7);
        salle.add(8);
        salle.add(9);
        salle.add(10);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, salle);
        adapter.setDropDownViewResource(android.R.layout.simple_gallery_item);
        spin.setAdapter(adapter);

    }

}
