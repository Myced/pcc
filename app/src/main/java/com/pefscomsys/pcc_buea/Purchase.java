package com.pefscomsys.pcc_buea;

class Purchase
{
    private int id;
    private String name;
    private String code;
    private String year;
    private String date;

    //constants to represent each element in the database
    public static final String DIARY = "DIARY";
    public static final String BOOK = "BOOK";
    public static final String HYMN = "HYMN";

    public static final String TABLE_NAME = "purchases";

    public Purchase()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
