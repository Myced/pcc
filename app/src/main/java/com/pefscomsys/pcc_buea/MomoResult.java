package com.pefscomsys.pcc_buea;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;

public class MomoResult
{
    public String receiverNumber;
    public String statusCode;
    public String amount;
    public String resultString;

    public Context context;
    private ScriptureDBHandler db;

    private JSONObject response;

    public boolean success;
    public String message; // will contain the message for the result


    public MomoResult(JSONObject object)
    {
        this.response = object;

        try {
            receiverNumber = object.getString("ReceiverNumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            statusCode = object.getString("StatusCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            amount = object.getString("Amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        resultString = object.toString();

        //now process the result
        if(statusCode.equals("529"))
        {
            this.success = false;
            this.message = "You don't have enough money. Please recharge";
        }
        else if(statusCode.equals("100"))
        {
            this.success = false;
            this.message = "Transaction Failed";
        }
        else if(statusCode.equals("01"))
        {
            this.success = true;
            this.message = "Payment Successful";
        }
        else
        {
            this.success = false;
            this.message = "Unknown Error";
        }

    }


    public void saveLog()
    {
        //save it to the local database;


        //then fire it up to firebase;

    }

    public void updateDiary(String year)
    {
        this.db = new ScriptureDBHandler(this.context);

        //prepare the payment status
        String status = "";

        if(this.success == true)
        {
            status = "TRUE";
        }
        else
        {
            status = "FALSE";
        }

        //try creating the database
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //open the database
        db.openDataBase();

        //check to see if the record exists
        String query = "SELECT * FROM `diary_payments` WHERE `year` = '" + year + "'";
        Log.d("PCCAPP", query);

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

        //close the database connection
        this.db.close();
    }

    private void insertDiary(String year, String status)
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("status", status);

        String table = "diary_payments";

        this.db.myDataBase.insert(table, null, values);

        Log.d("PCCAPP", "ACTIVATION INSERTED");
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
        Log.d("PCCAPP", "updating Activation");
    }

    @Override
    public String toString() {
        return "MomoResult{" +
                "receiverNumber='" + receiverNumber + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", amount='" + amount + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
