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

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor
public class Track extends AbstractBaseEntity implements Serializable {

    @OneToOne
    private Vehicle vehicle;

    @Column(name = "started")
    private LocalDateTime started;

    @Column(name = "finished")
    private LocalDateTime finished;
}
