package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ScriptureRetrieval {


    private int year;
    private Object diary = null;
    public Context context;

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

                FScripture changes = (FScripture) diary;

                //do saving here. it will be best
                //diary might return null
                ScriptureDBHandler connection = new ScriptureDBHandler(context);

                Scripture myScripture;

                try {
                    connection.createDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //then op the connection
                connection.openDataBase();

                //then loop through the scriptures and save them
                //pass the connection to the scripture class
                myScripture = new Scripture();
                myScripture.db = connection;

                myScripture.setDay(changes.getDay());
                myScripture.setMonth(changes.getMonth());
                myScripture.setYear(changes.getYear());
                myScripture.setDate(changes.getDate());
                myScripture.setPsalms(changes.getPsalms());
                myScripture.setReadingOne(changes.getReadingOne());
                myScripture.setReadingTwo(changes.getReadingTwo());
                myScripture.setReadingText(changes.getReadingText());

                //now save the scripture
                myScripture.saveScripture();


                //then close the connection
                connection.close();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                
            }
        });
        return diary;
    }
}
