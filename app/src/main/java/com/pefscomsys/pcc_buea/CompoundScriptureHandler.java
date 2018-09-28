package com.pefscomsys.pcc_buea;

import android.content.Context;
import android.util.Log;

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
        //so split the scripture into its individual parts
        String[] scriptures  = scripture.split("\\&");

        //now get the fist part.
        //Because it it complete
        String firstPart = scriptures[0];

        //now create an instance of the scripture text handler
        ScriptureTextHandler myScripture = new ScriptureTextHandler(firstPart, this.context);

        //now add the result to our final result
        this.finalResult += myScripture.getReading();

        //now I set my book and chapter
        this.book = myScripture.getBook();
        this.chapter = myScripture.getChapter();

        //now for the rest of the parts.
        //prepare them accordingly
        for(int i = 1; i < scriptures.length; i++)
        {
            String currentReading = scriptures[i];

            //now check for neccessary conditions and prepare the text
            if(this.hasBook(currentReading))
            {
                //this means that this is a complete scripture
                //hence we just create a text handler and get the reading
                ScriptureTextHandler currentText = new ScriptureTextHandler(currentReading, this.context);

                String theReading = currentText.getReading();

                //now append it to the final reading
                this.appendFinal(currentReading, theReading);
            }
            else
            {
                //has no book
                //therefore we will use our current book
                if(this.hasChapter(currentReading))
                {
                    //then prepare our text and get the reading
                    String text = this.prepareTextFromChapterAndVerse(currentReading);

                    //now create new scripture text handler
                    ScriptureTextHandler currentText = new ScriptureTextHandler(text, this.context);

                    String theReading = currentText.getReading();

                    //now add to the final result
                    this.appendFinal(currentReading, theReading);
                }
                else
                {
                    //has no chapter, only the verse
                    //then prepare our text and get the reading
                    String text = this.prepareTextFromVerse(currentReading);

                    //now create new scripture text handler
                    ScriptureTextHandler currentText = new ScriptureTextHandler(text, this.context);

                    String theReading = currentText.getReading();

                    //now add to the final result
                    this.appendFinal(currentReading, theReading);
                }
            }
        }

    }

    public String getFinalResult()
    {
        return this.finalResult;
    }

    private void setBook(String book)
    {
        this.book = book;
    }

    private void setChapter(String chapter)
    {
        this.chapter = chapter;
    }

    private boolean hasBook(String reading)
    {
        if(reading.contains("."))
        {
            return true;
        }
        else
            return false;
    }

    private boolean hasChapter(String reading)
    {
        if(reading.contains(":"))
        {
            return true;
        }
        return false;
    }

    private String prepareTextFromChapterAndVerse(String content)
    {
        String book = this.book;
        book += ".";

        return book + content;
    }

    private String prepareTextFromVerse(String content)
    {
        String book = this.book;
        book += ".";

        String chapter = this.chapter;
        chapter += ":";

        return book + chapter + content;
    }

    private void appendFinal(String reading, String content)
    {
        //now append it to the final reading
        this.finalResult += "\n\n";

        //add the current reading
        this.finalResult += reading;

        //now add the new reading
        this.finalResult += "\n\n";
        this.finalResult += content;

    }
}
