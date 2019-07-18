package com.pefscomsys.pcc_buea;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;

public class Activation
{
    private Context context;
    public ScriptureDBHandler db;
    private final int STATUS_INDEX = 2;

    private SharedPreferences paymentPrefs;

    public Activation(Context context)
    {
        this.context = context;
        this.paymentPrefs = context.getSharedPreferences(PAYMENT_PREFS, Context.MODE_PRIVATE);

        //work with the database
        db = new ScriptureDBHandler(this.context);

        //try to initialise the database;
        try {
            this.db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDiary(String year)
    {
        //the status is false by default;
        boolean status = false;
        String currentStatus = "";

        //open the datbase
        this.db.openDataBase();

        //do processing here
        String query = "SELECT * FROM `diary_payments` WHERE `year` = '" + year + "' ";

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

    public boolean checkBook(String name)
    {
        if(this.paymentPrefs.getString(name.toUpperCase(), "NOT_PAID").equals("NOT_PAID"))
        {
            return false;
        }

        return true;
    }

    public boolean checkHymns()
    {
        if(this.paymentPrefs.getString(context.getString(R.string.HYMN_STATUS), "NOT_PAID").equals("NOT_PAID"))
        {
            return false;
        }

        return true;
    }

    public void activateDiary(String year)
    {
        //open the database
        db.openDataBase();

        //we are activating the diary for the given year
        //so the status is TRUE
        String status = "TRUE";

        //check if the year is already entered into the database
        //check to see if the record exists
        String query = "SELECT * FROM `diary_payments` WHERE `year` = '" + year + "'";

        //peform the query
        Cursor result = this.db.myDataBase.rawQuery(query, null);

        if(result.getCount() == 0)
        {
            this.insertDiary(year, status);
        }
        else
        {
            this.updateDiaryYear(year, status);
        }

        //close the cursor
        result.close();

        db.close();
    }

    private void insertDiary(String year, String status)
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("status", status);

        String table = "diary_payments";

        this.db.myDataBase.insert(table, null, values);

    }

    private void updateDiaryYear(String year, String status)
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("status", status);

        String where = "year = '" + year + "'";
        String[] whereArgs = {};

        String table = "diary_payments";

        this.db.myDataBase.update(table, values, where, whereArgs);
    }

    public void activateBook(String name)
    {
        SharedPreferences.Editor editor = paymentPrefs.edit();

        editor.putString(name.toUpperCase(), "PAID");
        editor.apply();
    }

    public void activateHymns()
    {
        SharedPreferences.Editor editor = paymentPrefs.edit();

        editor.putString(context.getString(R.string.HYMN_STATUS), "PAID");
        editor.apply();
    }

}
