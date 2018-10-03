package com.pefscomsys.pcc_buea;

public class FScripture
{
    private  String date;
    private String day;
    private String month;
    private String psalms;
    private String readingOne;
    private String readingText;
    private String readingTwo;
    private String year;

    public FScripture()
    {

    }

    public FScripture(String date, String day, String month, String psalms, String readingOne, String readingText, String readingTwo, String year) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.psalms = psalms;
        this.readingOne = readingOne;
        this.readingText = readingText;
        this.readingTwo = readingTwo;
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPsalms() {
        return psalms;
    }

    public void setPsalms(String psalms) {
        this.psalms = psalms;
    }

    public String getReadingOne() {
        return readingOne;
    }

    public void setReadingOne(String readingOne) {
        this.readingOne = readingOne;
    }

    public String getReadingText() {
        return readingText;
    }

    public void setReadingText(String readingText) {
        this.readingText = readingText;
    }

    public String getReadingTwo() {
        return readingTwo;
    }

    public void setReadingTwo(String readingTwo) {
        this.readingTwo = readingTwo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
