package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
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
}
