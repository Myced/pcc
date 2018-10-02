package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ChurchAddressAdapter extends RecyclerView.Adapter<ChurchAddressAdapter.ChurchAddressViewHolder>
{
    List<ChurchAddress> addresses;

    public ChurchAddressAdapter(List<ChurchAddress> addresses)
    {
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public ChurchAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View addressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.church_address_row, parent,false);
        ChurchAddressViewHolder addressViewHolder = new ChurchAddressViewHolder(addressView);

        return addressViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChurchAddressViewHolder holder, int position) {
        holder.mName.setText(addresses.get(position).getName());
        holder.mChair.setText(addresses.get(position).getChair());
        holder.mCo.setText(addresses.get(position).getCo());
        holder.mPobox.setText(addresses.get(position).getPobox());
        holder.mAddress.setText(addresses.get(position).getAddress());
        holder.mTel.setText(addresses.get(position).getTel());
        holder.mEmail.setText(addresses.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return this.addresses.size();
    }

    public static class ChurchAddressViewHolder extends RecyclerView.ViewHolder
    {
        TextView mName, mChair, mCo, mPobox, mAddress, mTel, mEmail;
        CardView addressCard;

        public ChurchAddressViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.address_name);
            mChair = itemView.findViewById(R.id.address_chair);
            mCo = itemView.findViewById(R.id.address_co);
            mPobox = itemView.findViewById(R.id.address_pobox);
            mAddress = itemView.findViewById(R.id.address_address);
            mTel = itemView.findViewById(R.id.address_tel);
            mEmail = itemView.findViewById(R.id.address_email);

            addressCard = itemView.findViewById(R.id.address_card);
        }
    }
}
