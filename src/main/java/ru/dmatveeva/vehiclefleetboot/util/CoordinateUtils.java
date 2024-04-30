package ru.dmatveeva.vehiclefleetboot.util;


import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.to.VehicleCoordinateTo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoordinateUtils {

    public static List<VehicleCoordinateTo> getCoordinatesTos(List<VehicleCoordinate> vehicles) {
        return vehicles.stream()
                .map(CoordinateUtils::getCoordinateTo)
                .collect(Collectors.toList());
    }

    public static List<VehicleCoordinateTo> getCoordinatesTosWithTimezone(List<VehicleCoordinate> coordinates, String zone) {
        return coordinates.stream()
                .map(c -> CoordinateUtils.getCoordinateToWithTimezone(c, zone))
                .collect(Collectors.toList());
    }

    public static FeatureCollection getFeaturesFromCoordinatesWithTimeZone(List<VehicleCoordinate> coordinates, String zone) {
        List<Feature> features = coordinates.stream()
                .map(c -> CoordinateUtils.getFeatureWithTimezone(c, zone)).toList();
        FeatureCollection fc = new FeatureCollection();
        fc.addAll(features);
        return fc;
    }

    private static Feature getFeatureWithTimezone(VehicleCoordinate c, String zone) {
        LocalDateTime ldtVisited = c.getVisited();
        ZonedDateTime localZoned = ldtVisited.atZone(ZoneId.of("UTC"));
        ZonedDateTime zoned = localZoned.withZoneSameInstant(ZoneId.of(zone));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String visitedStr = zoned.format(formatter);

        Feature f = new Feature();
        Point point = new Point(c.getLat(), c.getLon());
        f.setGeometry(point);
        Map<String, Object> properties = new HashMap<>();
        properties.put("track_id", c.getTrack().getId());
        properties.put("vehicle_id", c.getVehicle().getId());
        properties.put("visited", visitedStr);
        f.setProperties(properties);
        return f;
    }

    public static VehicleCoordinateTo getCoordinateTo(VehicleCoordinate vehicleCoordinate) {
        LocalDateTime ldtVisited = vehicleCoordinate.getVisited();
        ZonedDateTime ldtVisitedZoned = ldtVisited.atZone(ZoneId.of("UTC"));

        return new VehicleCoordinateTo(vehicleCoordinate.getId(),
                vehicleCoordinate.getTrack().getId(),
                vehicleCoordinate.getLat(),
                vehicleCoordinate.getLon(),
                ldtVisitedZoned);
    }

    public static VehicleCoordinateTo getCoordinateToWithTimezone(VehicleCoordinate vehicleCoordinate, String zone) {
        LocalDateTime ldtVisited = vehicleCoordinate.getVisited();
        ZonedDateTime localZoned = ldtVisited.atZone(ZoneId.of("UTC"));
        ZonedDateTime zoned = localZoned.withZoneSameInstant(ZoneId.of(zone));

        return new VehicleCoordinateTo(vehicleCoordinate.getId(),
                vehicleCoordinate.getTrack().getId(),
                vehicleCoordinate.getLat(),
                vehicleCoordinate.getLon(),
                zoned);
    }

    public static double[][] getPoints(List<VehicleCoordinate> coordinates) {
        double[][] points = new double[coordinates.size()][2];
        for (int i = 0; i < coordinates.size(); i++) {
            VehicleCoordinate c = coordinates.get(i);
            double[] p = {c.getLat(), c.getLon()};
            points[i] = p;
        }
        return points;
    }

    public static double[] getCenter(List<VehicleCoordinate> coordinates) {
        Double minLat = coordinates.stream()
                .min(Comparator.comparing(VehicleCoordinate::getLat)).get().getLat();

        Double maxLat = coordinates.stream()
                .max(Comparator.comparing(VehicleCoordinate::getLat)).get().getLat();

        Double minLon = coordinates.stream()
                .min(Comparator.comparing(VehicleCoordinate::getLon)).get().getLon();

        Double maxLon= coordinates.stream()
                .max(Comparator.comparing(VehicleCoordinate::getLon)).get().getLon();

        double cLat = (minLat + maxLat) / 2;
        double cLon = (minLon + maxLon) / 2;

        return new double[]{cLat, cLon};
    }
}
