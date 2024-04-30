package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleCoordinateWithAddressTo {

    @JsonProperty
    private Double lat;

    @JsonProperty
    private Double lon;

    @JsonProperty
    private String address;

    public VehicleCoordinateWithAddressTo(Double lat, Double lon, String address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
