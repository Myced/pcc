package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class PresbyteriesAdapter extends RecyclerView.Adapter<PresbyteriesAdapter.PresbyteriesViewHolder>
{
    List<Presbytery> presbyteries;
    private Context context;
    private TableLayout tableLayout;

    public PresbyteriesAdapter(List<Presbytery> presbyteryList, Context context, TableLayout tableLayout)
    {
        this.presbyteries = presbyteryList;
        this.context = context;
        this.tableLayout = tableLayout;


    }

    @NonNull
    @Override
    public PresbyteriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View presView = LayoutInflater.from(parent.getContext()).inflate(R.layout.presbyteries_row, parent,false);
        PresbyteriesAdapter.PresbyteriesViewHolder presHolder = new PresbyteriesAdapter.PresbyteriesViewHolder(presView);

        return presHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PresbyteriesViewHolder holder, int position) {
        holder.name.setText(presbyteries.get(position).getName());
        holder.presbetery.setText(presbyteries.get(position).getPresbytery());
        holder.sec.setText(presbyteries.get(position).getSecretery());
        holder.secTel.setText(presbyteries.get(position).getSecTel());
        holder.treas.setText(presbyteries.get(position).getTreasurer());
        holder.treasTel.setText(presbyteries.get(position).getTreasurerTel());
        holder.chairTel.setText(presbyteries.get(position).getChairTel());
        holder.chair.setText(presbyteries.get(position).getChair());
        holder.cong.setText(presbyteries.get(position).getCong());

        //get the id of the current presbytery
        String id = presbyteries.get(position).getId();

        //now get the
        ///get the list of pastors.
        ImportantDay day = new ImportantDay();
        day.context = this.context;

        List<PresbyteryCongregation> stations = day.getCongregations(id);

        //initilaise the holder table to null before adding anything to it

        holder.presTable.removeAllViews();

        int headerTextColor = Color.WHITE;
        int bodyTextColoor = Color.BLACK;
        float bodyTextSize = 16;
        int paddingValue  =  16;

        //create a row for the header
        TableRow header = new TableRow(this.context);
        header.setBackgroundResource(R.color.colorPrimary);

        TextView countHeading = new TextView(context);
        countHeading.setText("No");
        countHeading.setTextColor(headerTextColor);
        countHeading.setTextSize(20);
        countHeading.setTypeface(null, Typeface.BOLD);
        countHeading.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        countHeading.setBackgroundResource(R.drawable.border_square);

        TextView stationHeading = new TextView(context);
        stationHeading.setText("Station");
        stationHeading.setTextColor(headerTextColor);
        stationHeading.setTextSize(20);
        stationHeading.setTypeface(null, Typeface.BOLD);
        stationHeading.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        stationHeading.setBackgroundResource(R.drawable.border_square);

        TextView workerHeading = new TextView(context);
        workerHeading.setText("Worker");
        workerHeading.setTextColor(headerTextColor);
        workerHeading.setTextSize(20);
        workerHeading.setTypeface(null, Typeface.BOLD);
        workerHeading.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        workerHeading.setBackgroundResource(R.drawable.border_square);

        TextView telHeading = new TextView(context);
        telHeading.setText("Telephone");
        telHeading.setTextColor(headerTextColor);
        telHeading.setTextSize(20);
        telHeading.setTypeface(null, Typeface.BOLD);
        telHeading.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
        telHeading.setBackgroundResource(R.drawable.border_square);

        header.addView(countHeading);
        header.addView(stationHeading);
        header.addView(workerHeading);
        header.addView(telHeading);

        holder.presTable.addView(header);


        for(int i = 0; i < stations.size(); i++)
        {
            int myCount = i + 1;
            //create and populate new table rows
            TableRow row = new TableRow(this.context);

            TextView count = new TextView(this.context);
            count.setText(Integer.toString(myCount));
            count.setTextSize(bodyTextSize);
            count.setTextColor(bodyTextColoor);
            count.setBackgroundResource(R.drawable.border_square);
            count.setPadding(10, 10, 10, 10);

            TextView station = new TextView(context);
            station.setText(stations.get(i).getStation());
            station.setTextSize(bodyTextSize);
            station.setTextColor(bodyTextColoor);
            station.setBackgroundResource(R.drawable.border_square);
            station.setPadding(10, 10, 10, 10);

            TextView worker = new TextView(context);
            worker.setText(stations.get(i).getWorker());
            worker.setTextSize(bodyTextSize);
            worker.setTextColor(bodyTextColoor);
            worker.setBackgroundResource(R.drawable.border_square);
            worker.setPadding(10, 10, 10, 10);

            TextView tel = new TextView(context);
            tel.setText(stations.get(i).getTel());
            tel.setTextSize(bodyTextSize);
            tel.setTextColor(bodyTextColoor);
            tel.setBackgroundResource(R.drawable.border_square);
            tel.setPadding(10, 10, 10, 10);

            //add the views
            row.addView(count);
            row.addView(station);
            row.addView(worker);
            row.addView(tel);

            //now add the row to the table view
            holder.presTable.addView(row);

        }
    }

    @Override
    public int getItemCount() {
        return this.presbyteries.size();
    }

    public class PresbyteriesViewHolder extends RecyclerView.ViewHolder {

        public TextView name, presbetery, sec, secTel, chair, chairTel, treas, treasTel, cong;
        CardView presCard;
        TableLayout presTable;

        public PresbyteriesViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.pres_name);
            this.presbetery = itemView.findViewById(R.id.pres_pres);
            this.sec = itemView.findViewById(R.id.pres_sec);
            this.secTel = itemView.findViewById(R.id.pres_sec_tel);
            this.chair = itemView.findViewById(R.id.pres_chair);
            this.chairTel = itemView.findViewById(R.id.pres_chair_tel);
            this.treas = itemView.findViewById(R.id.pres_treas);
            this.treasTel = itemView.findViewById(R.id.pres_treas_tel);
            this.cong = itemView.findViewById(R.id.pres_cong);
            this.presTable = itemView.findViewById(R.id.cong_table);

            this.presCard = itemView.findViewById(R.id.pres_card);
        }
    }
}
