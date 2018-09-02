package com.pefscomsys.pcc_buea;

public class Hymn {
    private String Hymn;
    private int hymnNumber;

    public Hymn(String hymn, int number) {
        this.setHymn(hymn);
        this.setHymnNumber(number);
    }

    public String getHymn() {
        return Hymn;
    }

    public void setHymn(String hymn) {
        Hymn = hymn;
    }

    public int getHymnNumber() {
        return hymnNumber;
    }

    public void setHymnNumber(int hymnNumber) {
        this.hymnNumber = hymnNumber;
    }
}
