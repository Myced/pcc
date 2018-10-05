package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RetiredPastors extends AppCompatActivity {

    private RecyclerView pastorView, pastorListView;
    private List<RetiredPresbytary> retiredPresbytaries;
    private List<PastorList> pastorLists;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retired_pastors);
        setTitle(R.string.retired_pastors);

        this.context = getApplicationContext();
        this.getRetiredPresbytaries();

        pastorView = findViewById(R.id.pastor_view);
        pastorListView = findViewById(R.id.pastor_list_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        RetiredPresbytaryAdapter presbytaryAdapter = new RetiredPresbytaryAdapter(this.retiredPresbytaries, this.context);

        pastorView.setLayoutManager(layoutManager);
        pastorView.setAdapter(presbytaryAdapter);

    }

    public void getRetiredPresbytaries()
    {
        List<RetiredPresbytary> presbytaries = new ArrayList<>();

        RetiredPresbytary presbytary;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `retired_pastor_presbytary` ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            presbytary = new RetiredPresbytary();


            presbytary.setId(result.getString(0));
            presbytary.setPresbytary(result.getString(1));

            presbytaries.add(presbytary);
        }

        this.retiredPresbytaries = presbytaries;

        result.close();
        db.close();
    }

}
