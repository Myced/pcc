package com.pefscomsys.pcc_buea;

public class PastorList
{
    private String id;
    private String name;
    private String count;

    public PastorList() {
    }

    public PastorList(String id, String name, String count) {
        this();
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
