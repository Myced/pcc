package com.pefscomsys.pcc_buea;

public class ChurchAppCalendar
{
    private String id;
    private String row;

    public ChurchAppCalendar()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "ChurchAppCalendar{" +
                "id=" + id +
                ", row='" + row + '\'' +
                '}';
    }
}
