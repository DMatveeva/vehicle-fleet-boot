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
import ru.dmatveeva.vehiclefleetboot.service.VehicleCoordinateService;
import ru.dmatveeva.vehiclefleetboot.to.TripTo;
import ru.dmatveeva.vehiclefleetboot.util.TripUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/trip", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestTripController {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleCoordinateRepository coordinateRepository;
    @Autowired
    private VehicleCoordinateService coordinateService;
    @Autowired
    private TrackRepository trackRepository;

    @GetMapping()
    public List<TripTo> getTripsByVehicleAndPeriod(@RequestParam int vehicleId,
            @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startEnterpriseZoned,
            @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endEnterpriseZoned) {

        log.info("Getting all trips for vehicle {} started after {} and finished before {}", vehicleId, startEnterpriseZoned, endEnterpriseZoned);
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Enterprise enterprise = vehicle.getEnterprise();
        List<Track> tracks = getTracksByVehicleAndTimePeriod(vehicle, startEnterpriseZoned, endEnterpriseZoned);

        List<TripTo> trips = new ArrayList<>();
        for (Track track : tracks) {
            List<VehicleCoordinate> coordinates = coordinateRepository.findAllByTrackOrderByVisited(track);
            VehicleCoordinate start = coordinateService.getStart(coordinates);
            VehicleCoordinate finish = coordinateService.getFinish(coordinates);
            trips.add(TripUtils.getTripToLocalizedFromTrack(track, start, finish, enterprise.getLocalTimeZone()));
        }
        return trips;
    }

    private List<Track> getTracksByVehicleAndTimePeriod(Vehicle vehicle, ZonedDateTime start, ZonedDateTime end) {
        LocalDateTime startUTC = start.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endUTC = end.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        return trackRepository.findAllByVehicleAndStartedAfterAndFinishedBefore(vehicle, startUTC, endUTC);
    }
}
