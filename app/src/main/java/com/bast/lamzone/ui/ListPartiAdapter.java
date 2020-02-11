package com.bast.lamzone.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bast.lamzone.R;

import java.util.List;

class ListPartiAdapter extends RecyclerView.Adapter<ListPartiAdapter.ViewHolder> {

    List<String> mParticipants;


    public ListPartiAdapter(List<String> mParticipants) {
        this.mParticipants = mParticipants;
    }

    @Override
    public ListPartiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items_parti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textMailParti.setText(mParticipants.get(position));

    }


    @Override
    public int getItemCount() {
        return mParticipants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textMailParti;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMailParti = itemView.findViewById(R.id.textMailParti);
        }
    }
}
