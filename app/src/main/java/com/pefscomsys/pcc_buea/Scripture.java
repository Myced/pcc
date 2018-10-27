package com.pefscomsys.pcc_buea;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;

public class Scripture
{
    private String label;
    private String psalms;
    private String readingOne;
    private String readingTwo;
    private String readingText;
    public String date;

    public ScriptureDBHandler db;

    public String day;
    public String month;
    public String year;

    public Scripture()
    {
        //initialise the database connection

    }

    public Scripture(String psalms, String readingOne, String readingTwo, String readingText, String date) {

        this();

        this.psalms = psalms;
        this.readingOne = readingOne;
        this.readingTwo = readingTwo;
        this.readingText = readingText;
        this.date = date;
    }

    public String getLabel() { return this.label; }

    public void setLabel(String label) { this.label = label; }

    public String getPsalms() {
        return psalms;
    }

    public void setPsalms(String psalms) {
        this.psalms = psalms;
    }

    public String getReadingOne() {
        return readingOne;
    }

    public void setReadingOne(String readingOne) {
        this.readingOne = readingOne;
    }

    public String getReadingTwo() {
        return readingTwo;
    }

    public void setReadingTwo(String readingTwo) {
        this.readingTwo = readingTwo;
    }

    public String getReadingText() {
        return readingText;
    }

    public void setReadingText(String readingText) {
        this.readingText = readingText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public void saveScripture()
    {
        if(this.checkDate() == true)
        {
            updateScripture();
        }
        else
        {
            insertScripture();
        }
    }

    private boolean checkDate()
    {
        String query = "SELECT * FROM `scriptures` WHERE `date` = '" + this.date + "' ";



        //now perform the query
        Cursor result = this.db.myDataBase.rawQuery(query, null);

        if(result.getCount() == 0)
        {
            result.close();
            return false;
        }
        else
        {
            result.close();
            return true;
        }

    }

    private void insertScripture()
    {
        Log.d("PCCAPP", "inserting");

        String table = "scriptures";
        ContentValues values = new ContentValues();

        values.put("day", this.getDay());
        values.put("month", this.getMonth());
        values.put("year", this.getYear());
        values.put("date", this.getDate());
        values.put("psalms", this.getPsalms());
        values.put("reading_one", this.getReadingOne());
        values.put("reading_two", this.getReadingTwo());
        values.put("text", this.getReadingText());
        values.put("name", this.getLabel());

        Log.d("PCCAPP", values.toString());

//        String query = " INSERT INTO `scriptures` " +
//                " (`day`, `month`, `year`, `date`, " +
//                "  `psalms`, `reading_one`, `reading_two`, `text` )" +
//                "" +
//                " VALUES ('" + this.getDay() + "', '" + this.getMonth() + "', '" + this.getYear() + "', '" + this.getDate() + "'," +
//                " '" + this.getPsalms() + "', '" + this.getReadingOne() + "', '" + this.getReadingTwo() + "', " +
//                " '" + this.getReadingText() + "' ) ";



        long result = this.db.myDataBase.insert(table, null, values);

        if(result == -1)
        {
            Log.d("PCCAPP", "Failed to insert");
        }
        else
        {
            Log.d("PCCAPP", "Inserting done");
        }


    }

    private void updateScripture()
    {
        Log.d("PCCAPP", "updating");

        String table = "scriptures";
        ContentValues values = new ContentValues();

        values.put("day", this.getDay());
        values.put("month", this.getMonth());
        values.put("year", this.getYear());
        values.put("psalms", this.getPsalms());
        values.put("reading_one", this.getReadingOne());
        values.put("reading_two", this.getReadingTwo());
        values.put("text", this.getReadingText());
        values.put("name", this.getLabel());

        String where = " `date` = '" + this.getDate() + "' ";
        String[] whereArgs = {};


        //now update the database
        long result = this.db.myDataBase.update(table, values, where, whereArgs);

        if(result == -1)
        {
            Log.d("PCCAPP", "Failed to update");
        }
        else
        {
            Log.d("PCCAPP", "Update successful");
        }

    }
}