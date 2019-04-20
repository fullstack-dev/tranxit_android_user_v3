package com.tranxitpro.user.data.network.model;

public class LatLngFireBaseDB {

    private double lat, lng, bearing;

    public LatLngFireBaseDB() {
    }

    public LatLngFireBaseDB(double lat, double lng, double bearing) {
        this.lat = lat;
        this.lng = lng;
        this.bearing = bearing;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
