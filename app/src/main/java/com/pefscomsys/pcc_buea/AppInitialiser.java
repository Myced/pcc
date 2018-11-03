package com.pefscomsys.pcc_buea;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

public class AppInitialiser
{
    private Context context;
    private ProgressDialog dialog;

    public AppInitialiser(Context context)
    {
        this.context = context;
    }

    public void initialiseApp()
    {
        if(this.isFirstRun())
        {

            this.copyDatabase();

            this.updateFirstRunStatus();
        }
    }

    public void copyDatabase()
    {
        Log.d("PCCAPP", "copying database");
        ScriptureDBHandler myDb = new ScriptureDBHandler(context);

        //now create the database
        try {
            myDb.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //database has been created
        //try to open then close it back
        myDb.openDataBase();
        myDb.close();
    }

    public void updateFirstRunStatus()
    {
        Log.d("PCCAPP", "Updating shared pref status to false");

        SharedPreferences mStatus = context.getSharedPreferences("FIRST_RUN", Context.MODE_PRIVATE);
        SharedPreferences.Editor mStatusEditor = mStatus.edit();

        mStatusEditor.putString("FIRST_RUN", "FALSE");

        mStatusEditor.apply();
    }

    public boolean isFirstRun()
    {
        //check shared preferences for FIRST_RUN
        SharedPreferences mStatusCheck = context.getSharedPreferences("FIRST_RUN", Context.MODE_PRIVATE);
        Log.d("STATUS", "First run status: "+ mStatusCheck.getAll().toString());

        if(!mStatusCheck.contains("FIRST_RUN"))
        {
            //this is the first run of the app/
            //initialise it
            Log.d("PCCAPP", "Fist run true");
            return true;
        }

        if(mStatusCheck.getString("FIRST_RUN", "TRUE").equals("FALSE"))
        {
            return false;
        }
        return false;
    }

    public void startProgressBar()
    {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Initialising The Application");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void endProgressBar()
    {
        dialog.dismiss();
    }


}
