package com.pefscomsys.pcc_buea;

import java.io.IOException;
import java.text.DecimalFormat;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class ScriptureTextHandler
{
    //declare public variables
    private String text;
    private Context context;
    private String reading;
    private String book;
    private String chapter;
    private String verse;
    private double start;
    private double end;

    private boolean hasNoVerses = false;

    private int verseStart;
    private int verseEnd;

    final private int VERSE_INDEX = 3;

    //the result of the reading
    private String result = "";

    public ScriptureTextHandler(String text, Context context)
    {
        this.text = text;
        this.context = context;
    }

    public String getReading()
    {
        String text = this.text;

        //now split it up and start working
        String[] parts = text.split("\\.");

        //now the first part is the book
        String book = parts[0];

        this.book = book;
        this.book.replace(" ", "");

        //now get the ret
        String content  = parts[1];

        this.setChapter(content);
        String verses = this.verse;

        String verseStart = "";
        String verseEnd = "";

        //now check if the reading has a starting and ending verse.
        if(this.hasNoVerses)
        {
            verseStart = "0";
            verseEnd = "0";
        }
        else
        {
            if(verses.contains("-"))
            {
                //split and get them
                String[] verseContent  =  verses.split("\\-");

                verseStart = verseContent[0];
                verseEnd = verseContent[1];

            }
            else
            {
                //starting and ending verses are the same
                verseStart = verses;
                verseEnd = verseStart;
            }
        }

        //now set them globally
        String verseStartString = verseStart.replace(" ", "");
        String verseEndString = verseEnd.replace(" ", "");

        //replace all other characters
//        Example  12a becomes 12
        String start = verseStartString.replaceFirst("[a-z]+", "");
        String end = verseEndString.replaceFirst("[a-z]+", "");


        this.verseStart = Integer.valueOf(start);
        this.verseEnd = Integer.valueOf(end);

        //now that we have chapter start and end. lets prepare them as real for
        // database query
        this.prepareStartEnd(Integer.toString(this.verseStart), Integer.toString(this.verseEnd));
        this.query();

        //return the result
        return this.result;
    }

    private void setChapter(String content)
    {
        if(content.contains(":"))
        {
            String[] parts = content.split("\\:");

            String chapter = parts[0];

            this.chapter = chapter;

            String verses = parts[1];

            this.verse = verses;
        }
        else
        {
            //there are no verses. Its the whole chapter
            this.hasNoVerses = true;

            this.chapter = content.replace(" ", "");
            this.verse = "";

        }

    }

    private String formatVerse(String verse)
    {
        String result = "";

        if(verse.length() == 1)
        {
            result = "00" + verse;
        }
        else if(verse.length() == 2)
        {
            result = "0" + verse;
        }
        else if(verse.length() == 3)
        {
            result = verse;
        }
        else
        {
            result = verse;
        }


        return result;
    }

    private void prepareStartEnd(String start, String end) {
        //preparet start and end to contain the chapter
        String theStart = this.chapter + "." + formatVerse(start.replace(" ", ""));
        String theEnd = "";

        if (this.hasNoVerses) {
            int chapter = Integer.valueOf(this.chapter);

            int chapterEnd = ++chapter;

            theEnd  = chapterEnd + "." + formatVerse(end.replace(" ", ""));
        }
        else
        {
            theEnd  = this.chapter + "." + formatVerse(end.replace(" ", ""));
        }

        //we have to format the numbers to 3 decimall places
        DecimalFormat df = new DecimalFormat("#.000"); //not needed for now

        //conver them to floats
        double startValue = Double.valueOf(theStart);
        double endValue = Double.valueOf(theEnd);



        this.start = startValue;
        this.end = endValue;
    }

    private void query()
    {
        String result = this.result;

        int startOfVerse = this.verseStart;

        if(startOfVerse == 0)
            ++startOfVerse;

        //create a connection to the database
        ScriptureDBHandler db = new ScriptureDBHandler(this.context);

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //open the database connection
        try
        {
            db.openDataBase();
        }
        catch (SQLException e)
        {
            Log.d("Database", e.getMessage());
        }


        //just do a normal query and see
        String query = "SELECT * FROM `verses` WHERE `verse` >= '" + this.start +
                "'  AND `verse` <= '" + this.end + "' AND `book` = '" + this.book.replace(" ", "") + "' ";

        //do the query
        Cursor scriptureResult = db.myDataBase.rawQuery(query, null);

        //now loop throught the results and log them
        while(scriptureResult.moveToNext())
        {

            result = result + Integer.toString(startOfVerse) + " " + scriptureResult.getString(VERSE_INDEX);
            result = result + "\n\n";

            //incremtnt the verse
            startOfVerse++;
        }

        this.result = result;

        //close the cursor
        scriptureResult.close();

        //close the connection
        db.close();

    }

    public String getBook()
    {
        return this.book;
    }

    public String getChapter()
    {
        return this.chapter;
    }
}
