package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PublicHolidayAdapter extends RecyclerView.Adapter<PublicHolidayAdapter.PublicHolidayViewHolder>
{
    private List<ChurchAppCalendar> holidays;

    public PublicHolidayAdapter(List<ChurchAppCalendar> holidays)
    {
        this.holidays = holidays;
    }

    @NonNull
    @Override
    public PublicHolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_holiday_row, parent, false);

        PublicHolidayAdapter.PublicHolidayViewHolder ivh  = new PublicHolidayAdapter.PublicHolidayViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull PublicHolidayViewHolder holder, int position) {
        holder.publicHoliday.setText(this.holidays.get(position).getRow());
    }

    @Override
    public int getItemCount() {
        return holidays.size();
    }

    public class PublicHolidayViewHolder extends RecyclerView.ViewHolder
    {
        public TextView publicHoliday;

        public PublicHolidayViewHolder(View itemView) {
            super(itemView);
            publicHoliday = itemView.findViewById(R.id.public_holidays_row);
        }
    }
}
