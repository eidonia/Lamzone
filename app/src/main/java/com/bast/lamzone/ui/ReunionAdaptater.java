package com.bast.lamzone.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bast.lamzone.R;
import com.bast.lamzone.models.Reunion;

import java.text.DecimalFormat;
import java.util.List;

class ReunionAdaptater extends RecyclerView.Adapter<ReunionAdaptater.ViewHolder> {

    List<Reunion> mReunion;
    Context context;

    public ReunionAdaptater(List<Reunion> mReunion, Context context) {
        this.mReunion = mReunion;
        this.context = context;
    }

    @NonNull
    @Override
    public ReunionAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionAdaptater.ViewHolder holder, int position) {
        Reunion reu = mReunion.get(position);
        if (reu.getSalle() % 2 == 0){
            holder.img.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorList1));
        }else{
            holder.img.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorList2));
        }

        String minuteDec = new DecimalFormat("00").format(reu.getMinute());

        holder.txtReu.setText(context.getResources().getString(R.string.reunion, reu.getSalle(), reu.getHeure(), minuteDec, reu.getHost()));
        holder.txtParti.setText(reu.getParticipants());

    }

    @Override
    public int getItemCount() {
        return mReunion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtReu;
        TextView txtParti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgList);
            txtReu = itemView.findViewById(R.id.txtList);
            txtParti = itemView.findViewById(R.id.txtListPart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPos = getLayoutPosition();
                    Context ctx = v.getContext();
                    Intent intent = new Intent(ctx, ReunionPage.class);
                    intent.putExtra("POSREU", itemPos);
                    ctx.startActivity(intent);

                }
            });
        }
    }
}
