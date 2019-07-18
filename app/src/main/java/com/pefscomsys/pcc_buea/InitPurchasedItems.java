package com.pefscomsys.pcc_buea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class InitPurchasedItems
{
    private ScriptureDBHandler db;
    private Context context;
    private Activation activation;

    private static final String TABLE_NAME = "purchases";

    public InitPurchasedItems(Context context)
    {
        this.context = context;
        this.db = new ScriptureDBHandler(context);
        this.activation = new Activation(context);

        this.db.openDataBase();

        this.createDatabaseTable();

        //now start shecking for activated products.
        this.checkHymn();

        //check books
        this.checkBooks();

        //check diary
        this.checkDiary();

        this.db.close();
    }

    private void createDatabaseTable()
    {
        String sql = "CREATE TABLE IF NOT EXISTS purchases (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, code TEXT, year TEXT, date TEXT);";


        //create the database;
        this.db.myDataBase.execSQL(sql);

    }

    private void checkHymn()
    {
        if(this.activation.checkHymns())
        {
            //hymns has been activated log it
            this.logHymns();
        }
    }

    private void checkBooks()
    {
        String books[] = {
                "Presbyterian Echo March 2019",
                "The Messenger 2018"
        };

        for (String book: books) {
            if(this.activation.checkBook(book))
            {
                this.logBook(book);
            }
        }
    }

    private void checkDiary()
    {
        String years[] = {"2018", "2019"};

        for(String year : years)
        {
            if(activation.checkDiary(year))
            {
                logDiary(year);
            }
        }
    }

    private void logHymns()
    {
        if(!this.hymnIsLogged())
        {
            this.insertHymn();
        }
    }

    private boolean hymnIsLogged() {
        String sql = "SELECT * FROM `" + TABLE_NAME + "` WHERE `code` = '" + Purchase.HYMN + "' ";

        Cursor result = db.myDataBase.rawQuery(sql, null);

        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    private void insertHymn()
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("name", "Church Hymn Book");
        values.put("code", Purchase.HYMN);
        values.put("year", "0000");
        values.put("date", " -- ");

        String table = TABLE_NAME;

        this.db.myDataBase.insert(table, null, values);
    }

    //logging for books
    private void logBook(String bookName)
    {
        if(!this.bookIsLogged(bookName))
        {
            this.insertBook(bookName);
        }
    }

    private boolean bookIsLogged(String bookName) {
        String sql = "SELECT * FROM `" + TABLE_NAME + "` WHERE `name` = '" + bookName + "' ";

        Cursor result = db.myDataBase.rawQuery(sql, null);

        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    private void insertBook(String bookName)
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("name", bookName);
        values.put("code", Purchase.BOOK);
        values.put("year", "0000");
        values.put("date", " -- ");

        String table = TABLE_NAME;

        this.db.myDataBase.insert(table, null, values);
    }

    //logging for the diaries.
    private void logDiary(String year)
    {
        if(!this.diaryIsLogged(year))
        {
            this.insertDiary(year);
        }
    }

    private boolean diaryIsLogged(String year) {
        String sql = "SELECT * FROM `" + TABLE_NAME + "` WHERE `code` = '" + Purchase.DIARY + "' AND `year` = '" + year + "'";

        Cursor result = db.myDataBase.rawQuery(sql, null);

        if(result.getCount() > 0)
        {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    private void insertDiary(String year)
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("name", "Diary " + year);
        values.put("code", Purchase.DIARY);
        values.put("year", year);
        values.put("date", " -- ");

        String table = TABLE_NAME;

        this.db.myDataBase.insert(table, null, values);
    }

}
