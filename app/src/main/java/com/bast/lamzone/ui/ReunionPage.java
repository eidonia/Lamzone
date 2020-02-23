package com.bast.lamzone.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.bast.lamzone.R;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bast.lamzone.utils.Constante.DEC_FOR;
import static com.bast.lamzone.utils.Constante.INT_VAL;

public class ReunionPage extends AppCompatActivity {

    ApiServiceReu apiService;
    List<Reunion> lReu;
    Reunion reunion;
    int itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion_page);

        ReunionFragment fragment = ReunionFragment.newInstance(getIntent().getIntegerArrayListExtra(INT_VAL));

        ArrayList<Integer> mPosandNumList;
        mPosandNumList = getIntent().getIntegerArrayListExtra(INT_VAL);
        itemPos = mPosandNumList.get(0);
        int numList = mPosandNumList.get(1);
        apiService = Di.getApiServiceReu();
        if (numList == 0) {
            lReu = apiService.getReunion();
        } else if (numList == 1) {
            lReu = apiService.getReunionFiltered();
        }
        reunion = lReu.get(itemPos);
        final String minuteDec = new DecimalFormat(DEC_FOR).format(reunion.getMinute());

        getSupportActionBar().setTitle(getResources().getString(R.string.reunionPage, reunion.getSalle(), reunion.getHeure(), minuteDec, reunion.getDay(), reunion.getDateDay(), reunion.getHost()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.listFragmentReu, fragment)
                .commit()
        ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.reunion_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ReunionPage.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.darkmode:
                switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                menu.findItem(R.id.darkmode).setIcon(R.drawable.ic_brightness_7_black_24dp);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                menu.findItem(R.id.darkmode).setIcon(R.drawable.ic_brightness_3_black_24dp);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
