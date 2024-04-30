package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class VehicleCoordinateTo {

    private Integer id;
    private Integer track_id;

    @JsonProperty
    private Double lat;

    @JsonProperty
    private Double lon;

    @JsonProperty
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private ZonedDateTime visited;

    @JsonProperty
    private String zone;

    public VehicleCoordinateTo(Integer id, Integer track_id, Double lat, Double lon, ZonedDateTime visited) {
        this.id = id;
        this.track_id = track_id;
        this.lat = lat;
        this.lon = lon;
        this.visited = visited;
        this.zone = visited.getZone().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrack_id() {
        return track_id;
    }

    public void setTrack_id(Integer track_id) {
        this.track_id = track_id;
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

    public ZonedDateTime getVisited() {
        return visited;
    }

    public void setVisited(ZonedDateTime visited) {
        this.visited = visited;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
