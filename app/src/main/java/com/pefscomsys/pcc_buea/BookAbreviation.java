package com.pefscomsys.pcc_buea;

public class BookAbreviation
{
    private String abbreviation;
    private String book;

    public BookAbreviation() {
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookAbreviation{" +
                "abbreviation='" + abbreviation + '\'' +
                ", book='" + book + '\'' +
                '}';
    }
}
