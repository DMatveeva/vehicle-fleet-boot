package ru.dmatveeva.vehiclefleetboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

import java.io.Serializable;
import java.time.LocalDateTime;
/*

@NamedQueries({
        @NamedQuery(name = Track.BY_VEHICLE_AND_PERIOD,
                query = "SELECT t FROM Track t WHERE t.vehicle=?1 and t.started between ?2 and ?3"),
        @NamedQuery(name = Track.BY_VEHICLE,
                query = "SELECT t FROM Track t WHERE t.vehicle=?1")
})
*/

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor
public class Track extends AbstractBaseEntity implements Serializable {

    public static final String BY_VEHICLE_AND_PERIOD = "Track.getByVehicleAndPeriod";
    public static final String BY_VEHICLE = "Track.getByVehicle";

    @OneToOne
    private Vehicle vehicle;

    @Column(name = "started")
    private LocalDateTime started;

    @Column(name = "finished")
    private LocalDateTime finished;
}
