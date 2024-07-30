package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
public class DriverTo {

    @JsonProperty
    private final Integer id;

    @JsonProperty
    private final String firstName;

    @JsonProperty
    private final String secondName;

    @JsonProperty
    private final BigDecimal salaryUSD;

    @JsonProperty
    private final int drivingExperienceYears;

    @JsonProperty
    private final int enterprise_id;

    @JsonProperty
    private final int vehicle_id;

    @JsonProperty
    private final boolean isActive;
}
