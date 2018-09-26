package com.pefscomsys.pcc_buea;

public class CompoundScriptureHandler
{
    private String  scripture;

    private String finalResult = "";

    public CompoundScriptureHandler(String scripture)
    {
        this.scripture = scripture;

        //this class is only called for compound scriptures

    }

    public String getFinalResult()
    {
        return this.finalResult;
    }
}
