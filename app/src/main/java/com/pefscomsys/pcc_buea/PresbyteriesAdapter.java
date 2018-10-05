package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PresbyteriesAdapter extends RecyclerView.Adapter<PresbyteriesAdapter.PresbyteriesViewHolder>
{
    List<Presbytery> presbyteries;

    public PresbyteriesAdapter(List<Presbytery> presbyteryList)
    {
        this.presbyteries = presbyteryList;
    }

    @NonNull
    @Override
    public PresbyteriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View presView = LayoutInflater.from(parent.getContext()).inflate(R.layout.presbyteries_row, parent,false);
        PresbyteriesAdapter.PresbyteriesViewHolder presHolder = new PresbyteriesAdapter.PresbyteriesViewHolder(presView);

        return presHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PresbyteriesViewHolder holder, int position) {
        holder.name.setText(presbyteries.get(position).getName());
        holder.presbetery.setText(presbyteries.get(position).getPresbytery());
        holder.sec.setText(presbyteries.get(position).getSecretery());
        holder.secTel.setText(presbyteries.get(position).getSecTel());
        holder.treas.setText(presbyteries.get(position).getTreasurer());
        holder.treasTel.setText(presbyteries.get(position).getTreasurerTel());
        holder.chairTel.setText(presbyteries.get(position).getChairTel());
        holder.chair.setText(presbyteries.get(position).getChair());
        holder.cong.setText(presbyteries.get(position).getCong());
    }

    @Override
    public int getItemCount() {
        return this.presbyteries.size();
    }

    public class PresbyteriesViewHolder extends RecyclerView.ViewHolder {

        public TextView name, presbetery, sec, secTel, chair, chairTel, treas, treasTel, cong;
        CardView presCard;

        public PresbyteriesViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.pres_name);
            this.presbetery = itemView.findViewById(R.id.pres_pres);
            this.sec = itemView.findViewById(R.id.pres_sec);
            this.secTel = itemView.findViewById(R.id.pres_sec_tel);
            this.chair = itemView.findViewById(R.id.pres_chair);
            this.chairTel = itemView.findViewById(R.id.pres_chair_tel);
            this.treas = itemView.findViewById(R.id.pres_treas);
            this.treasTel = itemView.findViewById(R.id.pres_treas_tel);
            this.cong = itemView.findViewById(R.id.pres_cong);

            this.presCard = itemView.findViewById(R.id.pres_card);
        }
    }
}
