package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
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
        holder.presTable.setShrinkAllColumns(true);

//        table.setStretchAllColumns(true);

        for(int i = 0; i < stations.size(); i++)
        {
            int myCount = i + 1;
            //create and populate new table rows
            TableRow row = new TableRow(this.context);

            TextView count = new TextView(this.context);
            count.setText(Integer.toString(myCount));
            count.setPadding(2, 2, 2, 2);

            TextView station = new TextView(context);
            station.setText(stations.get(i).getStation());
            station.setPadding(2, 2, 2, 2);

            TextView worker = new TextView(context);
            worker.setText(stations.get(i).getWorker());
            worker.setPadding(2, 2, 2, 2);

            TextView tel = new TextView(context);
            tel.setText(stations.get(i).getTel());
            tel.setPadding(2, 2, 2, 2);

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
