package com.patelheggere.trainsearch.models;

/**
 * Created by Talkative Parents on 1/11/2018.
 */

public class Prognosis {private String arrival;
    private String platform;
    private String departure;
    private String capacity2nd;
    private String capacity1st;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getCapacity2nd() {
        return capacity2nd;
    }

    public void setCapacity2nd(String capacity2nd) {
        this.capacity2nd = capacity2nd;
    }

    public String getCapacity1st() {
        return capacity1st;
    }

    public void setCapacity1st(String capacity1st) {
        this.capacity1st = capacity1st;
    }
}