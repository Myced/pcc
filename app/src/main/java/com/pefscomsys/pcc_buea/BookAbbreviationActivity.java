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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookAbbreviationActivity extends AppCompatActivity {

    private List<BookAbreviation> books;
    private Context context;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_abbreviation);
        setTitle(R.string.book_and_abbreviation);

        //prepare data
        this.context = getApplicationContext();
        this.getData();
        this.table = findViewById(R.id.book_abbrev);

        //prepare headings here
        TableRow headingRow = new TableRow(context);

        TextView countHeading = prepareHead(new TextView(context), "No");
        TextView abbrevHeading = prepareHead(new TextView(context), "Abbreviation");
        TextView bookHeading = prepareHead(new TextView(context), "Book");

        headingRow.addView(countHeading);
        headingRow.addView(abbrevHeading);
        headingRow.addView(bookHeading);

        table.addView(headingRow);

        for(int i = 0; i < books.size(); i++)
        {
            TableRow bodyRow = new TableRow(context);

            TextView countBody = prepareBody(new TextView(this.context), Integer.toString(i + 1));
            TextView abbrevBody = prepareBody(new TextView(context), books.get(i).getAbbreviation());
            TextView bookBody = prepareBody(new TextView(context), books.get(i).getBook());

            bodyRow.addView(countBody);
            bodyRow.addView(abbrevBody);
            bodyRow.addView(bookBody);

            table.addView(bodyRow);
        }

    }

    private void getData()
    {
        List<BookAbreviation> books = new ArrayList<>();

        BookAbreviation abbreviation;

        //query the database;
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.openDataBase();

        String query = "SELECT * FROM `books`  ";

        Cursor result = db.myDataBase.rawQuery(query, null);

        while(result.moveToNext())
        {
            abbreviation = new BookAbreviation();

            abbreviation.setAbbreviation(result.getString(1));
            abbreviation.setBook(result.getString(2));

            books.add(abbreviation);
        }

        result.close();
        db.close();

        this.books = books;
    }

    private TextView prepareHead(TextView view, String text)
    {
        int headingColor = Color.BLACK;
        int headingPadding = 30;
        float headingTextSize = 20;

        view.setText(text);
        view.setTextSize(headingTextSize);
        view.setTextColor(headingColor);
        view.setTypeface(null, Typeface.BOLD);
        view.setPadding(headingPadding, headingPadding, headingPadding, headingPadding);
        view.setBackgroundResource(R.drawable.border_square);

        return view;
    }

    private TextView prepareBody(TextView view, String text)
    {
        int bodyColor = Color.BLACK;
        int bodyPadding = 25;
        float bodyTextSize = 16;

        view.setText(text);
        view.setTextColor(bodyColor);
        view.setTextSize(bodyTextSize);
        view.setPadding(bodyPadding, bodyPadding, bodyPadding, bodyPadding);
        view.setBackgroundResource(R.drawable.border_square);

        return view;
    }


}
