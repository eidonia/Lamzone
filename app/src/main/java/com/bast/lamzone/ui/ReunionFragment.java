package com.bast.lamzone.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bast.lamzone.R;
import com.bast.lamzone.databinding.FragmentReunionBinding;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReunionFragment extends Fragment {

    ApiServiceReu apiService;
    List<Reunion> lReu;
    Reunion reunion;
    int itemPos;
    private FragmentReunionBinding binding;
    ConstraintLayout layoutBack;

    public ReunionFragment() {
    }

    public static ReunionFragment newInstance(ArrayList<Integer> posAndNumList) {
        ReunionFragment fragment = new ReunionFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("POSREU", posAndNumList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReunionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ArrayList<Integer> mPosandNumList;
        mPosandNumList = getArguments().getIntegerArrayList("POSREU");
        itemPos = mPosandNumList.get(0);
        int numList = mPosandNumList.get(1);
        apiService = Di.getApiServiceReu();
        if (numList == 0) {
            lReu = apiService.getReunion();
        } else if (numList == 1) {
            lReu = apiService.getReunionFiltered();
        }
        reunion = lReu.get(itemPos);
        final String minuteDec = new DecimalFormat("00").format(reunion.getMinute());

        layoutBack = view.findViewById(R.id.reuLayoutBack);

        if (reunion.getSalle() % 2 == 0) {
            layoutBack.setBackgroundColor(getResources().getColor(R.color.colorList1));
        } else {
            layoutBack.setBackgroundColor(getResources().getColor(R.color.colorList2));
        }

        binding.gridNom.setText(getResources().getString(R.string.salleNumber, reunion.getSalle()));
        binding.textHour.setText(getResources().getString(R.string.heureReunion, reunion.getHeure(), minuteDec, reunion.getDay(), reunion.getDateDay(), reunion.getMonth()));
        binding.textHost.setText(reunion.getHost());
        binding.textTitreAbout.setText(R.string.textReunion);
        binding.textAbout.setText(R.string.lorem_ipsum);
        binding.rvListParticipants.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListParticipants.setAdapter(new ListPartiAdapter(reunion.getParticipants()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
