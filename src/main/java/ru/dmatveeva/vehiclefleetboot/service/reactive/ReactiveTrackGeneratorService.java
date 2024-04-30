package ru.dmatveeva.vehiclefleetboot.service.reactive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.repository.TrackRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleCoordinateRepository;
import ru.dmatveeva.vehiclefleetboot.util.GeometryDecoder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ReactiveTrackGeneratorService {

    private final static String OPENROUTE_URI = "https://api.openrouteservice.org/v2/directions/driving-car";

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    VehicleCoordinateRepository vehicleCoordinateRepository;

    @Autowired
    VehicleCoordinateRepository vehicleCoordinateRepositoryж;

    private JSONArray getGeometryFromOpenroute(double[] start, double[] finish, int maxSpeedKmH) {

        String body = String.format("{\"coordinates\":[%s,%s], \"maximum_speed\":%s}",
                        coordinatesToStr(start), coordinatesToStr(finish), maxSpeedKmH);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                OPENROUTE_URI,
                HttpMethod.POST,
                new HttpEntity<>(body, getHeaders()),
                String.class);

        log.debug("Openroute status: {}", response.getStatusCode());
        log.debug("Openroute body: {}", response.getBody());

        return getGeometryFromResponse(response);
    }

    private JSONArray getGeometryFromResponse(ResponseEntity<String> response) {
        JSONObject json = new JSONObject(response.getBody());
        try {
            var geometryEncoded = json.getJSONArray("routes").getJSONObject(0).getString("geometry");
            return GeometryDecoder.decodeGeometry(geometryEncoded, false);
        } catch (JSONException e) {
            throw new RuntimeException("Error in Openroute response");
        }
    }
    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "5b3ce3597851110001cf6248a454b30fc2664363888082aa092ef980");
        headers.add("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8");
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    String coordinatesToStr(double[] arr) {
        return "[" + arr[0] + "," +arr[1] + "]";
    }

    public void generateTrackRealTime(Vehicle vehicle, double[] start, double[] finish,
                              int maxSpeedKmH, int delay/*, LocalDateTime startDate*/) throws JsonProcessingException {

/*        var geometryStr = getGeometryFromOpenroute(start, finish, maxSpeedKmH).toString();
        var geometryStrArray = geometryStr.substring(2, geometryStr.length()-2).split("],\\[");
        Arrays.stream(geometryStrArray)
                .map(s -> s.split(","))
                .map(a -> (new JSONArray()).put(a[0]).put(a[1]))
                .collect(Collectors.toList());*/


        var geometries = getGeometryFromOpenroute(start, finish, maxSpeedKmH).toString();

        ObjectMapper objectMapper = new ObjectMapper();
        var geometriesStream =
                Arrays.stream(objectMapper.readValue(geometries, Object[].class)).map(o -> (ArrayList<Double>) o);



        ZoneId zoneId = ZoneId.of(vehicle.getEnterprise().getLocalTimeZone());

        Track track = new Track();
        track.setVehicle(vehicle);
        track.setStarted(LocalDateTime.now());
        trackRepository.save(track);

       // LocalDateTime coordinateDate = startDate;
       // Flux<JSONArray> geometryFlux =
                Flux.fromStream(geometriesStream)
                        .delayUntil(d -> Mono.delay(Duration.ofSeconds(delay)))
                        //.delayElements(Duration.ofSeconds(delay))
                        .map(latLon ->  createAndSaveCoordinate(vehicle, latLon, track) )
                        .log()
                        .subscribe(c -> log.debug(c.toString()));

      /*  g


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
        trackRepository.save(track);*/
    }

    private VehicleCoordinate createAndSaveCoordinate(Vehicle vehicle, List<Double> latLon, Track track) {
        double lat = latLon.get(0);
        double lon = latLon.get(1);

        VehicleCoordinate vehicleCoordinate = new VehicleCoordinate();
        vehicleCoordinate.setVehicle(vehicle);
        vehicleCoordinate.setLat(lat);
        vehicleCoordinate.setLon(lon);
        vehicleCoordinate.setTrack(track);

        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coordinate = new Coordinate(lon, lat);
        Point point = geometryFactory.createPoint(coordinate);

        vehicleCoordinate.setPosition(point);
        vehicleCoordinate.setVisited(LocalDateTime.now());
    //    vehicleCoordinateRepository.save(vehicleCoordinate);
        return vehicleCoordinate;
    }
}

//1. Сначала получить все координаты и не сохранять