package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ChurchAddressAdapter extends RecyclerView.Adapter<ChurchAddressAdapter.ChurchAddressViewHolder>
{
    private List<ChurchAddress> addresses;
    private Context context;

    public ChurchAddressAdapter(List<ChurchAddress> addressList, Context ctx)
    {
        addresses = addressList;
        context = ctx;
    }

    @NonNull
    @Override
    public ChurchAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.church_address_row, parent,false);

        Log.d("churchAdressAdapter", addresses.toString());
        return new ChurchAddressViewHolder(view);
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
        return addresses.size();
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
