package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChurchHistoryAdapter extends RecyclerView.Adapter<ChurchHistoryAdapter.ChurchHistoryViewHolder>
{
    private List<ChurchHistory> histories;

    public ChurchHistoryAdapter(List<ChurchHistory> historyList)
    {
        this.histories = historyList;
    }

    @NonNull
    @Override
    public ChurchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.church_history_row, parent, false);
        ChurchHistoryViewHolder holder = new ChurchHistoryViewHolder(cardView);

        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChurchHistoryViewHolder holder, int position) {
        holder.mName.setText(histories.get(position).getName());
        holder.mChair.setText(histories.get(position).getChairPerson());
        holder.mPobox.setText((histories.get(position).getPobox()));
        holder.mAddress.setText(histories.get(position).getAddress());
        holder.mTel.setText(histories.get(position).getTel());
        holder.mEmail.setText(histories.get(position).getEmail());
    }


    @Override
    public int getItemCount() {
        return histories.size();
    }

    //public class for ChurchHistoryViewHolder
    public static class ChurchHistoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView mName;
        TextView mChair;
        TextView mPobox;
        TextView mAddress;
        TextView mTel;
        TextView mEmail;

        CardView mCard;

        public ChurchHistoryViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.church_info_name);
            mChair = (TextView) itemView.findViewById(R.id.church_info_chair);
            mPobox = (TextView) itemView.findViewById(R.id.church_info_pobox);
            mAddress = (TextView) itemView.findViewById(R.id.church_info_address);
            mTel = (TextView) itemView.findViewById(R.id.church_info_tel);
            mEmail = (TextView) itemView.findViewById(R.id.church_info_email);

            mCard = (CardView) itemView.findViewById(R.id.church_info_card);
        }
    }
}
