package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateRequestDto {

    @JsonProperty
    Integer id;

    @JsonProperty
    double[] start;

    @JsonProperty
    double[] finish;

    @JsonProperty
    int lengthKm;

    @JsonProperty
    int maxSpeedKmH;

    @JsonProperty
    int maxAccelerationMSS;
}
