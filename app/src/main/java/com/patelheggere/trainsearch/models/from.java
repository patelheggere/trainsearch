package com.patelheggere.trainsearch.models;

/**
 * Created by Talkative Parents on 1/11/2018.
 */

public class from {

    private String arrival;
    private Long arrivalTimestamp;
    private String departure;
    private String platform;
    private Long departureTimestamp;
    private Prognosis prognosis;
    private Station station;
    private coordinate coordinate1;
    private Location location;



    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Long getArrivalTimestamp() {
        return arrivalTimestamp;
    }

    public void setArrivalTimestamp(Long arrivalTimestamp) {
        this.arrivalTimestamp = arrivalTimestamp;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getDepartureTimestamp() {
        return departureTimestamp;
    }

    public void setDepartureTimestamp(Long departureTimestamp) {
        this.departureTimestamp = departureTimestamp;
    }

    public Prognosis getPrognosis() {
        return prognosis;
    }

    public void setPrognosis(Prognosis prognosis) {
        this.prognosis = prognosis;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public coordinate getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(coordinate coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
