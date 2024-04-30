package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

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

    public DriverTo(Integer id, String firstName, String secondName, BigDecimal salaryUSD, int drivingExperienceYears, int enterprise_id, int vehicle_id, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.salaryUSD = salaryUSD;
        this.drivingExperienceYears = drivingExperienceYears;
        this.enterprise_id = enterprise_id;
        this.vehicle_id = vehicle_id;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "DriverTo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", salaryUSD=" + salaryUSD +
                ", drivingExperienceYears=" + drivingExperienceYears +
                ", enterprise_id=" + enterprise_id +
                ", vehicle_id=" + vehicle_id +
                ", isActive=" + isActive +
                '}';
    }
}
