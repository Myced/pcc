package com.pefscomsys.pcc_buea;

public class SundaySchool
{
    private String month;
    private String date;
    private String event;
    private String ln;
    private String studyTitle;
    private String text;
    private String prayer;

    public SundaySchool() {

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getStudyTitle() {
        return studyTitle;
    }

    public void setStudyTitle(String studyTitle) {
        this.studyTitle = studyTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrayer() {
        return prayer;
    }

    public void setPrayer(String prayer) {
        this.prayer = prayer;
    }

    @Override
    public String toString() {
        return "SundaySchool{" +
                "month='" + month + '\'' +
                ", date='" + date + '\'' +
                ", event='" + event + '\'' +
                ", ln='" + ln + '\'' +
                ", studyTitle='" + studyTitle + '\'' +
                ", text='" + text + '\'' +
                ", prayer='" + prayer + '\'' +
                '}';
    }
}
