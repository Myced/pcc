package com.pefscomsys.pcc_buea;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class EchoFragment extends Fragment {


    public EchoFragment() {
        // Required empty public constructor
    }

    private RecyclerView presEchoRecyclerView;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_echo, container, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(getContext());
        presEchoRecyclerView = view.findViewById(R.id.available_echos_recyclerview);
        presEchoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PresEchoBookAdapter presEchoBookAdapter = new PresEchoBookAdapter(getContext(), new ArrayList<String>(), new ArrayList<String>(), presEchoRecyclerView);
        presEchoRecyclerView.setAdapter(presEchoBookAdapter);
        progressDialog.setMessage("Fetching files please wait...");
        progressDialog.show();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Log.d("YEAR", String.valueOf(year));

        databaseReference.child("books/presbyterian_echo/" + year ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Log.d("On data change", snapshot.toString());
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        ((PresEchoBookAdapter) presEchoRecyclerView.getAdapter()).update(book.getDisplay_name(), book.getUrl());
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        return view;
    }

}
