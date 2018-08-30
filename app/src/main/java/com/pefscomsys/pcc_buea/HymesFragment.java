package com.pefscomsys.pcc_buea;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.PrivateKey;


/**
 * A simple {@link Fragment} subclass.
 */
public class HymesFragment extends Fragment {


    public HymesFragment() {
        // Required empty public constructor
    }

    RecyclerView hymnListRecycler;
    String hymnTitles[], hymnExcerpts[];
    int hymnNumber[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    HymnListAdapter hymnListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hymes, container, false);
        hymnListRecycler = (RecyclerView) view.findViewById(R.id.hymnListRecyclerView);

        hymnTitles = view.getResources().getStringArray(R.array.hymns);
        hymnExcerpts = view.getResources().getStringArray(R.array.hymns_excerpt);
        hymnListAdapter = new HymnListAdapter(getContext(), hymnTitles, hymnExcerpts, hymnNumber);
        hymnListRecycler.setAdapter(hymnListAdapter);
        hymnListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}
