package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VehicleGeneratorParamsTo {

    @JsonProperty
    List<Integer> entepriseIds;

    @JsonProperty
    int numVehicles;

    public List<Integer> getEntepriseIds() {
        return entepriseIds;
    }

    public void setEntepriseIds(List<Integer> entepriseIds) {
        this.entepriseIds = entepriseIds;
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public void setNumVehicles(int numVehicles) {
        this.numVehicles = numVehicles;
    }
}
