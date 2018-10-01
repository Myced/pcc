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
        if(this.isFirstRun() == true)
        {
            //then initilise the app
            this.startProgressBar();

            this.copyDatabase();

            this.updateFirstRunStatus(false);

            this.endProgressBar();
        }
    }

    public void copyDatabase()
    {
        Log.d("PCCAPP", "copying database");
        ScriptureDBHandler myDb = new ScriptureDBHandler(this.context);

        //now create the database
        try {
            myDb.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //database has been created
    }

    public void updateFirstRunStatus(boolean status)
    {
        Log.d("PCCAPP", "Updating shared pref status to false");

        SharedPreferences mStatus = context.getSharedPreferences("FIRST_RUN", context.MODE_PRIVATE);
        SharedPreferences.Editor mStatusEditor = mStatus.edit();

        mStatusEditor.putString("FIRST_RUN", "TRUE");

        mStatusEditor.apply();
    }

    public boolean isFirstRun()
    {
        //check shared preferences for FIRST_RUN
        SharedPreferences mStatusCheck = context.getSharedPreferences("FIRST_RUN", this.context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mStatusCheck.edit();
        edit.apply();

        if(mStatusCheck.getAll().size() == 0)
        {
            mStatusCheck.edit().putString("FIRST_RUN", "TRUE").apply();
        }

        if(mStatusCheck.getString("FIRST_RUN", "TRUE").equals("TRUE"))
        {
            //this is the first run of the app/
            //initialise it
            Log.d("PCCAPP", "Fist run true");
            return true;
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
