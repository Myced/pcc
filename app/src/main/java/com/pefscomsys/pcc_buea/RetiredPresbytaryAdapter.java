package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RetiredPresbytaryAdapter extends RecyclerView.Adapter<RetiredPresbytaryAdapter.RetiredPresbytaryViewHolder>
{
    private List<RetiredPresbytary> retiredPresbytaries;
    private Context context;

    public RetiredPresbytaryAdapter(List<RetiredPresbytary> presbytaries, Context context)
    {
        this.retiredPresbytaries = presbytaries;
        this.context = context;
    }

    @NonNull
    @Override
    public RetiredPresbytaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View presView = LayoutInflater.from(parent.getContext()).inflate(R.layout.retired_pastor_list, parent,false);
        RetiredPresbytaryAdapter.RetiredPresbytaryViewHolder  presbytaryViewHolder = new RetiredPresbytaryAdapter.RetiredPresbytaryViewHolder(presView);
        return presbytaryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RetiredPresbytaryViewHolder holder, int position) {
        holder.nameView.setText(retiredPresbytaries.get(position).getPresbytary());

        //now the id of the presbytary
        String id = retiredPresbytaries.get(position).getId();

        LinearLayoutManager manager = new LinearLayoutManager(this.context);
        RecyclerView pastorList = holder.retiredList;

        ///get the list of pastors.
        ImportantDay day = new ImportantDay();
        day.context = this.context;

        List<PastorList> pastors = day.getPastorList(id);

        RetiredPastorListAdapter pastorListAdapter = new RetiredPastorListAdapter(pastors);

        pastorList.setLayoutManager(manager);
        pastorList.setAdapter(pastorListAdapter);
    }

    @Override
    public int getItemCount() {
        return this.retiredPresbytaries.size();
    }

    public class RetiredPresbytaryViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView;
        public RecyclerView retiredList;

        public RetiredPresbytaryViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.pres_name);
            retiredList = itemView.findViewById(R.id.pastor_list_view);
        }
    }
}
