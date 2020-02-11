package com.bast.lamzone.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bast.lamzone.R;
import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class ReunionAdaptater extends RecyclerView.Adapter<ReunionAdaptater.ViewHolder> {

    private List<Reunion> mReunion;
    private Context context;
    private int numList;
    MainActivity mainActivity;
    private List<Reunion> mReu;
    private ApiServiceReu apiService;
    private String listParti;

    public ReunionAdaptater(List<Reunion> mReunion, int numList, Context context, MainActivity mainActivity) {
        this.mReunion = mReunion;
        this.context = context;
        this.numList = numList;
        this.mainActivity = mainActivity;
        apiService = Di.getApiServiceReu();
        mReu = apiService.getReunion();
    }

    @NonNull
    @Override
    public ReunionAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionAdaptater.ViewHolder holder, final int position) {
        final Reunion reu = mReunion.get(position);
        if (reu.getSalle() % 2 == 0){
            holder.img.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorList1));
        }else{
            holder.img.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorList2));
        }

        String minuteDec = new DecimalFormat("00").format(reu.getMinute());

        holder.txtReu.setText(context.getResources().getString(R.string.reunion, reu.getSalle(), reu.getHeure(), minuteDec, reu.getHost()));

        for (int i = 0; i < reu.getParticipants().size(); i++) {
            if (listParti == null) {
                listParti = reu.getParticipants().get(i);
            } else {
                listParti = listParti + ", " + reu.getParticipants().get(i);
            }
        }
        holder.txtParti.setText(listParti);

        holder.btnDelete.setOnClickListener(v -> mainActivity.DltReu(reu, numList));

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
                Context ctx = v.getContext();
                Intent intent = new Intent(ctx, ReunionPage.class);
                intent.putIntegerArrayListExtra("POSREU", posAndNumList);
                ctx.startActivity(intent);

            });
        }
    }
}
