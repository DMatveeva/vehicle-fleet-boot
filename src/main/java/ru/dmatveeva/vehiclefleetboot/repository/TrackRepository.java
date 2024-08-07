package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Integer> {
    List<Track> findAllByVehicleAndStartedAfterAndFinishedBefore(Vehicle vehicle, LocalDateTime startUTC, LocalDateTime endUTC);

    List<Track> getAllByVehicle(Vehicle vehicle);
}
