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
        if(communicationProgList.get(position).getRow1() != null){
            holder.row1.setText(communicationProgList.get(position).getRow1().replaceAll("\\\\n", System.getProperty("line.separator")));
        }
        else{
            holder.row1.setText(" ");
        }

        if(communicationProgList.get(position).getRow2() != null){
            holder.row2.setText(communicationProgList.get(position).getRow2().replaceAll("\\\\n", System.getProperty("line.separator")));
        }
        else{
            holder.row2.setText(" ");
        }

        if(communicationProgList.get(position).getRow3() != null){

            holder.row3.setText(communicationProgList.get(position).getRow3().replaceAll("\\\\n", System.getProperty("line.separator")));
        }
        else{
            holder.row3.setText(" ");
        }

        if(communicationProgList.get(position).getRow4() != null){

            holder.row4.setText(communicationProgList.get(position).getRow4().replaceAll("\\\\n", System.getProperty("line.separator")));
        }
        else{
            holder.row4.setText(" ");
        }

        if(communicationProgList.get(position).getRow5() != null){

            holder.row5.setText(communicationProgList.get(position).getRow5().replaceAll("\\\\n", System.getProperty("line.separator")));
        }
        else{
            holder.row5.setText(" ");
        }

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
