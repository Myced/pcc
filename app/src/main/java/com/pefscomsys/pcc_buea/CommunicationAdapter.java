package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommunicationAdapter extends  RecyclerView.Adapter<CommunicationAdapter.CommunicationViewHolder>
{
    private List<CommunicationProg> communicationProgList;
    public CommunicationAdapter(List<CommunicationProg> communicationProgList)
    {
        this.communicationProgList = communicationProgList;
    }

    @NonNull
    @Override
    public CommunicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View comView = LayoutInflater.from(parent.getContext()).inflate(R.layout.communication_row, parent,false);
        CommunicationAdapter.CommunicationViewHolder comHolder = new CommunicationAdapter.CommunicationViewHolder(comView);

        return comHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunicationViewHolder holder, int position) {
        holder.name.setText(communicationProgList.get(position).getName());
        holder.row1.setText(communicationProgList.get(position).getRow1());
        holder.row2.setText(communicationProgList.get(position).getRow2());
        holder.row3.setText(communicationProgList.get(position).getRow3());
        holder.row4.setText(communicationProgList.get(position).getRow4());
        holder.row5.setText(communicationProgList.get(position).getRow5());
    }

    @Override
    public int getItemCount() {
        return this.communicationProgList.size();
    }

    public class CommunicationViewHolder extends RecyclerView.ViewHolder {

        public TextView name, row1, row2, row3, row4, row5;
        public CardView comCard;

        public CommunicationViewHolder(View itemView) {
            super(itemView);

            comCard = itemView.findViewById(R.id.com_card);
            name = itemView.findViewById(R.id.com_name);
            row1 = itemView.findViewById(R.id.com_row1);
            row2 = itemView.findViewById(R.id.com_row2);
            row3 = itemView.findViewById(R.id.com_row3);
            row4 = itemView.findViewById(R.id.com_row4);
            row5 = itemView.findViewById(R.id.com_row5);
        }
    }
}
