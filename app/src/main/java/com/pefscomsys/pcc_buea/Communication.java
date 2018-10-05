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

public class Communication extends AppCompatActivity {

    private List<CommunicationProg> communications;
    private Context context;
    private RecyclerView comView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        comView = findViewById(R.id.com_view);

        setTitle(R.string.communication);

        //now get the communications
        this.getCommunications();

        //now prepare a layout manager
        LinearLayoutManager manager = new LinearLayoutManager(context);

        CommunicationAdapter adapter = new CommunicationAdapter(this.communications);

        comView.setLayoutManager(manager);
        comView.setAdapter(adapter);


    }

    private void getCommunications()
    {
        Context context = this.context;
        List<CommunicationProg> communications = new ArrayList<>();

        CommunicationProg com;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `communication` ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            com = new CommunicationProg();

            com.setName(result.getString(1));
            com.setRow1(result.getString(2));
            com.setRow2(result.getString(3));
            com.setRow3(result.getString(4));
            com.setRow4(result.getString(5));
            com.setRow5(result.getString(6));

            communications.add(com);
        }

        result.close();
        db.close();

        this.communications = communications;
    }
}
