package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Presbyteries extends AppCompatActivity {

    List<Presbytery> presbyteryList;
    private Context context;
    private RecyclerView presView ;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presbyteries);

        setTitle(R.string.congregation);

        this.context = getApplicationContext();
        this.getPresbyteryList();
        this.presView = findViewById(R.id.pres_view);
        this.tableLayout =  (TableLayout) findViewById(R.id.cong_table);

        LinearLayoutManager manager = new LinearLayoutManager(this.context);

        PresbyteriesAdapter adapter = new PresbyteriesAdapter(this.presbyteryList, this.context, this.tableLayout);

        presView.setLayoutManager(manager);
        presView.setAdapter(adapter);

    }

    private void getPresbyteryList()
    {
        List<Presbytery> presbyteries = new ArrayList<>();

        Presbytery presbytery;

        //do a database call
        ScriptureDBHandler db = new ScriptureDBHandler(context);

        //tty creating a database
        try {
            db.createDataBase();
        } catch (IOException e) {
            Log.d("PCCAPP", "Database error");
            e.printStackTrace();
        }

        //now open the databse
        db.openDataBase();

        String query = "SELECT * FROM presbyteries ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            presbytery = new Presbytery();

            presbytery.setId(result.getString(0));
            presbytery.setName(result.getString(1));
            presbytery.setPresbytery(result.getString(2));
            presbytery.setSecretery(result.getString(3));
            presbytery.setSecTel(result.getString(4));
            presbytery.setChair(result.getString(7));
            presbytery.setChairTel(result.getString(8));
            presbytery.setTreasurer(result.getString(5));
            presbytery.setTreasurerTel(result.getString(6));
            presbytery.setCong(result.getString(9));

            presbyteries.add(presbytery);
        }

        //close the result
        result.close();

        //close the database
        db.close();

        this.presbyteryList = presbyteries;
    }

    private List<PresbyteryCongregation> getCongregations(String PresbyteryId)
    {
        List<PresbyteryCongregation> congregations = new ArrayList<>();

        PresbyteryCongregation station;


        //do a database call
        ScriptureDBHandler db = new ScriptureDBHandler(context);

        //tty creating a database
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //now open the databse
        db.openDataBase();

        String query = "SELECT * FROM presbytery_workers WHERE `presbytery` =  '" + PresbyteryId + "' ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            station = new PresbyteryCongregation();

            station.setStation(result.getString(1));
            station.setWorker(result.getString(2));
            station.setTel(result.getString(3));

            congregations.add(station);
        }

        //close the result
        result.close();

        //close the database
        db.close();

        return congregations;
    }
}
