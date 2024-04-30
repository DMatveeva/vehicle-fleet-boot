package ru.dmatveeva.vehiclefleetboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleCoordinateRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class VehicleCoordinateService {

    VehicleCoordinateRepository coordinateRepository;

    @Autowired
    public VehicleCoordinateService(VehicleCoordinateRepository coordinateRepository) {
        this.coordinateRepository = coordinateRepository;
    }

    public VehicleCoordinate getStart(List<VehicleCoordinate> coordinates) {
        return coordinates.stream()
                .min(Comparator.comparing(VehicleCoordinate::getVisited))
                .orElse(null);
    }

    public VehicleCoordinate getFinish(List<VehicleCoordinate> coordinates) {
        return coordinates.stream()
                .max(Comparator.comparing(VehicleCoordinate::getVisited))
                .orElse(null);
    }
}
