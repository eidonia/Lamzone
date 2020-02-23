package com.bast.lamzone.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bast.lamzone.R;
import com.bast.lamzone.models.Reunion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bast.lamzone.utils.Constante.DEC_FOR;

class ReunionAdapter extends RecyclerView.Adapter<ReunionAdapter.ViewHolder> {

    private List<Reunion> mReunion;
    private Context context;
    private int numList;
    MainFragment mainFragment;
    MainActivity mainActivity;
    private String listParti;
    private ReuPageLoader reuPageLoader;

    public ReunionAdapter(List<Reunion> mReunion, int numList, Context context, MainFragment mainFragment, MainActivity mainActivity, ReuPageLoader reuPageLoader) {
        this.mReunion = mReunion;
        this.context = context;
        this.numList = numList;
        this.mainActivity = mainActivity;
        this.mainFragment = mainFragment;
        this.reuPageLoader = reuPageLoader;
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionAdapter.ViewHolder holder, final int position) {
        final Reunion reu = mReunion.get(position);
        if (reu.getSalle() % 2 == 0){
            holder.img.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorList1));
        }else{
            holder.img.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorList2));
        }

        String minuteDec = new DecimalFormat(DEC_FOR).format(reu.getMinute());

        holder.txtReu.setText(context.getResources().getString(R.string.reunion, reu.getSalle(), reu.getHeure(), minuteDec, reu.getHost()));

        for (int i = 0; i < reu.getParticipants().size(); i++) {
            if (listParti == null) {
                listParti = reu.getParticipants().get(i);
            } else {
                listParti = listParti + ", " + reu.getParticipants().get(i);
            }
        }
        holder.txtParti.setText(listParti);

        holder.btnDelete.setOnClickListener(v -> {
            Log.e("adapter", "suppression");
            mainFragment.DltReu(reu, numList);
            mainActivity.checkIfDel(reu.getReuID());
        });

    }

    @NonNull
    @Override
    public ReunionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);

        return new ViewHolder(view);
    }

    public interface ReuPageLoader {
        void load(ArrayList<Integer> numAndPosList);
    }

    @Override
    public int getItemCount() {
        return mReunion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtReu;
        TextView txtParti;
        ImageButton btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgList);
            txtReu = itemView.findViewById(R.id.txtList);
            txtParti = itemView.findViewById(R.id.txtListPart);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(v -> {
                int itemPos = getLayoutPosition();
                ArrayList<Integer> posAndNumList = new ArrayList<>();
                posAndNumList.add(itemPos);
                posAndNumList.add(numList);
                reuPageLoader.load(posAndNumList);

            });
        }
    }
}
