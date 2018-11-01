package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportantDay
{
    private String day;
    public Context context;

    public ImportantDay()
    {

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<PastorList> getPastorList(String presbytary)
    {
        List<PastorList> pastors = new ArrayList<>();

        PastorList pastor;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `retired_pastor_list` WHERE `presbytary` = '" + presbytary + "' ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            pastor = new PastorList();

            pastor.setId(result.getString(0));
            pastor.setCount(result.getString(2));
            pastor.setName(result.getString(3));

            pastors.add(pastor);
        }

        result.close();
        db.close();

        return pastors;
    }

    public List<PresbyteryCongregation> getCongregations(String PresbyteryId)
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

            station.setStation(result.getString(2));
            station.setWorker(result.getString(3));
            station.setTel(result.getString(4));

            congregations.add(station);
        }

        //close the result
        result.close();

        //close the database
        db.close();

        return congregations;
    }
}
