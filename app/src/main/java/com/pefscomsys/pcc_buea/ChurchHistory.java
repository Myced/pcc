package com.pefscomsys.pcc_buea;

public class ChurchHistory
{
    private String name;
    private String chairPerson;
    private String pobox;
    private String address;
    private String tel;
    private String email;

    public ChurchHistory()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChairPerson() {
        return chairPerson;
    }

    public void setChairPerson(String chairPerson) {
        this.chairPerson = chairPerson;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ChurchHistory{" +
                "name='" + name + '\'' +
                ", chairPerson='" + chairPerson + '\'' +
                ", pobox='" + pobox + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
