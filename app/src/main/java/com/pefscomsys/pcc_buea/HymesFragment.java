package com.pefscomsys.pcc_buea;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HymesFragment extends Fragment implements SearchView.OnQueryTextListener,HymnListAdapter.OnItemClickListener {


    public static final String HYMN_NUMBER = "hymnNumber";
    public static final String HYMN = "hymn";

    public HymesFragment() {
        // Required empty public constructor
    }

    RecyclerView hymnListRecycler;
    String hymns[];
    HymnListAdapter hymnListAdapter;
    ArrayList<Hymn> hymnArrayList = new ArrayList<>();
    int count = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hymes, container, false);
        hymnListRecycler = (RecyclerView) view.findViewById(R.id.hymnListRecyclerView);

        hymns = view.getResources().getStringArray(R.array.hymns);
        hymnListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        hymnListRecycler.setHasFixedSize(true);

        for(String hymn :hymns ){
            int num = count + 1;
            hymnArrayList.add(new Hymn(hymn, num));
            count++;
        }
        hymnListAdapter = new HymnListAdapter(getContext(), hymnArrayList);
        hymnListRecycler.setAdapter(hymnListAdapter);
        hymnListAdapter.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
        Intent hymnViewIntent = new Intent(getActivity(), HymeView.class);
        hymnViewIntent.putExtra(HYMN_NUMBER, position);;
        hymnViewIntent.putExtra(HYMN, hymns[position]);
        startActivity(hymnViewIntent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hymn_fragment_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(getActivity(), R.string.search, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Hymn> newHymnList = new ArrayList<>();
        for(Hymn hymn : hymnArrayList){
            String number = Integer.toString(hymn.getHymnNumber());

        }
        return false;
    }
}
