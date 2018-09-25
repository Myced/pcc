package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HealthSecretary
{
    private String mName;
    private String mAddress;
    private String mTel;
    private String mCell;
    private String mEmail;
    private Context context;
    private ScriptureDBHandler mDatabase;

    List<HealthInstitution>  institutions;


    private final int NAME_INDEX = 1;
    private final int ADDRESS_INDEX = 2;
    private final int TEL_INDEX = 3;
    private final int CELL_INDEX = 4;
    private final int EMAIL_INDEX = 5;

    private final int INST_NAME = 1;
    private final int INST_DOCTOR = 2;
    private final int INST_POBOX = 3;
    private final int INST_ADDRESS = 4;
    private final int INST_TEL = 5;


    public HealthSecretary(Context context)
    {
        this.context = context;
        mDatabase = new ScriptureDBHandler(context);

        //initialise the database
        try {
            mDatabase.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PCC", "creating database");
        }


        //initilise the results
        this.init();
    }

    public void init()
    {
        //do a database query to get the results
        String query = "SELECT * FROM `healt_sec` WHERE `id` = '1' ";

        //do the query
        mDatabase.openDataBase();
        Cursor result = mDatabase.myDataBase.rawQuery(query, null);

        //now loop throught the results and save them
        while(result.moveToNext())
        {
            //save the results
            this.mName = result.getString(NAME_INDEX);
            this.mAddress = result.getString(ADDRESS_INDEX);
            this.mCell = "Cell: " + result.getString(CELL_INDEX);
            this.mTel = "Tel: " + result.getString(TEL_INDEX);
            this.mEmail =  result.getString(EMAIL_INDEX);
            Log.d("PCC", "loopign through results");
        }

        result.close();

        mDatabase.close();

    }

    public List<HealthInstitution> getHealthInstitutions()
    {
        //save the institutions too
        List<HealthInstitution> institutionss   = new ArrayList<HealthInstitution>();

        //create an instaance of health insitution
        HealthInstitution inst;

        String query = "SELECT  * FROM `hospitals` ";

        mDatabase.openDataBase();


        Cursor result = mDatabase.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            inst = new HealthInstitution();

            inst.setAddress(result.getString(INST_ADDRESS));
            inst.setDoctor(result.getString(INST_DOCTOR));
            inst.setName(result.getString(INST_NAME));
            inst.setPobox(result.getString(INST_POBOX));
            inst.setTel(result.getString(INST_TEL));

            Log.d("PCCAPP", " Adding " + inst.toString());

            institutionss.add(inst);
        }

        result.close();

        mDatabase.close();

        return institutionss;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmTel() {
        return mTel;
    }

    public void setmTel(String mTel) {
        this.mTel = mTel;
    }

    public String getmCell() {
        return mCell;
    }

    public void setmCell(String mCell) {
        this.mCell = mCell;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
