package com.pefscomsys.pcc_buea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.List;

public class Education extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        setTitle("Education Information");

        //create an instance of school processor
        SchoolProcessor process = new SchoolProcessor(getApplicationContext());

        List<School> mySchools = process.getSchools();

        //get the recycler view to set the adapter
        RecyclerView schoolView = findViewById(R.id.school_view);

        //create an adapter
        SchoolViewAdapter adapter = new SchoolViewAdapter(mySchools);

        //create a layout for the recycler
        LinearLayoutManager schoolLayout = new LinearLayoutManager(getApplicationContext());

        //set the recyler layout
        schoolView.setLayoutManager(schoolLayout);
        schoolView.setAdapter(adapter);

    }
}
