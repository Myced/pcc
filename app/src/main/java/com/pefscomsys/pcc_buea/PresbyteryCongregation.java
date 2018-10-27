package com.pefscomsys.pcc_buea;

public class PresbyteryCongregation
{
    private String station;
    private String worker;
    private String tel;

    public PresbyteryCongregation() {
    }

    public PresbyteryCongregation(String station, String worker, String tel) {
        this();
        this.station = station;
        this.worker = worker;
        this.tel = tel;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return "PresbyteryCongregation{" +
                "station='" + station + '\'' +
                ", worker='" + worker + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
