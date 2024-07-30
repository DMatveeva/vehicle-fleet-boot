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

@Entity
@Table(name = "vehicle_coordinates")
@Getter
@Setter
@NoArgsConstructor
public class VehicleCoordinate extends AbstractBaseEntity implements Serializable {

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
    private Point position;
}
