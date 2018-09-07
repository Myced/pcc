package com.pefscomsys.pcc_buea;

public class Scripture
{
    private String psalms;
    private String readingOne;
    private String readingTwo;
    private String readingText;
    public String date;


    public Scripture(String psalms, String readingOne, String readingTwo, String readingText, String date) {
        this.psalms = psalms;
        this.readingOne = readingOne;
        this.readingTwo = readingTwo;
        this.readingText = readingText;
        this.date = date;
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

    public String getReadingTwo() {
        return readingTwo;
    }

    public void setReadingTwo(String readingTwo) {
        this.readingTwo = readingTwo;
    }

    public String getReadingText() {
        return readingText;
    }

    public void setReadingText(String readingText) {
        this.readingText = readingText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
