package com.pefscomsys.pcc_buea;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScriptureRetrieval {


    private int year;
    private Object diary = null;

    public ScriptureRetrieval(int year) {
        this.year = year;
    }


    //Use this method to pull scriptures of the year passed in
    public Object pullScriptures(){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef.child(String.valueOf(year)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("RetrievingScriptures", String.valueOf(dataSnapshot.getValue()));
                diary = dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return diary;
    }
}
