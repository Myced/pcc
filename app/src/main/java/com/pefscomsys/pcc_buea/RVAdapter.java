package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.InstViewHolder>
{
    List<HealthInstitution> institutions;

    public RVAdapter(List<HealthInstitution> institutions)
    {
        this.institutions = institutions;
    }

    @NonNull
    @Override
    public InstViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_health_row, parent, false);
        InstViewHolder ivh = new InstViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull InstViewHolder holder, int position) {

        holder.instName.setText(institutions.get(position).getName());
        holder.instDoctor.setText(institutions.get(position).getDoctor());
        holder.instPobox.setText(institutions.get(position).getPobox());
        holder.instAddress.setText(institutions.get(position).getAddress());
        holder.instTel.setText(institutions.get(position).getTel());

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return institutions.size();
    }

    public static class InstViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView instName;
        TextView instDoctor;
        TextView instAddress;
        TextView instPobox;
        TextView instTel;

        InstViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.health_card);
            instName = (TextView)itemView.findViewById(R.id.inst_name);
            instDoctor = (TextView)itemView.findViewById(R.id.inst_doctor);
            instAddress = (TextView) itemView.findViewById(R.id.inst_address);
            instPobox = (TextView) itemView.findViewById(R.id.inst_pobox);
            instTel = (TextView) itemView.findViewById(R.id.inst_tel);

        }
    }
}
