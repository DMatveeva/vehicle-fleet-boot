package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
