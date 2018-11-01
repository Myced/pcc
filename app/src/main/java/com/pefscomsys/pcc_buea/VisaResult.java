package com.pefscomsys.pcc_buea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author tnced  <tncedric@yahoo.com>
 * @
 */
public class VisaResult
{
    private JSONObject response;

    private String transactionID;
    private int amount;
    private int created_at;
    private String description;

    private Context context;

    //database
    ScriptureDBHandler db;

    //firebase specifitcs
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public VisaResult(JSONObject object, Context context)
    {
        this.response = object;
        this.context = context;

        //set the other values
        this.setAmount();
        this.setCreated_at();
        this.setDescription();
        this.setTransactionID();
        this.saveLog();
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID() {

        String transactionID = "";

        try {
            transactionID = this.response.getString("id");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        this.transactionID = transactionID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount() {

        int amount = 0;

        try
        {
            amount = this.response.getInt("amount");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        this.amount = amount;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {

        int created_at = 0;

        try {
            created_at = this.response.getInt("created");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        String description = "";

        try {
            description = this.response.getString("description");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        this.description = description;
    }

    public void saveDiary(String year)
    {
        this.db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        //perform other actions here.
        if(this.checkYear(year) == true)
        {
            //update
            this.updateYear(year);
        }
        else {
            //insert
            this.insertYear(year);
        }

    }

    private boolean checkYear(String year)
    {
        String sql = "SELECT * FROM `diary_payments` WHERE `year` = '" + year + "'";

        Cursor result = this.db.myDataBase.rawQuery(sql, null);

        int count = result.getCount();

        if(count == 0)
            return false;

        return true;
    }

    private void insertYear(String year)
    {
        //create params
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("status", "TRUE");

        String table = "diary_payments";

        this.db.myDataBase.insert(table, null, values);
    }

    private void updateYear(String year)
    {
        //creat a values object
        ContentValues values = new ContentValues();
        values.put("year", year);
        values.put("status", "TRUE");

        String where = "year = '" + year + "'";
        String[] whereArgs = {};

        String table = "diary_payments";

        this.db.myDataBase.update(table, values, where, whereArgs);
    }

    private void saveLog()
    {
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = null;
        if (mUser != null) {
            uid = mUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            try {
                mDatabase.child("Transactions").child(""+response.getString("TransactionID")).setValue(getObject(response, context));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static VisaResult getObject (JSONObject myObject, Context context)
    {
        VisaResult result = new VisaResult(myObject, context);

       return result;
    }
}
