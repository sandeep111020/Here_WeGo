package com.example.herewego;

public class junctionmodel {
    String lat,lon,name,number;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public junctionmodel(String lat,String lon,String name,String number){
        this.lat=lat;
        this.lon=lon;
        this.name=name;
        this.number=number;
    }
}
