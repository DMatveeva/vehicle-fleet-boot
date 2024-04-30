package ru.dmatveeva.vehiclefleetboot.util;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.model.Address;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.to.TripTo;
import ru.dmatveeva.vehiclefleetboot.to.VehicleCoordinateWithAddressTo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class TripUtils {

    private static JsonNominatimClient nominatimClient;

    public static String getAddressFromCoordinate(double lat, double lon) {

        final SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        final ClientConnectionManager connexionManager = new SingleClientConnManager(null, registry);

        final HttpClient httpClient = new DefaultHttpClient(connexionManager, null);

        nominatimClient = new JsonNominatimClient("https://nominatim.openstreetmap.org/", httpClient, "darya.d.chernikova@gmail.com");
        try {
            final Address address = nominatimClient.getAddress(lon, lat);
            return address.getDisplayName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeParams(final Map<String, String> params) {
        return params.entrySet()
                .stream()
                .map(entry -> entry.getKey() + '=' + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    public static TripTo getTripToLocalizedFromTrack(Track track, VehicleCoordinate start, VehicleCoordinate finish, String tz) {
        ZonedDateTime startedZdt = DateUtils.getZdtFromLdtInUtc(track.getStarted(), tz);
        ZonedDateTime finishedZdt = DateUtils.getZdtFromLdtInUtc(track.getFinished(), tz);

        TripTo tripTo = new TripTo();
        tripTo.setStarted(startedZdt);
        tripTo.setFinished(finishedZdt);
        tripTo.setZone(tz);
        tripTo.setVehicleId(track.getVehicle().getId());
        tripTo.setStart(getToWithAddress(start));
        tripTo.setFinish(getToWithAddress(finish));
        return tripTo;

    }

    private static VehicleCoordinateWithAddressTo getToWithAddress(VehicleCoordinate vehicleCoordinate) {
        String startAddress = TripUtils.getAddressFromCoordinate(vehicleCoordinate.getLat(), vehicleCoordinate.getLon());
        return new VehicleCoordinateWithAddressTo(vehicleCoordinate.getLat(), vehicleCoordinate.getLon(), startAddress);
    }
}
