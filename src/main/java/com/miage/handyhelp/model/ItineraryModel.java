package com.miage.handyhelp.model;

public class ItineraryModel {
    private String departure;
    private String arrival;

    private String route_1="";

    private Boolean is_1_accessible;
    private String route_2="";

    public Boolean getIs_1_accessible() {
        return is_1_accessible;
    }

    public void setIs_1_accessible(Boolean is_1_accessible) {
        this.is_1_accessible = is_1_accessible;
    }

    public Boolean getIs_2_accessible() {
        return is_2_accessible;
    }

    public void setIs_2_accessible(Boolean is_2_accessible) {
        this.is_2_accessible = is_2_accessible;
    }

    private Boolean is_2_accessible;

    private String route_3="";

    public String getRoute_2() {
        return route_2;
    }

    public void setRoute_2(String route_2) {
        this.route_2 = route_2;
    }

    public String getRoute_3() {
        return route_3;
    }

    public void setRoute_3(String route_3) {
        this.route_3 = route_3;
    }

    public String getRoute_1() {
        return route_1;
    }

    public void setRoute_1(String route) {
        this.route_1 = route;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

}