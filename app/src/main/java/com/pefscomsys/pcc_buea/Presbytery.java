package com.pefscomsys.pcc_buea;

public class Presbytery
{
    private String name;
    private String presbytery;
    private String secretery;
    private String secTel;
    private String treasurer;
    private String treasurerTel;
    private String chair;
    private String chairTel;
    private String cong;

    public Presbytery()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresbytery() {
        return presbytery;
    }

    public void setPresbytery(String presbytery) {
        this.presbytery = presbytery;
    }

    public String getSecretery() {
        return secretery;
    }

    public void setSecretery(String secretery) {
        this.secretery = secretery;
    }

    public String getSecTel() {
        return secTel;
    }

    public void setSecTel(String secTel) {
        this.secTel = secTel;
    }

    public String getTreasurer() {
        return treasurer;
    }

    public void setTreasurer(String treasurer) {
        this.treasurer = treasurer;
    }

    public String getTreasurerTel() {
        return treasurerTel;
    }

    public void setTreasurerTel(String treasurerTel) {
        this.treasurerTel = treasurerTel;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getChairTel() {
        return chairTel;
    }

    public void setChairTel(String chairTel) {
        this.chairTel = chairTel;
    }

    public String getCong() {
        return cong;
    }

    public void setCong(String cong) {
        this.cong = cong;
    }

    @Override
    public String toString() {
        return "Presbytery{" +
                "name='" + name + '\'' +
                ", presbytery='" + presbytery + '\'' +
                ", secretery='" + secretery + '\'' +
                ", secTel='" + secTel + '\'' +
                ", treasurer='" + treasurer + '\'' +
                ", treasurerTel='" + treasurerTel + '\'' +
                ", chair='" + chair + '\'' +
                ", chairTel='" + chairTel + '\'' +
                ", cong='" + cong + '\'' +
                '}';
    }
}
