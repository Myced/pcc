package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SundaySchoolActivity extends AppCompatActivity {

    private List<SundaySchool> lessons;
    private Context context;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunday_school);
        setTitle(R.string.sunday_school);

        this.getData();
        this.context = getApplicationContext();
        table = findViewById(R.id.sunday_table);

        //get the table layout
        //setup table parameters
        int headingColor = Color.BLACK;
        int bodyColor = Color.BLACK;

        int headingPadding = 50;
        int bodyPadding = 40;

        float headingTextSize = 20;
        float bodyTextSize = 16;


        //prepare table heading
        TableRow heading = new TableRow(context);

        TextView dateHeading = new TextView(context);
        dateHeading.setText("Date");
        dateHeading.setTextSize(headingTextSize);
        dateHeading.setTextColor(headingColor);
        dateHeading.setTypeface(null, Typeface.BOLD);
        dateHeading.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        dateHeading.setBackgroundResource(R.drawable.border_square);

        TextView eventHeading = new TextView(context);
        eventHeading.setText("Event");
        eventHeading.setTextSize(headingTextSize);
        eventHeading.setTextColor(headingColor);
        eventHeading.setTypeface(null, Typeface.BOLD);
        eventHeading.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        eventHeading.setBackgroundResource(R.drawable.border_square);

        TextView lnHeading = new TextView(context);
        lnHeading.setText("LN");
        lnHeading.setTextSize(headingTextSize);
        lnHeading.setTextColor(headingColor);
        lnHeading.setTypeface(null, Typeface.BOLD);
        lnHeading.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        lnHeading.setBackgroundResource(R.drawable.border_square);

        TextView titleHeading = new TextView(context);
        titleHeading.setText("Bible Study Title");
        titleHeading.setTextSize(headingTextSize);
        titleHeading.setTextColor(headingColor);
        titleHeading.setTypeface(null, Typeface.BOLD);
        titleHeading.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        titleHeading.setBackgroundResource(R.drawable.border_square);

        TextView textHeading = new TextView(context);
        textHeading.setText("Text");
        textHeading.setTextSize(headingTextSize);
        textHeading.setTextColor(headingColor);
        textHeading.setTypeface(null, Typeface.BOLD);
        textHeading.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        textHeading.setBackgroundResource(R.drawable.border_square);

        TextView prayerHeading = new TextView(context);
        prayerHeading.setText("Prayer");
        prayerHeading.setTextSize(headingTextSize);
        prayerHeading.setTextColor(headingColor);
        prayerHeading.setTypeface(null, Typeface.BOLD);
        prayerHeading.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        prayerHeading.setBackgroundResource(R.drawable.border_square);

        //add the views to the row
        heading.addView(dateHeading);
        heading.addView(eventHeading);
        heading.addView(lnHeading);
        heading.addView(titleHeading);
        heading.addView(textHeading);
        heading.addView(prayerHeading);

        //now add the row to the
        table.addView(heading);

        //now loop through the rows and add them
        for (int i = 0; i < lessons.size(); i++)
        {
            SundaySchool currentLesson = lessons.get(i);

            TableRow body = new TableRow(context);

            TextView dateBody = new TextView(context);
            dateBody.setText(currentLesson.getDate().replaceAll("\\\\n", System.getProperty("line.separator")));
            TextView dateBodyFinal = prepareBody(dateBody);

            TextView eventBody = new TextView(context);
            eventBody.setText(currentLesson.getEvent().replaceAll("\\\\n", System.getProperty("line.separator")));
            TextView eventBodyFinal = prepareBody(eventBody);

            TextView lnBody = new TextView(context);
            lnBody.setText(currentLesson.getLn().replaceAll("\\\\n", System.getProperty("line.separator")));
            TextView lnBodyFinal  = this.prepareBody(lnBody);

            TextView titleBody = new TextView(context);
            titleBody.setText(currentLesson.getStudyTitle().replaceAll("\\\\n", System.getProperty("line.separator")));
            TextView titleBodyFinal = this.prepareBody(titleBody);

            TextView textBody  = new TextView(context);
            textBody.setText(currentLesson.getText().replaceAll("\\\\n", System.getProperty("line.separator")));
            TextView textBodyFinal = this.prepareBody(textBody);

            TextView prayerBody = new TextView(context);
            prayerBody.setText(currentLesson.getPrayer().replaceAll("\\\\n", System.getProperty("line.separator")));
            TextView prayerBodyFinal = this.prepareBody(prayerBody);

            //add them to the body row
            body.addView(dateBodyFinal);
            body.addView(eventBodyFinal);
            body.addView(lnBodyFinal);
            body.addView(titleBodyFinal);
            body.addView(textBodyFinal);
            body.addView(prayerBodyFinal);

            //add to the table
            table.addView(body);
        }

    }

    private TextView prepareBody(TextView textView)
    {
        //variables needed
        int headingColor = Color.BLACK;
        int bodyColor = Color.BLACK;

        int headingPadding = 50;
        int bodyPadding = 40;

        float headingTextSize = 20;
        float bodyTextSize = 16;

        textView.setTextSize(bodyTextSize);
        textView.setTextColor(bodyColor);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setPadding(bodyPadding, bodyPadding, bodyPadding, bodyPadding);
        textView.setBackgroundResource(R.drawable.border_square);

        return textView;
    }

    public void getData()
    {
        List<SundaySchool> sundaySchools = new ArrayList<>();

        SundaySchool sundaySchool;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `sunday_school_lessons`  ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            sundaySchool = new SundaySchool();

            sundaySchool.setDate(result.getString(2));
            sundaySchool.setEvent(result.getString(3));
            sundaySchool.setLn(result.getString(4));
            sundaySchool.setStudyTitle(result.getString(5));
            sundaySchool.setText(result.getString(6));
            sundaySchool.setPrayer(result.getString(7));

            sundaySchools.add(sundaySchool);
        }

        result.close();
        db.close();

        lessons = sundaySchools;
    }
}
