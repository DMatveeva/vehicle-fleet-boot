package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehicleGeneratorRequestDto {
    @JsonProperty
    List<Integer> entepriseIds;

    @JsonProperty
    int numVehicles;
}
