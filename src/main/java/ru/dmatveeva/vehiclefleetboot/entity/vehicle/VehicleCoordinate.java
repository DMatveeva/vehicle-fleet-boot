package ru.dmatveeva.vehiclefleetboot.entity.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import ru.dmatveeva.vehiclefleetboot.entity.AbstractBaseEntity;
import ru.dmatveeva.vehiclefleetboot.entity.Track;

import java.io.Serializable;
import java.time.LocalDateTime;

/*@NamedQueries({
@NamedQuery(name = VehicleCoordinate.BY_VEHICLE_AND_PERIOD,
        query = "SELECT c FROM VehicleCoordinate c WHERE c.vehicle=?1 and c.visited between ?2 and ?3"),
@NamedQuery(name = VehicleCoordinate.BY_TRACK,
        query = "SELECT c FROM VehicleCoordinate c WHERE c.track=?1 order by c.visited")
})*/

@Entity
@Table(name = "vehicle_coordinates")
@Getter
@Setter
@NoArgsConstructor
public class VehicleCoordinate extends AbstractBaseEntity implements Serializable {

    /*public static final String BY_TRACK = "VehicleCoordinate.getByTrack";
    public static final String BY_VEHICLE_AND_PERIOD = "VehicleCoordinate.getByVehicleAndPeriod";
*/
    private static int SRID = 4326;

    @OneToOne
    private Track track;

    @ManyToOne
    private Vehicle vehicle;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "visited")
    private LocalDateTime visited;

    @Column(columnDefinition = "geometry(Point,4326)", name = "position")
   // @Column(columnDefinition = "Geometry", nullable = true)
    private Point position;
}
