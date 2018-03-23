package com.example.lewho.projectappmobile;

/**
 * Created by p1601248 on 23/03/2018.
 */

public class Position {
    private float lat;
    private float lng;

    public Position(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }
}
