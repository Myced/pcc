package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchoolProcessor
{
    private Context context;
    
    private final int SCHOOL_NAME_INDEX = 1;
    private final int SCHOOL_PRINCIPAL_INDEX = 2;
    private final int SCHOOL_POBOX = 3;
    private final int SCHOOL_ADDRESS = 4;
    private final int SCHOOL_TEL = 5;
    private final int SCHOOL_EMAIL = 6;

    private final int ADDRESS_NAME = 1;
    private final int ADDRESS_CHAIR = 2;
    private final int ADDRESS_CO = 3;
    private final int ADDRESS_POBOX = 4;
    private final int ADDRESS_ADDRESS = 5;
    private final int ADDRESS_TEL = 6;
    private final int ADDRESS_EMAIL = 7;
    

    public SchoolProcessor(Context context)
    {
        this.context = context;
    }

    public List<School> getSchools()
    {
        List<School> schools = new ArrayList<>();

        School currentSchool;

        ScriptureDBHandler database = new ScriptureDBHandler(this.context);

        try {
            database.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = "SELECT  * FROM `schools` ";

        database.openDataBase();


        Cursor result = database.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            currentSchool = new School();

            currentSchool.setAddress(result.getString(SCHOOL_ADDRESS));
            currentSchool.setPrincipal(result.getString(SCHOOL_PRINCIPAL_INDEX));
            currentSchool.setName(result.getString(SCHOOL_NAME_INDEX));
            currentSchool.setPobox(result.getString(SCHOOL_POBOX));
            currentSchool.setTel(result.getString(SCHOOL_TEL));
            currentSchool.setEmail(result.getString(SCHOOL_EMAIL));


            schools.add(currentSchool);
        }

        result.close();

        database.close();


        return schools;
    }

    public List<ChurchAddress> getChurchAddresses()
    {
        List<ChurchAddress> addresses  = new ArrayList<>();

        ChurchAddress currentAddress;

        ScriptureDBHandler database = new ScriptureDBHandler(this.context);

        try {
            database.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = "SELECT  * FROM `church_address` ";

        database.openDataBase();


        Cursor result = database.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            currentAddress = new ChurchAddress();

            currentAddress.setName(result.getString(ADDRESS_NAME));
            currentAddress.setChair(result.getString(ADDRESS_CHAIR));
            currentAddress.setCo(result.getString(ADDRESS_CO));
            currentAddress.setPobox(result.getString(ADDRESS_POBOX));
            currentAddress.setAddress(result.getString(ADDRESS_ADDRESS));
            currentAddress.setTel(result.getString(ADDRESS_TEL));
            currentAddress.setEmail(result.getString(ADDRESS_EMAIL));

            addresses.add(currentAddress);
        }

        result.close();

        database.close();

        return addresses;
    }
}
