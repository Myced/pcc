package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChurchCalendar extends AppCompatActivity {

    List<ChurchAppCalendar> holidays;
    private Context context;
    private RecyclerView publicHodidaysView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_calendar);

        setTitle(R.string.church_calender);

        this.holidays = this.getHolidays();

        //now get the view id
        publicHodidaysView = findViewById(R.id.public_holidays_view);

        //now create an adapter for that
        LinearLayoutManager publicHolidayManager = new LinearLayoutManager(this.context);

        PublicHolidayAdapter publicHolidayAdapter = new PublicHolidayAdapter(this.holidays);

        publicHodidaysView.setLayoutManager(publicHolidayManager);
        publicHodidaysView.setAdapter(publicHolidayAdapter);

    }

    private List<ChurchAppCalendar> getHolidays()
    {
        List<ChurchAppCalendar> holidays = new ArrayList<>();

        ChurchAppCalendar holiday;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `public_holidays` ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            holiday = new ChurchAppCalendar();
            holiday.setId(result.getString(0));
            holiday.setRow(result.getString(1));

            holidays.add(holiday);
            Log.d("PCCAPP", holiday.toString());
        }

        result.close();
        db.close();

        return holidays;
    }
}
