package com.pefscomsys.pcc_buea;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.pefscomsys.pcc_buea.MainActivity.PAYMENT_PREFS;

public class MomoResult
{
    public String receiverNumber;
    public String statusCode;
    public String amount;
    public String transactionID;
    public String processingNumber;

    public String getProcessingNumber() {
        return processingNumber;
    }

    public String resultString;

    public Context context;
    private ScriptureDBHandler db;

    private JSONObject response;

    public boolean success;
    public String message; // will contain the message for the result
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public MomoResult(){

    }

    public MomoResult(JSONObject object)
    {
        this.response = object;

        try {
            receiverNumber = object.getString("ReceiverNumber");
            amount = object.getString("Amount");
            statusCode = object.getString("StatusCode");
            transactionID = object.getString("TransactionID");
            processingNumber = object.getString("ProcessingNumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        resultString = object.toString();

        //now process the result
		if(statusCode.equals("515")
		{
			this.success = false;
			this.message = "This number does not have a mobile money account";
		}
        else if(statusCode.equals("529"))
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

        this.saveLog();

    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransactionID() {
        return transactionID;
    }


    public static MomoResult fromJson(JSONObject object){
        MomoResult momoResult = new MomoResult();
        try {
            momoResult.receiverNumber = object.getString("ReceiverNumber");
            momoResult.amount = object.getString("Amount");
            momoResult.statusCode = object.getString("StatusCode");
            momoResult.transactionID = object.getString("TransactionID");
            momoResult.processingNumber = object.getString("ProcessingNumber");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return momoResult;
    }

    public void saveLog(){
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = null;
        if (mUser != null) {
            uid = mUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            try {
                Log.d("TransactionID", String.valueOf(fromJson(response)));
                mDatabase.child("Transactions").child(""+response.getString("TransactionID")).setValue(fromJson(response));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //save it to the local database;

        //then fire it up to firebase;


    }

    public void updateDiary(String year)
    {
        this.db = new ScriptureDBHandler(this.context);

        //prepare the payment status
        String status = "";

        if(this.success)
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
