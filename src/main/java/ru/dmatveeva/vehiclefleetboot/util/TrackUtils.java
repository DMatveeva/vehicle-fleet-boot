package ru.dmatveeva.vehiclefleetboot.util;

import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.to.TrackTo;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class TrackUtils {
    public static TrackTo getTrackTo(Track track) {

        ZonedDateTime localZonedStarted = Optional.ofNullable(track.getStarted()).isPresent() ? track.getStarted().atZone(ZoneId.of("UTC")) : null;
        ZonedDateTime localZonedFinished = Optional.ofNullable(track.getFinished()).isPresent() ? track.getFinished().atZone(ZoneId.of("UTC")) : null;

        return new TrackTo(track.getId(),
                track.getVehicle().getId(),
                localZonedStarted,
                localZonedFinished);
    }
}
