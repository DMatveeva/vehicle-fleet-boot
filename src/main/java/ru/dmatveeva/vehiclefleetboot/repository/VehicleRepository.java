package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    void deleteById(Integer id);
    List<Vehicle> findAllByEnterprise(Enterprise enterprise);
    List<Vehicle> getAllByEnterprise(Enterprise enterprise, Pageable pageable);
}
