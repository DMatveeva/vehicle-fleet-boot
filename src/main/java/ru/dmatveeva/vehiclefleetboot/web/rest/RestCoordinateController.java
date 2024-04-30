package ru.dmatveeva.vehiclefleetboot.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.geojson.FeatureCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ru.dmatveeva.vehiclefleetboot.to.VehicleCoordinateTo;
import ru.dmatveeva.vehiclefleetboot.util.CoordinateUtils;
import ru.dmatveeva.vehiclefleetboot.util.TripUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = RestCoordinateController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestCoordinateController {

    public static Logger LOG = LoggerFactory.getLogger(RestCoordinateController.class);
    static final String REST_URL = "/rest/coordinates";

    private final VehicleCoordinateRepository coordinateRepository;
    private final VehicleRepository vehicleRepository;
    private final TrackRepository trackRepository;
    private final VehicleCoordinateService coordinateService;

    public RestCoordinateController(VehicleCoordinateRepository coordinateRepository,
                                    VehicleRepository vehicleRepository,
                                    TrackRepository trackRepository,
                                    VehicleCoordinateService coordinateService) {
        this.coordinateRepository = coordinateRepository;
        this.vehicleRepository = vehicleRepository;
        this.trackRepository = trackRepository;
        this.coordinateService = coordinateService;
    }

    @GetMapping("/json/vehicle/{vehicleId}")
    public List<VehicleCoordinateTo> getJsonByVehicleAndPeriod(@PathVariable int vehicleId,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Enterprise enterprise = vehicle.getEnterprise();
        List<VehicleCoordinate> coordinates = getCoordinatesByVehicleAndPeriod(vehicleId, start, end);
        return CoordinateUtils.getCoordinatesTosWithTimezone(coordinates, enterprise.getLocalTimeZone());
    }

    @GetMapping("/geojson/vehicle/{vehicleId}")
    public FeatureCollection getGeoJsonByVehicleAndPeriod(@PathVariable int vehicleId,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Enterprise enterprise = vehicle.getEnterprise();
        List<VehicleCoordinate> coordinates = getCoordinatesByVehicleAndPeriod(vehicleId, start, end);

        return CoordinateUtils.getFeaturesFromCoordinatesWithTimeZone(coordinates, enterprise.getLocalTimeZone());
    }

    @GetMapping("/tracks/vehicle/{vehicleId}")
    public List<VehicleCoordinateTo> getTracksCoordinatesByVehicleAndPeriod(@PathVariable int vehicleId,
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

    @GetMapping("/trips/vehicle/{vehicleId}")
    public List<TripTo> getTripsByVehicleAndPeriod(@PathVariable int vehicleId,
                                                   @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startEnterpriseZoned,
                                                   @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endEnterpriseZoned) throws JsonProcessingException {

        LOG.info("Getting all trips for vehicle {} started after and finished before {}");
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        Enterprise enterprise = vehicle.getEnterprise();
        List<Track> tracks = getTracksByVehicleAndTimePeriod(vehicle, startEnterpriseZoned, endEnterpriseZoned);

        List<TripTo> trips = new ArrayList<>();
        for (Track track: tracks) {
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
    public List<VehicleCoordinate> getCoordinatesByVehicleAndPeriod(int vehicleId, ZonedDateTime start, ZonedDateTime end) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();

        ZonedDateTime utcStart = start.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime utcEnd = end.withZoneSameInstant(ZoneId.of("UTC"));

        return coordinateRepository.findAllByVehicleAndVisitedBetween(
                vehicle, utcStart.toLocalDateTime(), utcEnd.toLocalDateTime());
    }
}
