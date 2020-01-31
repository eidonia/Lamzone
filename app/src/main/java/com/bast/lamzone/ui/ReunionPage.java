package com.bast.lamzone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.ActivityReunionPageBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.google.android.material.appbar.AppBarLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReunionPage extends AppCompatActivity {

    private ActivityReunionPageBinding binding;

    ApiServiceReu apiService;
    List<Reunion> lReu;
    Reunion reunion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReunionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ArrayList<Integer> mPosandNumList = intent.getIntegerArrayListExtra("POSREU");
        int itemPos = mPosandNumList.get(0);
        int numList = mPosandNumList.get(1);

        apiService = Di.getApiServiceReu();
        if (numList == 0) {
            lReu = apiService.getReunion();
        } else if (numList == 1) {
            lReu = apiService.getReunionFiltered();
        }
        reunion = lReu.get(itemPos);

        String part = reunion.getParticipants().replace(", ", "\n- ");
        String startChar = "- ";
        String partFinal = startChar + part;

        final String minuteDec = new DecimalFormat("00").format(reunion.getMinute());

        setSupportActionBar(binding.toolBar);
        binding.toolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReunionPage.this.finish();
            }
        });

        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0){
                    binding.collapseTool.setTitle(getResources().getString(R.string.reunionPage, reunion.getSalle(), reunion.getHeure(), minuteDec, reunion.getDateDay(), reunion.getMonth(), reunion.getHost()));
                    isShow = true;
                }else if (isShow){
                    binding.collapseTool.setTitle(" ");
                    isShow = false;
                }

            }
        });

        if (reunion.getSalle() % 2 == 0){
            binding.collapseTool.setContentScrimColor(getResources().getColor(R.color.colorList1));
            binding.appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorList1));
        }else{
            binding.collapseTool.setContentScrimColor(getResources().getColor(R.color.colorList2));
            binding.appBarLayout.setBackgroundColor(getResources().getColor(R.color.colorList2));
        }

        binding.gridNom.setText(getResources().getString(R.string.salleNumber, reunion.getSalle()));
        binding.textHour.setText(getResources().getString(R.string.heureReunion, reunion.getHeure(), minuteDec, reunion.getDay(), reunion.getDateDay(), reunion.getMonth()));
        binding.textHost.setText(reunion.getHost());

        binding.textParticipants.setText(partFinal);
        binding.textTitreAbout.setText(R.string.textReunion);
        binding.textAbout.setText(R.string.lorem_ipsum);





    }
}
