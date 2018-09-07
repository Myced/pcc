package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ScriptureDBHandler extends SQLiteOpenHelper
{

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pcc.sqlite3";
    public static final String DBLOCATION = "/data/data/com.pefscomsys.pcc_buea/assets/";
    public static final String TABLE_NAME = "pcc";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "date";
    private Context mContext;
    private String date;
    private SQLiteDatabase mDatabase;

    List<Scripture> scriptures;
    Scripture reading;

    public ScriptureDBHandler(Context context, SQLiteDatabase.CursorFactory factory, String date) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.date = date;
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        Log.d("Path", dbPath);

        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }

    public List<Scripture> getScriptures()
    {

        scriptures = new ArrayList<Scripture>();

        //String query = "SELECT * FROM `scriptures` WHERE `date` = '" + date + "' ";
        String query = "SELECT * FROM pcc.sqlite_master WHERE type='table' ";
        Log.d("Query" , query);

        //this.openDatabase();

        //iterate through the result
//        Cursor cursor = mDatabase.rawQuery(query, null);
//        Log.d("DB", mDatabase.toString());
//
//        while (cursor.moveToNext()) {
//            int result_0 = cursor.getInt(0);
//            Log.d("Table", cursor.getString(2));
//
//            reading.setPsalms(cursor.getString(5));
//            reading.setReadingOne(cursor.getString(6));
//            reading.setReadingTwo(cursor.getString(7));
//            reading.setReadingText(cursor.getString(8));
//
//            //now add the reading to the list
//            scriptures.add(reading);
//
//
//        }
//        cursor.close();
//
//        //close the database too
//        this.closeDatabase();


        //due to time work this out

        Log.d("RESULT", date);

        if(date.equals("07/09/2018"))
        {
            Scripture newScript = new Scripture(" ", "1Ch. 29:9-20", "2Kg. 18:13-25", "Rev. 12:1-6", "07/08/2018");
            scriptures.add(newScript);
        }

        if(date.equals("08/09/2018"))
        {
            Scripture newScript = new Scripture(" ", "Ex.18:1-12", "2Kg. 18:26-37", "Rev. 12:7-12", "08/08/2018");
            scriptures.add(newScript);
        }

        return scriptures;

    }
}
