package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleModel;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Integer> {
    VehicleModel findByName(String name);
}
