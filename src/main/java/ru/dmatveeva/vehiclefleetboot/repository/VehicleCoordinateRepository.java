package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;

import java.time.LocalDateTime;
import java.util.List;

public interface VehicleCoordinateRepository extends JpaRepository<VehicleCoordinate, Integer> {
    List<VehicleCoordinate> findAllByVehicleAndVisitedBetween(Vehicle vehicle, LocalDateTime startUTC,
                                                              LocalDateTime endUTC);

    List<VehicleCoordinate> findAllByTrackOrderByVisited(Track track);
}
