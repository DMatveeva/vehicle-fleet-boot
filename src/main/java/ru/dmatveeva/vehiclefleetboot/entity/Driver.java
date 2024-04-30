package ru.dmatveeva.vehiclefleetboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

import java.math.BigDecimal;


@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends AbstractBaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "salary_usd")
    private BigDecimal salaryUSD;

    @Column(name = "experience")
    private int drivingExperienceYears;

    @ManyToOne
    private Enterprise enterprise;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @Column(name = "is_active")
    private boolean isActive;
}