package com.bast.lamzone.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.FilterreuFragBinding;

public class FilterReuFrag extends DialogFragment {

    TextView txtHeure;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        Context ctx = getActivity();
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx, R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.filterreu_frag, null);
        builder.setView(view);

        txtHeure = view.findViewById(R.id.btnHeure);
        txtHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClockFragment();
            }
        });

        return builder.create();
    }

    public void showClockFragment(){
        FragmentManager fm = getFragmentManager();
        Clock clock = new Clock();
        clock.show(fm, "clock");

    }
}
