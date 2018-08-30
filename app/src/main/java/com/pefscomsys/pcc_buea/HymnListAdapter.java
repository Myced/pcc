package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HymnListAdapter extends RecyclerView.Adapter<HymnListAdapter.HymnListHolder> {
    private Context context;
    private String Hymns[], Excerpt[];
    private int Numbers[];

    public HymnListAdapter(Context ctx, String hymns[], String excerpt[], int numbers[]){

      context = ctx;
      Hymns = hymns;
      Excerpt = excerpt;
      Numbers = numbers;

    }

    @Override
    public HymnListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hymn_list_row_design, parent, false);
        return new HymnListHolder(view);
    }

    @Override
    public void onBindViewHolder(HymnListHolder holder, int position) {
        holder.hymenumber.setText(Integer.toString(Numbers[position]));
        holder.hymetitle.setText(Hymns[position]);
        holder.hymnexcerpt.setText(Excerpt[position]);
    }

    @Override
    public int getItemCount() {
        return Numbers.length;
    }

    TextView hymenumber, hymetitle, hymnexcerpt;
    public class HymnListHolder extends RecyclerView.ViewHolder{
        TextView hymenumber, hymetitle, hymnexcerpt;
        public HymnListHolder(View itemView) {
            super(itemView);
            hymenumber = (TextView) itemView.findViewById(R.id.hymnNumber);
            hymetitle = (TextView) itemView.findViewById(R.id.hymnTitle);
            hymnexcerpt = (TextView) itemView.findViewById(R.id.hymnExcerpt);
        }
    }
}
