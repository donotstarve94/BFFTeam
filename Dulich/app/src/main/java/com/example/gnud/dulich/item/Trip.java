package com.example.gnud.dulich.item;

/**
 * Created by GNUD on 15/08/2016.
 */
public class Trip {
    private int id;
    private String location;
    private String datemonth;
    private String things;

    public Trip(){

    }

    public Trip(int id, String location, String datemonth, String things) {
        this.id = id;
        this.location = location;
        this.datemonth = datemonth;
        this.things = things;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDatemonth() {
        return datemonth;
    }

    public void setDatemonth(String datemonth) {
        this.datemonth = datemonth;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }
}
