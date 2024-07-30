package ru.dmatveeva.vehiclefleetboot.entity.vehicle;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.dmatveeva.vehiclefleetboot.entity.AbstractBaseEntity;
import ru.dmatveeva.vehiclefleetboot.entity.Driver;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle extends AbstractBaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private VehicleModel vehicleModel;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "cost_usd", nullable = false)
    private BigDecimal costUsd;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "production_year", nullable = false)
    private int productionYear;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle", cascade= CascadeType.PERSIST)
    @JsonIgnore
    private List<Driver> drivers;

    @ManyToOne
    private Enterprise enterprise;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    public Vehicle(Integer id,
                   VehicleModel vehicleModel,
                   String vin,
                   BigDecimal costUsd,
                   String color,
                   int mileage,
                   int productionYear,
                   LocalDateTime purchaseDate,
                   List<Driver> drivers,
                   Enterprise enterprise) {
        super(id);
        this.vehicleModel = vehicleModel;
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
        this.purchaseDate = purchaseDate;
        this.drivers = drivers;
        this.enterprise = enterprise;
    }

    public Vehicle(VehicleModel vehicleModel,
                   String vin,
                   BigDecimal costUsd,
                   String color,
                   int mileage,
                   int productionYear,
                   LocalDateTime purchaseDate,
                   List<Driver> drivers,
                   Enterprise enterprise) {
        this.vehicleModel = vehicleModel;
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
        this.purchaseDate = purchaseDate;
        this.drivers = drivers;
        this.enterprise = enterprise;
    }
}
