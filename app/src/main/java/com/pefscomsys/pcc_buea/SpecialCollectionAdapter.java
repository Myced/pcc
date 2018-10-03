package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SpecialCollectionAdapter extends RecyclerView.Adapter<SpecialCollectionAdapter.SpecialCollectionViewHolder>
{
    private List<SpecialCollection> specialCollections;

    public SpecialCollectionAdapter(List<SpecialCollection> specialCollectionList)
    {
        this.specialCollections = specialCollectionList;
    }
    @NonNull
    @Override
    public SpecialCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_collection_row, parent, false);

        SpecialCollectionAdapter.SpecialCollectionViewHolder ivh  = new SpecialCollectionAdapter.SpecialCollectionViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialCollectionViewHolder holder, int position) {
        holder.specialCollection.setText(specialCollections.get(position).getCollection());
    }

    @Override
    public int getItemCount() {
        return specialCollections.size();
    }

    public class SpecialCollectionViewHolder extends RecyclerView.ViewHolder {

        public TextView specialCollection;

        public SpecialCollectionViewHolder(View itemView)
        {
            super(itemView);
            specialCollection = itemView.findViewById(R.id.special_collection_row);
        }
    }
}
