package ru.dmatveeva.vehiclefleetboot.entity.vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dmatveeva.vehiclefleetboot.entity.AbstractBaseEntity;
import ru.dmatveeva.vehiclefleetboot.entity.Brand;
import ru.dmatveeva.vehiclefleetboot.entity.EngineType;

@Entity
@Table(name = "vehicle_models")
@Getter
@Setter
@NoArgsConstructor
public class VehicleModel extends AbstractBaseEntity {

    @Column(name = "brand", nullable = false)
    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "num_seats", nullable = false)
    private Integer numSeats;

    @Column(name = "engine_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(name = "load_capacity", nullable = false)
    private Integer loadCapacity;

}
