package com.pefscomsys.pcc_buea;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HymnListAdapter extends RecyclerView.Adapter<HymnListAdapter.HymnListHolder> {
    private Context context;
    private ArrayList<Hymn> hymnList;
    private int Number;
    private OnItemClickListener hymeListListener;
    Resources res;

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
        res = context.getResources();
        return new HymnListHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NewApi"})
    @Override
    public void onBindViewHolder(HymnListHolder holder, int position) {
        Number = hymnList.get(position).getHymnNumber();

        holder.hymnnumber.setText(Integer.toString(Number));
        holder.hymntitle.setText("HYMN " + Number);
        holder.hymnexcerpt.setText(hymnList.get(position).getHymn());
    }

    @Override
    public int getItemCount() {
        return hymnList.size();
    }

    public class HymnListHolder extends RecyclerView.ViewHolder{
        public TextView hymnnumber, hymntitle, hymnexcerpt;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public HymnListHolder(View itemView) {
            super(itemView);
           hymnnumber =  (TextView) itemView.findViewById(R.id.hymnNumber);
            hymntitle =  (TextView) itemView.findViewById(R.id.hymnTitle);
            hymnexcerpt =  (TextView) itemView.findViewById(R.id.hymnExcerpt);
            int value = (int) ((Math.random() * 3) + 1);
            switch (value){
                case 1:
                    hymnnumber.setBackground(res.getDrawable(R.drawable.circle_grey));
                    break;
                case  2:
                    hymnnumber.setBackground(res.getDrawable(R.drawable.circle_blue));
                    break;
                case  3:
                    hymnnumber.setBackground(res.getDrawable(R.drawable.circle_red));
                    break;
                default:
                    hymnnumber.setBackground(res.getDrawable(R.drawable.circle_red));
                    break;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hymeListListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            hymeListListener.onItemClick(hymnList.get(position).getHymnNumber());
                        }
                    }
                }
            });
        }
    }

    public void updateList(ArrayList<Hymn> newHymnList){
        hymnList = new ArrayList<>();
        hymnList = newHymnList;
        notifyDataSetChanged();
    }
}
