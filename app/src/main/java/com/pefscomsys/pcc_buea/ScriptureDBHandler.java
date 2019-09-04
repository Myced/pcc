package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ScriptureDBHandler extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.pefscomsys.pcc_buea/databases/";

    private static String DB_NAME = "pcc.db";

    public SQLiteDatabase myDataBase;

    private final Context myContext;

    //setup indexes for column results
    private final int PSALMS_INDEX = 5;
    private final int READING_ONE_INDEX = 6;
    private final int READING_TWO_INDEX = 7;
    private final int READING_TEXT_INDEX = 8;
    private final int LABEL_INDEX = 9;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public ScriptureDBHandler(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            Log.d("Database", "Database exist");
        }else{

            //By calling this method an empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            this.close();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        Log.d("PCC", "copyDataBase: " + myInput.toString());

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.d("Database", "Database opened");

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
    public List<Scripture> getReadings(String date)
    {
        List<Scripture> reading = new ArrayList<Scripture>();

        //open the database connection
        try
        {
            this.openDataBase();
        }
        catch (SQLException e)
        {
            Log.d("Error", e.getMessage());
        }

        //do our query inside here
        String query = "SELECT * FROM scriptures WHERE `date` = '" + date + "' ";
        Log.d("Database", query);

        Cursor result  = myDataBase.rawQuery(query, null);



        int count = result.getCount();

        while(result.moveToNext())
        {

            Scripture currentReading = new Scripture();
            currentReading.setPsalms(result.getString(PSALMS_INDEX));
            currentReading.setReadingOne(result.getString(READING_ONE_INDEX));
            currentReading.setReadingTwo(result.getString(READING_TWO_INDEX));
            currentReading.setReadingText(result.getString(READING_TEXT_INDEX));
            currentReading.setLabel(result.getString(LABEL_INDEX));


            //now add the result to the array list
            reading.add(currentReading);
        }

        //close the cursor result
        result.close();

        //close the database connection
        this.close();

        //return the reading
        return reading;
    }

}