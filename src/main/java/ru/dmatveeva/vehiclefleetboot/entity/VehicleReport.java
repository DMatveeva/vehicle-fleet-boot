package ru.dmatveeva.vehiclefleetboot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

@Getter
@Setter
@NoArgsConstructor
public class VehicleReport extends Report{

    public Vehicle vehicle;
}
