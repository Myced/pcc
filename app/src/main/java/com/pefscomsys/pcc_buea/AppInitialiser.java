package com.pefscomsys.pcc_buea;

import android.content.Context;

public class AppInitialiser
{
    private Context context;

    public AppInitialiser(Context context)
    {
        this.context = context;
    }

    public boolean isFirstRun()
    {
        return false;
    }

    
}
