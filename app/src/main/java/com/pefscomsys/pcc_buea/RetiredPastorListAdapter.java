package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RetiredPastorListAdapter extends  RecyclerView.Adapter<RetiredPastorListAdapter.PastorListViewHolder>
{
    private List<PastorList> pastorList;

    public RetiredPastorListAdapter(List<PastorList> pastors)
    {
        this.pastorList = pastors;
    }

    @NonNull
    @Override
    public PastorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pastorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pastor_list, parent,false);
        RetiredPastorListAdapter.PastorListViewHolder pastorListViewHolder = new RetiredPastorListAdapter.PastorListViewHolder(pastorView);

        return pastorListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PastorListViewHolder holder, int position) {
        holder.pastorName.setText(pastorList.get(position).getName());
        holder.pastorCount.setText(pastorList.get(position).getCount());
    }

    @Override
    public int getItemCount() {
        return pastorList.size();
    }

    public class PastorListViewHolder extends RecyclerView.ViewHolder {

        public TextView pastorCount, pastorName;

        public PastorListViewHolder(View itemView) {
            super(itemView);

            pastorCount = itemView.findViewById(R.id.pastor_count);
            pastorName = itemView.findViewById(R.id.pastor_name);
        }
    }
}
