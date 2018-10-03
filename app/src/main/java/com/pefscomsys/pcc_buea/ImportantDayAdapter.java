package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ImportantDayAdapter extends RecyclerView.Adapter<ImportantDayAdapter.ImportantDayViewHolder>
{
    private List<ImportantDay> importantDays;
    public ImportantDayAdapter(List<ImportantDay> importantDayList)
    {
        this.importantDays = importantDayList;
    }

    @NonNull
    @Override
    public ImportantDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.important_days_row, parent, false);

        ImportantDayAdapter.ImportantDayViewHolder ivh  = new ImportantDayAdapter.ImportantDayViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImportantDayViewHolder holder, int position) {
        holder.importantDay.setText(importantDays.get(position).getDay());
    }

    @Override
    public int getItemCount() {
        return importantDays.size();
    }

    public class ImportantDayViewHolder extends RecyclerView.ViewHolder {

        public TextView importantDay;

        public ImportantDayViewHolder(View itemView) {
            super(itemView);
            this.importantDay = itemView.findViewById(R.id.important_day_row);
        }
    }
}
