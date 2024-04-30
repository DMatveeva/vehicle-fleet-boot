package ru.dmatveeva.vehiclefleetboot.service.generate;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.repository.TrackRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleCoordinateRepository;
import ru.dmatveeva.vehiclefleetboot.service.Producer;
import ru.dmatveeva.vehiclefleetboot.util.GeometryDecoder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TrackGeneratorService {

    @Autowired Producer producer;
    @Autowired TrackRepository trackRepository;
    @Autowired VehicleCoordinateRepository coordinateRepository;

    String coordinatesToStr(double[] arr) {
        return "[" + arr[0] + "," +arr[1] + "]";
    }

    @SneakyThrows
    public void generateTrackInRealTime(Vehicle vehicle, double[] start, double[] finish,
                                        int lengthKm, int maxSpeedKmH, int maxAccelerationMSS) {
    /*    producer.sendGenerateTrackMessage
                (vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS, 10000, LocalDateTime.now());*/
        //generateTrack(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS, 10000, LocalDateTime.now());
    }

    @SneakyThrows
    public void generateTrackInRealTime(String body) {
        producer.sendGenerateTrackMessage(body);
        //generateTrack(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS, 10000, LocalDateTime.now());
    }

    public void generateTrackInstantly(Vehicle vehicle, double[] start, double[] finish,
                                       int lengthKm, int maxSpeedKmH, int maxAccelerationMSS, LocalDateTime startDate) {
        generateTrack(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS, 0, startDate);

    }

    @Transactional
    public void generateTrack(Vehicle vehicle, double[] start, double[] finish,
                              int lengthKm, int maxSpeedKmH, int maxAccelerationMSS, int delay, LocalDateTime startDate) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "5b3ce3597851110001cf6248a454b30fc2664363888082aa092ef980");
        headers.add("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8");
        headers.add("Content-Type", "application/json; charset=utf-8");

        String jsonContent = "{\"coordinates\":["+ coordinatesToStr(start) +"," + coordinatesToStr(finish)+"], \"maximum_speed\":"+ maxSpeedKmH +"}";

        HttpEntity<String> request = new HttpEntity<>(jsonContent, headers);

        String uri = "https://api.openrouteservice.org/v2/directions/driving-car";

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);

        System.out.println("status: " + response.getStatusCode());
        System.out.println("headers: " + response.getHeaders());

        String responseBody = response.getBody();
        System.out.println("body:" + responseBody);

        JSONObject json = new JSONObject(responseBody);
        String geometryEncoded;
        try {
            geometryEncoded = json.getJSONArray("routes").getJSONObject(0).getString("geometry");
        } catch (JSONException e) {
            return;
        }
        JSONArray geometryArray = GeometryDecoder.decodeGeometry(geometryEncoded, false);

        ZoneId zoneId = ZoneId.of(vehicle.getEnterprise().getLocalTimeZone());
        Track track = new Track();
        track.setVehicle(vehicle);
        track.setStarted(startDate);
        trackRepository.save(track);
        LocalDateTime coordinateDate = startDate;
        for (int i = 0; i < geometryArray.length(); i++) {
            JSONArray latLon = geometryArray.getJSONArray(i);
            createAndSaveCoordinate(vehicle, latLon, track, coordinateDate);

            long sec = coordinateDate.toEpochSecond(ZoneOffset.UTC) + 10L;
            coordinateDate = LocalDateTime.ofEpochSecond(sec, 0, ZoneOffset.UTC);
            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        track.setFinished(coordinateDate);
    }


    private void createAndSaveCoordinate(Vehicle vehicle, JSONArray latLon, Track track, LocalDateTime coordinateDate) {
        double lat = latLon.getDouble(0);
        double lon = latLon.getDouble(1);
        VehicleCoordinate vehicleCoordinate = new VehicleCoordinate();
        vehicleCoordinate.setVehicle(vehicle);
        vehicleCoordinate.setLat(lat);
        vehicleCoordinate.setLon(lon);
        vehicleCoordinate.setTrack(track);
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(lon, lat);
        Point point = geometryFactory.createPoint(coordinate);

        vehicleCoordinate.setPosition(point);
        vehicleCoordinate.setVisited(coordinateDate);
        coordinateRepository.save(vehicleCoordinate);
    }

    private static final LocalDateTime MIN_DATE_START = LocalDateTime.of(2018, 1, 1, 0,0);
    private static final LocalDateTime MAX_DATE_START = LocalDateTime.of(2021, 1, 1, 0,0);

    private static final double MIN_LAT_LOS_ANGELES = 33.830504;
    private static final double MIN_LON_LOS_ANGELES = -118.385967;
    private static final double MAX_LAT_LOS_ANGELES = 34.007444;
    private static final double MAX_LON_LOS_ANGELES = -118.070118;

    private static final double MIN_LAT_LAS_VEGAS = 36.147515;
    private static final double MIN_LON_LAS_VEGAS = -115.247185;
    private static final double MAX_LAT_LAS_VEGAS = 36.253734;
    private static final double MAX_LON_LAS_VEGAS = -115.113607;


    // lon, lat
    public void generateTracksForVehicles(List<Vehicle> vehicles, int numTracks, String city) {
        final double minLat;
        final double minLon;
        final double maxLat;
        final double maxLon;
        if (city.equals("Los Angeles")) {
            minLat = MIN_LAT_LOS_ANGELES;
            minLon = MIN_LON_LOS_ANGELES;
            maxLat = MAX_LAT_LOS_ANGELES;
            maxLon = MAX_LON_LOS_ANGELES;
        } else if(city.equals("Las Vegas")) {
            minLat = MIN_LAT_LAS_VEGAS;
            minLon = MIN_LON_LAS_VEGAS;
            maxLat = MAX_LAT_LAS_VEGAS;
            maxLon = MAX_LON_LAS_VEGAS;
        } else {
            throw new RuntimeException();
        }
        for (int j = 0; j < vehicles.size(); j++) {
            Vehicle vehicle = vehicles.get(j);
            for(int i = 0; i <= numTracks; i++) {
                double latStart = ThreadLocalRandom.current().nextDouble(minLat, maxLat);
                double lonStart = ThreadLocalRandom.current().nextDouble(minLon, maxLon);
                double[] start = {lonStart, latStart};
                double latFinish = ThreadLocalRandom.current().nextDouble(minLat, maxLat);
                double lonFinish = ThreadLocalRandom.current().nextDouble(minLon, maxLon);
                double[] finish = {lonFinish, latFinish};
                LocalDateTime startDate = generateDate(MIN_DATE_START, MAX_DATE_START);

                generateTrackInstantly(vehicle, start, finish, 0, 90, 0, startDate);
                System.out.println("vehicle " + j + ", track " + i );
            }
        }
    }

    public LocalDateTime generateDate(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        long minDay = startInclusive.toEpochSecond(ZoneOffset.UTC);
        long maxDay = endExclusive.toEpochSecond(ZoneOffset.UTC);
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.UTC);
    }
}
