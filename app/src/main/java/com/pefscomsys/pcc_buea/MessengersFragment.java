package com.pefscomsys.pcc_buea;


import android.app.ProgressDialog;
import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MessengersFragment extends Fragment {

    private RecyclerView messengerRecyclerView;
    ProgressDialog progressDialog;

    public MessengersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messengers, container, false);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(getContext());
        messengerRecyclerView = view.findViewById(R.id.available_messengers_recyclerview);
        messengerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MessengerBookAdapter messengerBookAdapter = new MessengerBookAdapter(getContext(), new ArrayList<String>(), new ArrayList<String>(), messengerRecyclerView);
        messengerRecyclerView.setAdapter(messengerBookAdapter);
        progressDialog.setMessage("Fetching files please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        databaseReference.child("books/the_messenger").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                if (book != null) {
                    ((MessengerBookAdapter) messengerRecyclerView.getAdapter()).update(book.getDisplay_name(), book.getUrl());
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
