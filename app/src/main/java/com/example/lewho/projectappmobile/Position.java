package com.example.lewho.projectappmobile;

import java.io.Serializable;

/**
 * Created by p1601248 on 23/03/2018.
 */

public class Position implements Serializable {
    private double lat;
    private double lng;

    public Position(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
