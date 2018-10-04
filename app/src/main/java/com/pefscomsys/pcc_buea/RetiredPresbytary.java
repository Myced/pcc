package com.pefscomsys.pcc_buea;

public class RetiredPresbytary
{
    private String presbytary;
    private String id;

    public RetiredPresbytary() {
    }

    public RetiredPresbytary(String presbytary, String id) {
        this();
        this.presbytary = presbytary;
        this.id = id;
    }

    public String getPresbytary() {
        return presbytary;
    }

    public void setPresbytary(String presbytary) {
        this.presbytary = presbytary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RetiredPresbytary{" +
                "presbytary='" + presbytary + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
