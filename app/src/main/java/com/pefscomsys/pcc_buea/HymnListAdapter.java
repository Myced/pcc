package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HymnListAdapter extends RecyclerView.Adapter<HymnListAdapter.HymnListHolder> {
    private Context context;
    private ArrayList<Hymn> hymnList = new ArrayList<>();
    private int Number;
    private OnItemClickListener hymeListListener;

    public HymnListAdapter(Context ctx, ArrayList<Hymn> hymns){

      context = ctx;
      this.hymnList = hymns;

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        hymeListListener = listener;
    }

    @Override
    public HymnListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hymn_list_row_design,parent, false);
        return new HymnListHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(HymnListHolder holder, int position) {
        Number = position + 1;
        holder.hymnnumber.setText(Integer.toString(Number));
        holder.hymntitle.setText("HYME " + Number);

        holder.hymnexcerpt.setText(hymnList.get(position).getHymn());
    }

    @Override
    public int getItemCount() {
        return hymnList.size();
    }

    public class HymnListHolder extends RecyclerView.ViewHolder{
        public TextView hymnnumber, hymntitle, hymnexcerpt;
        public HymnListHolder(View itemView) {
            super(itemView);
            hymnnumber =  (TextView) itemView.findViewById(R.id.hymnNumber);
            hymntitle =  (TextView) itemView.findViewById(R.id.hymnTitle);
            hymnexcerpt =  (TextView) itemView.findViewById(R.id.hymnExcerpt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hymeListListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            hymeListListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void updateList(ArrayList<Hymn> newHymnList){
        hymnList = new ArrayList<>();
        hymnList.addAll(newHymnList);
        notifyDataSetChanged();
    }
}
