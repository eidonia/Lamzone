package com.bast.lamzone.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bast.lamzone.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date extends DialogFragment {

    private DatePicker datePicker;
    private OnDateChooser onDateChooser;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.date_frag, null);
        builder.setView(view);

        datePicker = view.findViewById(R.id.datePicker);

        TextView txtOK = view.findViewById(R.id.txtOK);
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dateDay = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                Calendar cal = new GregorianCalendar(year, month, dateDay);
                int day = cal.get(Calendar.DAY_OF_WEEK);
                onDateChooser.setOnDateChooser(day, dateDay, month, year);
                dismiss();
            }
        });


        return builder.create();
    }

    public void setOnDateChooser(OnDateChooser onDateChooser) {
        this.onDateChooser = onDateChooser;
    }

    interface OnDateChooser {
        void setOnDateChooser(int day, int dateDay, int month, int year);
    }
}
