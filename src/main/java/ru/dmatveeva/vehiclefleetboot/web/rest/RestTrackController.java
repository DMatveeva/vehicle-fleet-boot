package ru.dmatveeva.vehiclefleetboot.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.repository.TrackRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleCoordinateRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.to.VehicleCoordinateTo;
import ru.dmatveeva.vehiclefleetboot.util.CoordinateUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/track", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestTrackController {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleCoordinateRepository coordinateRepository;
    @Autowired
    private TrackRepository trackRepository;

    @GetMapping("/coordinates")
    public List<VehicleCoordinateTo> getTracksCoordinatesByVehicleAndPeriod(@RequestParam int vehicleId,
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startEnterpriseZoned,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endEnterpriseZoned) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Enterprise enterprise = vehicle.getEnterprise();
        List<Track> tracks = getTracksByVehicleAndTimePeriod(vehicle, startEnterpriseZoned, endEnterpriseZoned);

        List<VehicleCoordinate> coordinates = tracks.stream()
                .map(coordinateRepository::findAllByTrackOrderByVisited)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return CoordinateUtils.getCoordinatesTosWithTimezone(coordinates, enterprise.getLocalTimeZone());
    }

    private List<Track> getTracksByVehicleAndTimePeriod(Vehicle vehicle, ZonedDateTime start, ZonedDateTime end) {
        LocalDateTime startUTC = start.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endUTC = end.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        return trackRepository.findAllByVehicleAndStartedAfterAndFinishedBefore(vehicle, startUTC, endUTC);
    }
}
