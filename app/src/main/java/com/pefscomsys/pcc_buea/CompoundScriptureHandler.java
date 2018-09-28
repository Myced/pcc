package com.pefscomsys.pcc_buea;

import android.content.Context;

public class CompoundScriptureHandler
{
    private String  scripture;

    private String book;
    private String chapter;

    private String firstReading;

    private String finalResult = "";
    private Context context;

    public CompoundScriptureHandler(String scripture, Context context)
    {
        this.scripture = scripture;
        this.context = context;

        //this class is only called for compound scriptures

    }

    public String getFinalResult()
    {
        return this.finalResult;
    }

    private boolean hasBook(String reading)
    {
        return false;
    }

    private boolean hasChapter(String reading)
    {
        return true;
    }

    private String prepareTextFromBookAndChapter(String chapters)
    {

        return null;
    }
}
