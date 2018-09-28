package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SchoolViewAdapter extends RecyclerView.Adapter<SchoolViewAdapter.SchoolViewHolder>
{
    //constructor for the list of schools
    private List<School> schools;

    public SchoolViewAdapter(List<School> schools)
    {
        this.schools = schools;
    }
    @NonNull
    @Override
    public SchoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View schoolView = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_row, parent, false);
        SchoolViewHolder schoolHolder = new SchoolViewHolder(schoolView);

        return schoolHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolViewHolder holder, int position) {
        holder.mSchoolName.setText(schools.get(position).getName());
        holder.mPrincipal.setText(schools.get(position).getPrincipal());
        holder.mPobox.setText(schools.get(position).getPobox());
        holder.mAddress.setText(schools.get(position).getAddress());
        holder.mTel.setText(schools.get(position).getTel());
        holder.mEmail.setText(schools.get(position).getEmail());
    }

    @Override
    public int getItemCount() {

        return this.schools.size();
    }

    public static class SchoolViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mSchoolName, mPrincipal, mPobox, mAddress, mTel, mEmail;
        CardView schoolCard;


        public SchoolViewHolder(View itemView) {
            super(itemView);

            mSchoolName = itemView.findViewById(R.id.school_name);
            mPrincipal = itemView.findViewById(R.id.school_principal);
            mPobox = itemView.findViewById(R.id.school_pobox);
            mAddress = itemView.findViewById(R.id.school_address);
            mTel = itemView.findViewById(R.id.school_tel);
            mEmail = itemView.findViewById(R.id.school_email);

            schoolCard = itemView.findViewById(R.id.school_card);
        }
    }
}
