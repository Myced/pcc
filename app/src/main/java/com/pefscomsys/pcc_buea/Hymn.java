package com.pefscomsys.pcc_buea;

public class Hymn {
    private CharSequence Hymn;
    private int hymnNumber;

    public Hymn(CharSequence hymn, int number) {
        this.setHymn(hymn);
        this.setHymnNumber(number);
    }

    public CharSequence getHymn() {
        return Hymn;
    }

    public void setHymn(CharSequence hymn) {
        Hymn = hymn;
    }

    public int getHymnNumber() {
        return hymnNumber;
    }

    public void setHymnNumber(int hymnNumber) {
        this.hymnNumber = hymnNumber;
    }
}
