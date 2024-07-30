package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VehicleCoordinateWithAddressTo {

    @JsonProperty
    private Double lat;
    @JsonProperty
    private Double lon;
    @JsonProperty
    private String address;
}
