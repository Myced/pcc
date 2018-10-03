package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChurchCalendar extends AppCompatActivity {

    List<ChurchAppCalendar> holidays;
    List<ImportantDay> importantDays;
    List<SpecialCollection> specialCollections;

    private Context context;
    private RecyclerView publicHodidaysView, importantDaysView, specialCollectionsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_calendar);

        setTitle(R.string.church_calender);

        this.holidays = this.getHolidays();
        this.importantDays = this.getImportantDays();
        this.specialCollections = this.getSpecialCollections();


        //now get the view id
        publicHodidaysView = findViewById(R.id.public_holidays_view);
        publicHodidaysView.setHasFixedSize(true);

        //now create an adapter for that
        LinearLayoutManager publicHolidayManager = new LinearLayoutManager(this.context);

        PublicHolidayAdapter publicHolidayAdapter = new PublicHolidayAdapter(this.holidays);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);

        publicHodidaysView.setLayoutManager(publicHolidayManager);
        publicHodidaysView.setAdapter(publicHolidayAdapter);
        publicHodidaysView.addItemDecoration(itemDecoration);

        //now for the important days collection
        importantDaysView = findViewById(R.id.important_days_view);
        importantDaysView.setHasFixedSize(true);

        LinearLayoutManager importantDaysLayoutManager = new LinearLayoutManager(this.context);

        ImportantDayAdapter importantDayAdapter = new ImportantDayAdapter(this.getImportantDays());

        importantDaysView.setLayoutManager(importantDaysLayoutManager);
        importantDaysView.setAdapter(importantDayAdapter);
        importantDaysView.addItemDecoration(itemDecoration);

        specialCollectionsView = findViewById(R.id.special_collections_view);
        specialCollectionsView.setHasFixedSize(true);
        LinearLayoutManager specialCollectionLayuutManager = new LinearLayoutManager(context);

        SpecialCollectionAdapter specialCollectionAdapter = new SpecialCollectionAdapter(this.getSpecialCollections());

        specialCollectionsView.setLayoutManager(specialCollectionLayuutManager);
        specialCollectionsView.setAdapter(specialCollectionAdapter);
        specialCollectionsView.addItemDecoration(itemDecoration);

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

    private List<SpecialCollection> getSpecialCollections()
    {
        List<SpecialCollection> specialCollections = new ArrayList<>();

        SpecialCollection collection;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `special_collections` ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            collection = new SpecialCollection();

            collection.setCollection(result.getString(1));

            specialCollections.add(collection);
        }

        result.close();
        db.close();

        return specialCollections;
    }

    private List<ImportantDay> getImportantDays()
    {
        List<ImportantDay> importantDays = new ArrayList<>();

        ImportantDay day ;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `important_days` ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            day = new ImportantDay();

            day.setDay(result.getString(1));

            importantDays.add(day);
        }

        result.close();
        db.close();

        return importantDays;
    }
}
