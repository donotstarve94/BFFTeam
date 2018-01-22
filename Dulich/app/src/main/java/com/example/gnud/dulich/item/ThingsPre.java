package com.example.gnud.dulich.item;

/**
 * Created by GNUD on 19/08/2016.
 */
public class ThingsPre {
    private String id;
    private String things;

    public ThingsPre(){

    }

    public ThingsPre(String id, String things) {
        this.id = id;
        this.things = things;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThings() {
        return things;
    }

    public void setThings(String things) {
        this.things = things;
    }
}
