package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;

public class Activation
{
    private Context context;
    public ScriptureDBHandler db;
    private final int STATUS_INDEX = 2;

    public Activation(Context context)
    {
        this.context = context;
    }

    public boolean checkDiary(String year)
    {
        //the status is false by default;
        boolean status = false;
        String currentStatus = "";

        db = new ScriptureDBHandler(this.context);

        //try to initialise the database;
        try {
            this.db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //open the datbase
        this.db.openDataBase();

        //do processing here
        String query = "SELECT * FROM `diary_payments` WHERE `year` = '" + year + "' ";
        Log.d("PCCAPP", query);

        Cursor result = this.db.myDataBase.rawQuery(query, null);

        if(result.getCount() == 0)
        {
            status = false;
        }
        else
        {
            while(result.moveToNext())
            {
                currentStatus = result.getString(STATUS_INDEX);
            }

            //now check if the result is true or false;
            if(currentStatus.equals("TRUE"))
            {
                status = true;
            }
            else
            {
                status = false;
            }
        }

        //close the connection
        this.db.close();

        return status;
    }

    public boolean checkHymn()
    {
        //TODo write code to check hymn activation

        return true;
    }

    public boolean checkEcho(String name)
    {
        //TODO write code to check if a particular echo had been paid for

        return true;
    }

    public boolean checkMessenger(String year)
    {
        //TODO write code to check messenger activation for a particular year

        return true;
    }
}
