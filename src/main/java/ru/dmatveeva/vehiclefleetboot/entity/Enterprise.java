package ru.dmatveeva.vehiclefleetboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

import java.util.List;

@Entity
@Table(name = "enterprises")
@Getter
@Setter
@NoArgsConstructor
public class Enterprise extends AbstractBaseEntity{

    public Enterprise(Integer id, String name, String city, List<Driver> drivers, List<Vehicle> vehicles) {
        super(id);
        this.name = name;
        this.city = city;
        this.drivers = drivers;
        this.vehicles = vehicles;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "enterprise")
    @JsonIgnore
    private List<Driver> drivers;

    @OneToMany(mappedBy = "enterprise")
    @JsonIgnore
    private List<Vehicle> vehicles;

    @ManyToMany
    @JsonIgnore
    private List<Manager> manager;

    @Column(name = "time_zone")
    private String localTimeZone;
}
