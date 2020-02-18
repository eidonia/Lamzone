package com.bast.lamzone.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bast.lamzone.R;

import java.util.ArrayList;

public class Clock extends DialogFragment {

    TimePicker timePicker;
    TextView txtOK, txtCancel;
    OnTimeChooser onTimeChooser;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.clock_frag, null);
        builder.setView(view);

        timePicker = view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);



        txtOK = view.findViewById(R.id.txtOK);
        txtOK.setOnClickListener(v -> {

            int heure;
            int minutes;

            if (Build.VERSION.SDK_INT < 23) {
                heure = timePicker.getCurrentHour();
                minutes = timePicker.getCurrentMinute();
            } else {
                heure = timePicker.getHour();
                minutes = timePicker.getMinute();
            }

            ArrayList<Integer> timeClock = new ArrayList<>();
            timeClock.add(heure);
            timeClock.add(minutes);

            onTimeChooser.setOnTimeChooser(heure, minutes);
            dismiss();
        });

        txtCancel = view.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(v -> dismiss());





        return builder.create();
    }

    public void setOnTimeChooser(OnTimeChooser onTimeChooser) {
        this.onTimeChooser = onTimeChooser;
    }

    interface OnTimeChooser{
         void setOnTimeChooser(int heure, int minutes);
    }
}
